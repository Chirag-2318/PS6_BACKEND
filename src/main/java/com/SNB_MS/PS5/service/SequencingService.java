package com.SNB_MS.PS5.service;

import com.SNB_MS.PS5.model.*;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SequencingService implements SequencingAlgorithm {

    private List<int[]> bufferLanes; // store colors as integers or indices
    private int[][] bufferLanesArray; // 2D array representation for easier access
    private Map<String, Integer> colorDistribution;
    private int cars;
    private int penaltyCounter;

    public SequencingService() {
        this.bufferLanes = new ArrayList<>();
        // initialize 9 lanes: 0-3 with 14, 4-8 with 16
        for (int i = 0; i < 4; i++) bufferLanes.add(new int[14]);
        for (int i = 4; i < 9; i++) bufferLanes.add(new int[16]);
        
        // Initialize the 2D array representation
        updateBufferLanesArray();

        // color priority mapping
        this.colorDistribution = new HashMap<>();
        colorDistribution.put("C1", 40);
        colorDistribution.put("C2", 25);
        colorDistribution.put("C3", 12);
        colorDistribution.put("C4", 8);
        colorDistribution.put("C5", 3);
        colorDistribution.put("C6", 2);
        colorDistribution.put("C7", 2);
        colorDistribution.put("C8", 2);
        colorDistribution.put("C9", 2);
        colorDistribution.put("C10", 2);
        colorDistribution.put("C11", 2);
        colorDistribution.put("C12", 1);
        colorDistribution.put("0", 0); // empty

        this.cars = 0;
        this.penaltyCounter = 0;
    }

    private void logEvent(String message) {
        System.out.println(LocalDateTime.now() + ": " + message);
    }

    private int getFirstNonZero(int[] lane) {
        for (int val : lane) {
            if (val != 0) return val;
        }
        return 0;
    }

    protected int colorToInt(String color) {
        try {
            return Integer.parseInt(color.replace("C", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    protected String intToColor(int value) {
        if (value == 0) return "0";
        return "C" + value;
    }

    private boolean enqueue(int laneIndex, String color) {
        int[] lane = bufferLanes.get(laneIndex);
        for (int i = 0; i < lane.length; i++) {
            if (lane[i] == 0) {
                lane[i] = colorToInt(color);
                cars++;
                updateBufferLanesArray();
                return true;
            }
        }
        return false;
    }

    // === Oven 1 logic ===
    protected void addToBufferO1(String color) {
        if (cars == 0) {
            enqueue(0, color);
            return;
        }

        // lane colors for L1-L4
        int[] laneColors = new int[4];
        for (int i = 0; i < 4; i++) {
            laneColors[i] = getFirstNonZero(bufferLanes.get(i));
        }

        // matching color
        for (int i = 0; i < 4; i++) {
            if (laneColors[i] == colorToInt(color)) {
                if (enqueue(i, color)) return;
            }
        }

        // min priority
        int minLane = 0;
        int minPriority = Integer.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            String c = intToColor(laneColors[i]);
            int priority = colorDistribution.getOrDefault(c, Integer.MAX_VALUE);
            if (priority < minPriority) {
                minPriority = priority;
                minLane = i;
            }
        }

        if (enqueue(minLane, color)) return;

        // if all full, send to oven2
        penaltyCounter++;
        logEvent("Penalty: Car " + color + " sent to lanes 4-8 using oven1");
        addToBufferO2(color);
    }

    // === Oven 2 logic ===
    protected void addToBufferO2(String color) {
        int[] laneColors = new int[5];
        for (int i = 0; i < 5; i++) {
            laneColors[i] = getFirstNonZero(bufferLanes.get(i + 4));
        }

        // matching color
        for (int i = 0; i < 5; i++) {
            if (laneColors[i] == colorToInt(color)) {
                if (enqueue(i + 4, color)) return;
            }
        }

        // min priority
        int minLane = 4;
        int minPriority = Integer.MAX_VALUE;
        for (int i = 0; i < 5; i++) {
            String c = intToColor(laneColors[i]);
            int priority = colorDistribution.getOrDefault(c, Integer.MAX_VALUE);
            if (priority < minPriority) {
                minPriority = priority;
                minLane = 4 + i;
            }
        }

        if (enqueue(minLane, color)) return;

        logEvent("Error: All lanes 4-8 are full for car " + color);
    }

    /**
     * Returns the current state of buffer lanes as a 2D array
     * @return 2D array representing the buffer lanes
     */
    public int[][] getBufferLanes() {
        return bufferLanesArray;
    }
    
    /**
     * Updates the 2D array representation of buffer lanes
     */
    private void updateBufferLanesArray() {
        bufferLanesArray = new int[9][];
        for (int i = 0; i < bufferLanes.size(); i++) {
            bufferLanesArray[i] = bufferLanes.get(i).clone();
        }
    }
    
    /**
     * Removes a vehicle from the specified lane (FIFO)
     * @param laneIndex Index of the lane (0-8)
     * @return The color of the removed vehicle, or null if lane is empty
     */
    public String removeFromLane(int laneIndex) {
        if (laneIndex < 0 || laneIndex >= bufferLanes.size()) {
            return null;
        }
        
        int[] lane = bufferLanes.get(laneIndex);
        if (lane[0] == 0) {
            return null; // Lane is empty
        }
        
        int removedColor = lane[0];
        // Shift all elements left
        System.arraycopy(lane, 1, lane, 0, lane.length - 1);
        lane[lane.length - 1] = 0; // Clear the last position
        
        cars--;
        updateBufferLanesArray();
        return intToColor(removedColor);
    }
    
    /**
     * Gets the current number of cars in the buffer
     * @return Total number of cars in all buffer lanes
     */
    public int getCarCount() {
        return cars;
    }
    
    /**
     * Gets the current penalty counter
     * @return Number of penalty events
     */
    public int getPenaltyCounter() {
        return penaltyCounter;
    }
    
    /**
     * Displays the current state of all buffer lanes
     */
    public void showBuffer() {
        System.out.println("Current buffer lanes (" + cars + " cars, " + penaltyCounter + " penalties):");
        for (int i = 0; i < bufferLanes.size(); i++) {
            int[] lane = bufferLanes.get(i);
            List<String> filled = new ArrayList<>();
            for (int val : lane) {
                if (val != 0) filled.add(intToColor(val));
            }
            System.out.println(" Lane " + (i + 1) + ": " + filled);
        }
    }

    @Override
    public int determineBufferLine(List<List<Vehicle>> bufferLines, Vehicle incomingVehicle,
                                  List<Vehicle> lastVehiclesInLines, List<Boolean> lineAvailability) {
        String color = incomingVehicle.getColor();
        String oven = incomingVehicle.getOvenSource();

        if ("O1".equals(oven)) {
            addToBufferO1(color);
        } else {
            addToBufferO2(color);
        }

        // Return line index where it was added (find last added)
        for (int i = 0; i < bufferLanes.size(); i++) {
            int[] lane = bufferLanes.get(i);
            for (int val : lane) {
                if (val == colorToInt(color)) {
                    return i;
                }
            }
        }
        return 0;
    }

    @Override
    public int determineVehicleForPainting(List<List<Vehicle>> bufferLines, String currentOvenColor,
                                         List<Boolean> lineAvailability) {
        // simple strategy: pick first non-empty lane
        for (int i = 0; i < bufferLanes.size(); i++) {
            int[] lane = bufferLanes.get(i);
            if (lane[0] != 0) {
                return i;
            }
        }
        return -1;
    }
}