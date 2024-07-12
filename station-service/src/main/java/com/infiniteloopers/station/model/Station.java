package com.infiniteloopers.station.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int totalParkingPlaces;
    private int totalChargingPlaces;
    private double pricePerHour;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalParkingPlaces() {
        return totalParkingPlaces;
    }

    public void setTotalParkingPlaces(int totalParkingPlaces) {
        this.totalParkingPlaces = totalParkingPlaces;
    }

    public int getTotalChargingPlaces() {
        return totalChargingPlaces;
    }

    public void setTotalChargingPlaces(int totalChargingPlaces) {
        this.totalChargingPlaces = totalChargingPlaces;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
}
