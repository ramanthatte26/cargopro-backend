package com.cargopro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class LoadRequest {

    @Schema(description = "Shipper's ID", example = "shipperA", required = true)
    @NotBlank
    private String shipperId;

    @Schema(description = "Facility details (loading/unloading info)", required = true)
    @Valid
    @NotNull
    private FacilityDTO facility;

    @Schema(description = "Type of product", example = "Electronics")
    @NotBlank
    private String productType;

    @Schema(description = "Type of truck required", example = "Open")
    @NotBlank
    private String truckType;

    @Min(1)
    @Schema(description = "Number of trucks required", example = "2")
    private int noOfTrucks;

    @Positive
    @Schema(description = "Weight in kg", example = "5000")
    private double weight;

    @Schema(description = "Comments or special instructions", example = "Handle with care")
    private String comment;

    public String getShipperId() {
        return shipperId;
    }

    public void setShipperId(String shipperId) {
        this.shipperId = shipperId;
    }

    public FacilityDTO getFacility() {
        return facility;
    }

    public void setFacility(FacilityDTO facility) {
        this.facility = facility;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getTruckType() {
        return truckType;
    }

    public void setTruckType(String truckType) {
        this.truckType = truckType;
    }

    public int getNoOfTrucks() {
        return noOfTrucks;
    }

    public void setNoOfTrucks(int noOfTrucks) {
        this.noOfTrucks = noOfTrucks;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
