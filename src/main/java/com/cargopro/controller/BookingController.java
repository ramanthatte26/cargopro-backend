package com.cargopro.controller;

import com.cargopro.dto.BookingRequest;
import com.cargopro.dto.BookingResponse;
import com.cargopro.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Booking API", description = "APIs to manage Bookings")
@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Create a booking for a load")
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @Parameter(description = "Booking to create") @Valid @RequestBody BookingRequest bookingRequest) {
        BookingResponse response = bookingService.createBooking(bookingRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get bookings with optional filters and pagination")
    @GetMapping
    public ResponseEntity<Page<BookingResponse>> getBookings(
            @Parameter(description = "Filter by load UUID") @RequestParam(required = false) UUID loadId,
            @Parameter(description = "Filter by transporter ID") @RequestParam(required = false) String transporterId,
            @Parameter(description = "Filter by booking status") @RequestParam(required = false) String status,
            @Parameter(description = "Page number (zero-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        Page<BookingResponse> responses = bookingService.getBookings(loadId, transporterId, status, page, size);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get a single booking by ID")
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBooking(
            @Parameter(description = "Booking UUID") @PathVariable UUID bookingId) {
        BookingResponse response = bookingService.getBooking(bookingId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a booking")
    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> updateBooking(
            @Parameter(description = "ID of the booking to update") @PathVariable UUID bookingId,
            @Valid @RequestBody BookingRequest bookingRequest) {
        BookingResponse response = bookingService.updateBooking(bookingId, bookingRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a booking")
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(
            @Parameter(description = "ID of the booking to delete") @PathVariable UUID bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
