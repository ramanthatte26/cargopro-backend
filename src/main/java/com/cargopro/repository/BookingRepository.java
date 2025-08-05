package com.cargopro.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cargopro.model.Booking;
import com.cargopro.model.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    @Query("SELECT b FROM Booking b WHERE "
            + "(:loadId IS NULL OR b.load.id = :loadId) AND "
            + "(:transporterId IS NULL OR b.transporterId = :transporterId) AND "
            + "(:status IS NULL OR b.status = :status)")
    Page<Booking> findByFilters(@Param("loadId") UUID loadId,
            @Param("transporterId") String transporterId,
            @Param("status") BookingStatus status,
            Pageable pageable);

    // Add this method for your status logic
    List<Booking> findByLoadIdAndStatus(UUID loadId, BookingStatus status);
}
