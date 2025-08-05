package com.cargopro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public class BookingRequest {

    @Schema(description = "ID of the load to book", required = true)
    @NotNull
    private UUID loadId;

    @Schema(description = "Transporter ID", example = "transporter1", required = true)
    @NotBlank
    private String transporterId;

    @Schema(description = "Proposed rate for booking", example = "1500")
    @Positive
    private double proposedRate;

    @Schema(description = "Comments or requests", example = "Please deliver by evening")
    private String comment;

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

}
