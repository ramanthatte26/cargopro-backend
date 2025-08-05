package com.cargopro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;
import com.cargopro.model.LoadStatus;

public class LoadResponse {

    @Schema(description = "Unique Load ID", example = "6dbd56e4-2530-40d0-858c-688d5f96f1a3")
    private UUID id;

    @Schema(description = "ID of the shipper", example = "shipperA")
    private String shipperId;

    @Schema(description = "Facility details with loading and unloading info")
    private FacilityDTO facility;

    @Schema(description = "Type of product", example = "Electronics")
    private String productType;

    @Schema(description = "Type of truck required", example = "Open")
    private String truckType;

    @Schema(description = "Number of trucks to be booked", example = "2")
    private int noOfTrucks;

    @Schema(description = "Weight of the load", example = "5000")
    private double weight;

    @Schema(description = "Additional comments or instructions", example = "Handle with care")
    private String comment;

    @Schema(description = "Date when the load was posted", example = "2025-08-05T12:12:28.419474+05:30")
    private Instant datePosted;

    @Schema(description = "Current status of the load", example = "POSTED")
    private LoadStatus status;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
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
    public Instant getDatePosted() {
        return datePosted;
    }
    public void setDatePosted(Instant datePosted) {
        this.datePosted = datePosted;
    }
    public LoadStatus getStatus() {
        return status;
    }
    public void setStatus(LoadStatus status) {
        this.status = status;
    }
  
}
