package com.cargopro.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cargopro.model.Load;
import com.cargopro.model.LoadStatus;

public interface LoadRepository extends JpaRepository<Load, UUID> {

    @Query("SELECT l FROM Load l WHERE "
            + "(:shipperId IS NULL OR l.shipperId = :shipperId) AND "
            + "(:truckType IS NULL OR l.truckType = :truckType) AND "
            + "(:status IS NULL OR l.status = :status)")
    Page<Load> findByFilters(@Param("shipperId") String shipperId,
            @Param("truckType") String truckType,
            @Param("status") LoadStatus status,
            Pageable pageable);
}
