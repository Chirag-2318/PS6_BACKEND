package com.SNB_MS.PS5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Update {
    
    @Autowired
    private SequencingService sequencingService;
    
    /**
     * Adds a new vehicle to the buffer
     * @param color The color of the vehicle
     * @param ovenSource The source oven ("O1" or "O2")
     * @return The lane index where the vehicle was added, or -1 if failed
     */
    public int addToBuffer(String color, String ovenSource) {
        if (ovenSource.equals("O1")) {
            sequencingService.addToBufferO1(color);
        } else {
            sequencingService.addToBufferO2(color);
        }
        
        // Find which lane the car was added to
        int[][] bufferLanes = sequencingService.getBufferLanes();
        int colorInt = sequencingService.colorToInt(color);
        
        for (int i = 0; i < bufferLanes.length; i++) {
            for (int j = 0; j < bufferLanes[i].length; j++) {
                if (bufferLanes[i][j] == colorInt) {
                    return i;
                }
            }
        }
        return -1; // Failed to add to buffer
    }
    
    /**
     * Removes a vehicle from the specified lane
     * @param laneIndex The index of the lane (0-8)
     * @return The color of the removed vehicle, or null if lane is empty
     */
    public String removeFromBuffer(int laneIndex) {
        return sequencingService.removeFromLane(laneIndex);
    }
    
    /**
     * Gets the current state of all buffer lanes
     * @return 2D array representing the buffer lanes
     */
    public int[][] getBufferState() {
        return sequencingService.getBufferLanes();
    }
    
    /**
     * Gets the current number of cars in the buffer
     * @return Total number of cars in all buffer lanes
     */
    public int getCurrentCarCount() {
        return sequencingService.getCarCount();
    }
    
    /**
     * Gets the penalty counter
     * @return The number of times cars had to be sent to the other oven
     */
    public int getPenaltyCount() {
        return sequencingService.getPenaltyCounter();
    }
    

}
