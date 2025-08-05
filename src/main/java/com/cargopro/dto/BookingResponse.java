package com.cargopro.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

import com.cargopro.model.BookingStatus;

public class BookingResponse {

    @Schema(description = "Unique booking ID", example = "7a57c864-4458-4774-bbf7-847a73406f26")
    private UUID id;

    @Schema(description = "ID of the load this booking refers to", example = "6dbd56e4-2530-40d0-858c-688d5f96f1a3")
    private UUID loadId;

    @Schema(description = "Transporter ID", example = "transporterX")
    private String transporterId;

    @Schema(description = "Proposed rate for the booking", example = "1500")
    private double proposedRate;

    @Schema(description = "Comments for the booking", example = "Will deliver by evening")
    private String comment;

    @Schema(description = "Current status of the booking", example = "PENDING")
    private BookingStatus status;

    @Schema(description = "Timestamp when booking was requested", example = "2025-08-05T12:12:28.92448+05:30")
    private Instant requestedAt;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getLoadId() {
        return loadId;
    }
    public void setLoadId(UUID loadId) {
        this.loadId = loadId;
    }
    public String getTransporterId() {
        return transporterId;
    }
    public void setTransporterId(String transporterId) {
        this.transporterId = transporterId;
    }
    public double getProposedRate() {
        return proposedRate;
    }
    public void setProposedRate(double proposedRate) {
        this.proposedRate = proposedRate;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public BookingStatus getStatus() {
        return status;
    }
    public void setStatus(BookingStatus status) {
        this.status = status;
    }
    public Instant getRequestedAt() {
        return requestedAt;
    }
    public void setRequestedAt(Instant requestedAt) {
        this.requestedAt = requestedAt;
    }

    
}
