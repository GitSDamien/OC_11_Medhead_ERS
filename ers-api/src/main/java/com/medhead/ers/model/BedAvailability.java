package com.medhead.ers.model;

import java.util.List;

public class BedAvailability {
    private double latitude;

    private double longitude;

    private List<Integer> specs;

    public BedAvailability() { }

    public BedAvailability(double latitude, double longitude, List<Integer> specs) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.specs = specs;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Integer> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Integer> specs) {
        this.specs = specs;
    }

    @Override
    public String toString() {
        return "BedAvailability{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", specs=" + specs +
                '}';
    }
}
