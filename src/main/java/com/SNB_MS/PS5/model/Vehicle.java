package com.SNB_MS.PS5.model;


import jakarta.validation.constraints.NotBlank;

public class Vehicle {
    @NotBlank(message = "Vehicle ID cannot be blank")
    private String vehicleId;
    private String ovenSource;
    @NotBlank(message = "Color required")
    private String color;

    public Vehicle(String vehicleId, String ovenSource, String color) {
        this.vehicleId = vehicleId;
        this.ovenSource = ovenSource;
        this.color = color;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOvenSource() {
        return ovenSource;
    }

    public void setOvenSource(String ovenSource) {
        this.ovenSource = ovenSource;
    }
}
