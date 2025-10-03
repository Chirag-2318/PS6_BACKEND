package com.SNB_MS.PS5.service;

import com.SNB_MS.PS5.model.Vehicle;

import java.util.List;

public interface SequencingAlgorithm {
    /**
     * Determines which buffer line to assign the incoming vehicle from oven
     * @return line number (0-8 for L1-L9)
     */
    int determineBufferLine(List<List<Vehicle>> bufferLines,
                            Vehicle incomingVehicle,
                            List<Vehicle> lastVehiclesInLines,
                            List<Boolean> lineAvailability);

    /**
     * Determines which buffer line to pick vehicle from for painting
     * @return line number (0-8 for L1-L9)
     */
    int determineVehicleForPainting(List<List<Vehicle>> bufferLines,
                                    String currentOvenColor,
                                    List<Boolean> lineAvailability);
}
