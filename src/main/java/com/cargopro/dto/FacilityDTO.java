package com.cargopro.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FacilityDTO {

    @Schema(description = "Location where the loading occurs", example = "CityX")
    @NotBlank
    private String loadingPoint;

    @Schema(description = "Location where the unloading occurs", example = "CityY")
    @NotBlank
    private String unloadingPoint;

    @Schema(description = "Loading date", example = "2025-01-01T08:00:00Z")
    @NotNull
    private Instant loadingDate;

    @Schema(description = "Unloading date", example = "2025-01-01T12:00:00Z")
    @NotNull
    private Instant unloadingDate;

    public String getLoadingPoint() {
        return loadingPoint;
    }

    public void setLoadingPoint(String loadingPoint) {
        this.loadingPoint = loadingPoint;
    }

    public String getUnloadingPoint() {
        return unloadingPoint;
    }

    public void setUnloadingPoint(String unloadingPoint) {
        this.unloadingPoint = unloadingPoint;
    }

    public Instant getLoadingDate() {
        return loadingDate;
    }

    public void setLoadingDate(Instant loadingDate) {
        this.loadingDate = loadingDate;
    }

    public Instant getUnloadingDate() {
        return unloadingDate;
    }

    public void setUnloadingDate(Instant unloadingDate) {
        this.unloadingDate = unloadingDate;
    }

    
}
