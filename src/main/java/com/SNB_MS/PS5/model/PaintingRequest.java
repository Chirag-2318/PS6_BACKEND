package com.SNB_MS.PS5.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaintingRequest {
    @NotNull(message =  "Buffer state is required")
    private List<List<Vehicle>> BufferLines;
    @NotBlank(message = "Current oven color is requires")
    private String currentOvenColor;

    private List<Boolean> lineAvailability;

    public List<List<Vehicle>> getBufferLines() {
        return BufferLines;
    }

    public void setBufferLines(List<List<Vehicle>> bufferLines) {
        BufferLines = bufferLines;
    }

    public String getCurrentOvenColor() {
        return currentOvenColor;
    }

    public void setCurrentOvenColor(String currentOvenColor) {
        this.currentOvenColor = currentOvenColor;
    }

    public List<Boolean> getLineAvailability() {
        return lineAvailability;
    }

    public void setLineAvailability(List<Boolean> lineAvailability) {
        this.lineAvailability = lineAvailability;
    }
}
