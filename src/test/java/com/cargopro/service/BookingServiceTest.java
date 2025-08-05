package com.cargopro.service;

import com.cargopro.dto.BookingRequest;
import com.cargopro.model.*;
import com.cargopro.repository.BookingRepository;
import com.cargopro.repository.LoadRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private LoadRepository loadRepository;

    @Mock
    private LoadService loadService;

    private Load load;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        load = new Load();
        load.setId(UUID.randomUUID());
        load.setStatus(LoadStatus.POSTED);
    }

    private BookingRequest createBookingRequest() {
        BookingRequest request = new BookingRequest();
        request.setLoadId(load.getId());
        request.setTransporterId("transporter1");
        request.setProposedRate(500.0);
        request.setComment("Please handle delicately");
        return request;
    }

    @Test
    void createBooking_shouldCreateBookingAndChangeLoadStatus() {
        BookingRequest request = createBookingRequest();

        when(loadRepository.findById(load.getId())).thenReturn(Optional.of(load));
        when(bookingRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var response = bookingService.createBooking(request);

        assertEquals(request.getTransporterId(), response.getTransporterId());
        verify(loadService, times(1)).updateLoadStatus(load.getId(), LoadStatus.BOOKED);
    }

    @Test
    void createBooking_shouldThrowException_whenLoadCancelled() {
        load.setStatus(LoadStatus.CANCELLED);
        when(loadRepository.findById(load.getId())).thenReturn(Optional.of(load));

        BookingRequest request = createBookingRequest();

        assertThrows(IllegalStateException.class, () -> bookingService.createBooking(request));
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void getBooking_shouldReturnBookingResponse() {
        UUID id = UUID.randomUUID();
        Booking booking = new Booking();
        booking.setId(id);
        booking.setLoad(load);
        booking.setTransporterId("transporter1");
        booking.setStatus(BookingStatus.PENDING);

        when(bookingRepository.findById(id)).thenReturn(Optional.of(booking));

        var response = bookingService.getBooking(id);

        assertEquals(id, response.getId());
        assertEquals("transporter1", response.getTransporterId());
    }

    @Test
    void getBooking_shouldThrow_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(bookingRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookingService.getBooking(id));
    }

    @Test
    void deleteBooking_shouldUpdateLoadStatusToPostedWhenNoBookingsLeft() {
        UUID bookingId = UUID.randomUUID();
        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setLoad(load);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(bookingRepository.findByLoadIdAndStatus(load.getId(), BookingStatus.ACCEPTED)).thenReturn(Collections.emptyList());
        when(bookingRepository.findByLoadIdAndStatus(load.getId(), BookingStatus.PENDING)).thenReturn(Collections.emptyList());
        when(bookingRepository.findByLoadIdAndStatus(load.getId(), BookingStatus.REJECTED)).thenReturn(Collections.emptyList());

        bookingService.deleteBooking(bookingId);

        verify(bookingRepository, times(1)).delete(booking);
        verify(loadService, times(1)).updateLoadStatus(load.getId(), LoadStatus.POSTED);
    }

    // Add more tests for updateBooking, getBookings with filters, and edge cases
}
