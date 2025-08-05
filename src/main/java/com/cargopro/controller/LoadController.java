package com.cargopro.controller;

import com.cargopro.dto.LoadRequest;
import com.cargopro.dto.LoadResponse;
import com.cargopro.service.LoadService;
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

@Tag(name = "Load API", description = "APIs to manage Loads")
@RestController
@RequestMapping("/load")
public class LoadController {

    @Autowired
    private LoadService loadService;

    @Operation(summary = "Create a new Load")
    @PostMapping
    public ResponseEntity<LoadResponse> createLoad(
            @Parameter(description = "Load to create") @Valid @RequestBody LoadRequest loadRequest) {
        LoadResponse response = loadService.createLoad(loadRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get loads with optional filters and pagination")
    @GetMapping
    public ResponseEntity<Page<LoadResponse>> getLoads(
            @Parameter(description = "Filter by shipper ID") @RequestParam(required = false) String shipperId,
            @Parameter(description = "Filter by truck type") @RequestParam(required = false) String truckType,
            @Parameter(description = "Filter by load status") @RequestParam(required = false) String status,
            @Parameter(description = "Page number (zero-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        Page<LoadResponse> responses = loadService.getLoads(shipperId, truckType, status, page, size);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get a single load by ID")
    @GetMapping("/{loadId}")
    public ResponseEntity<LoadResponse> getLoad(
            @Parameter(description = "Load UUID") @PathVariable UUID loadId) {
        LoadResponse response = loadService.getLoad(loadId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a load")
    @PutMapping("/{loadId}")
    public ResponseEntity<LoadResponse> updateLoad(
            @Parameter(description = "ID of the load to update") @PathVariable UUID loadId,
            @Valid @RequestBody LoadRequest loadRequest) {
        LoadResponse response = loadService.updateLoad(loadId, loadRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a load")
    @DeleteMapping("/{loadId}")
    public ResponseEntity<Void> deleteLoad(
            @Parameter(description = "ID of the load to delete") @PathVariable UUID loadId) {
        loadService.deleteLoad(loadId);
        return ResponseEntity.noContent().build();
    }
}
