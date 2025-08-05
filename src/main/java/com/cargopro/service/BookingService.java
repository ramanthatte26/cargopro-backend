package com.cargopro.service;

import com.cargopro.dto.BookingRequest;
import com.cargopro.dto.BookingResponse;
import com.cargopro.model.Booking;
import com.cargopro.model.BookingStatus;
import com.cargopro.model.Load;
import com.cargopro.model.LoadStatus;
import com.cargopro.repository.BookingRepository;
import com.cargopro.repository.LoadRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private LoadRepository loadRepository;

    @Autowired
    private LoadService loadService;

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        Load load = loadRepository.findById(request.getLoadId())
                .orElseThrow(() -> new EntityNotFoundException("Load not found with id: " + request.getLoadId()));

        if (load.getStatus() == LoadStatus.CANCELLED) {
            throw new IllegalStateException("Cannot create a booking for a cancelled load");
        }

        Booking booking = new Booking();
        booking.setLoad(load);
        booking.setTransporterId(request.getTransporterId());
        booking.setProposedRate(request.getProposedRate());
        booking.setComment(request.getComment());
        booking.setStatus(BookingStatus.PENDING);
        booking.setRequestedAt(Instant.now());

        Booking saved = bookingRepository.save(booking);

        // Change load status to BOOKED when a booking is made
        loadService.updateLoadStatus(load.getId(), LoadStatus.BOOKED);

        return mapToResponse(saved);
    }

    public Page<BookingResponse> getBookings(UUID loadId, String transporterId, String statusStr, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("requestedAt").descending());
        BookingStatus status = null;
        if (statusStr != null) {
            status = BookingStatus.valueOf(statusStr.toUpperCase());
        }
        Page<Booking> bookings = bookingRepository.findByFilters(loadId, transporterId, status, pageable);
        return bookings.map(this::mapToResponse);
    }

    public BookingResponse getBooking(UUID id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));
        return mapToResponse(booking);
    }

    @Transactional
    public BookingResponse updateBooking(UUID id, BookingRequest request) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        booking.setTransporterId(request.getTransporterId());
        booking.setProposedRate(request.getProposedRate());
        booking.setComment(request.getComment());

        // No status update here as BookingRequest DTO does not include a status field;
        // status updates are handled in separate dedicated methods or controllers optionally.
        Booking saved = bookingRepository.save(booking);
        return mapToResponse(saved);
    }

    @Transactional
    public void deleteBooking(UUID id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        UUID loadId = booking.getLoad().getId();

        bookingRepository.delete(booking);

        // Check if there are any accepted bookings left on the load
        List<Booking> acceptedBookings = bookingRepository.findByLoadIdAndStatus(loadId, BookingStatus.ACCEPTED);

        if (acceptedBookings.isEmpty()) {
            // If no accepted bookings, check if any bookings exist at all
            List<Booking> remainingBookings = bookingRepository.findByLoadIdAndStatus(loadId, BookingStatus.PENDING);
            remainingBookings.addAll(bookingRepository.findByLoadIdAndStatus(loadId, BookingStatus.REJECTED));

            if (remainingBookings.isEmpty()) {
                // If no bookings remain after deletion, revert load status back to POSTED
                loadService.updateLoadStatus(loadId, LoadStatus.POSTED);
            }
        }
    }

    // Helper method for mapping Booking entity to BookingResponse DTO
    private BookingResponse mapToResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setLoadId(booking.getLoad().getId());
        response.setTransporterId(booking.getTransporterId());
        response.setProposedRate(booking.getProposedRate());
        response.setComment(booking.getComment());
        response.setStatus(booking.getStatus());
        response.setRequestedAt(booking.getRequestedAt());
        return response;
    }
}
