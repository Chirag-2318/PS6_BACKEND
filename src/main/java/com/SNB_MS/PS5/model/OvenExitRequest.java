package com.SNB_MS.PS5.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OvenExitRequest {

    @NotNull(message = "Buffer state is required")
    private List<List<Vehicle>> bufferLines;
    @Valid
    @NotNull(message = "Incoming vehicle is required")
    private Vehicle incomingVehicle;
    @NotNull(message = "Last vehicle array is required")
    private List<Vehicle> lastVehiclesInLines;
    private List<Boolean> lineAvailability;

    public List<List<Vehicle>> getBufferLines() {
        return bufferLines;
    }

    public void setBufferLines(List<List<Vehicle>> bufferLines) {
        this.bufferLines = bufferLines;
    }

    public Vehicle getIncomingVehicle() {
        return incomingVehicle;
    }

    public void setIncomingVehicle(Vehicle incomingVehicle) {
        this.incomingVehicle = incomingVehicle;
    }

    public List<Vehicle> getLastVehiclesInLines() {
        return lastVehiclesInLines;
    }

    public void setLastVehiclesInLines(List<Vehicle> lastVehiclesInLines) {
        this.lastVehiclesInLines = lastVehiclesInLines;
    }

    public List<Boolean> getLineAvailability() {
        return lineAvailability;
    }

    public void setLineAvailability(List<Boolean> lineAvailability) {
        this.lineAvailability = lineAvailability;
    }

    public OvenExitRequest(List<List<Vehicle>> bufferLines, Vehicle incomingVehicle, List<Vehicle> lastVehiclesInLines, List<Boolean> lineAvailability) {
        this.bufferLines = bufferLines;
        this.incomingVehicle = incomingVehicle;
        this.lastVehiclesInLines = lastVehiclesInLines;
        this.lineAvailability = lineAvailability;
    }
}

