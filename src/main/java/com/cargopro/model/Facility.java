package com.cargopro.model;

import java.time.Instant;

import jakarta.persistence.Embeddable;

@Embeddable
public class Facility {

    private String loadingPoint;
    private String unloadingPoint;
    private Instant loadingDate;
    private Instant unloadingDate;

    public Facility() {
    }

    public Facility(String loadingPoint, String unloadingPoint, Instant loadingDate, Instant unloadingDate) {
        this.loadingPoint = loadingPoint;
        this.unloadingPoint = unloadingPoint;
        this.loadingDate = loadingDate;
        this.unloadingDate = unloadingDate;
    }

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
