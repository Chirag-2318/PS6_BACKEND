package com.SNB_MS.PS5.service;

import com.SNB_MS.PS5.model.*;

import java.nio.BufferOverflowException;
import java.util.List;
public class SequencingService {
    private final SequencingAlgorithm algorithm;

    public SequencingService(SequencingAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Handle vehicle coming out from oven
     */
    public OvenExitResponse assignBufferLine(OvenExitRequest request) {
        // Validate buffer state
        validateBufferState(request.getBufferLines());

        // Call algorithm to determine line
        int assignedLine = algorithm.determineBufferLine(
                request.getBufferLines(),
                request.getIncomingVehicle(),
                request.getLastVehiclesInLines(),
                request.getLineAvailability()
        );

        // Validate assigned line
        if (assignedLine < 0 || assignedLine > 8) {
            throw new IllegalArgumentException("Invalid line number: " + assignedLine);
        }

        // Check capacity
        List<Vehicle> targetLine = request.getBufferLines().get(assignedLine);
        int capacity = assignedLine < 4 ? 14 : 16;

        OvenExitResponse response = new OvenExitResponse();
        response.setAssignedLineNumber(assignedLine);
        return response;
    }

    /**
     * Handle vehicle selection for painting
     */
    public PaintingResponse selectVehicleForPainting(PaintingRequest request) {
        // Validate buffer state
        validateBufferState(request.getBufferLines());

        // Call algorithm to determine line
        int selectedLine = algorithm.determineVehicleForPainting(
                request.getBufferLines(),
                request.getCurrentOvenColor(),
                request.getLineAvailability()
        );

        // Validate selected line
        if (selectedLine < 0 || selectedLine > 8) {
            throw new IllegalStateException("No suitable vehicle found");
        }

        // Get vehicle from selected line
        List<Vehicle> line = request.getBufferLines().get(selectedLine);
        if (line.isEmpty()) {
            throw new IllegalStateException("Selected line is empty");
        }

        Vehicle selectedVehicle = line.get(0); // FIFO
        boolean colorChange = !selectedVehicle.getColor().equals(request.getCurrentOvenColor());
        return new PaintingResponse();
    }

    private void validateBufferState(List<List<Vehicle>> bufferLines) {
        if (bufferLines == null || bufferLines.size() != 9) {
            throw new IllegalArgumentException("Buffer state must have exactly 9 lines");
        }
    }
}
