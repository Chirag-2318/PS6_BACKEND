package com.SNB_MS.PS5.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BufferState {
    private List<List<Vehicle>> bufferLines;
    private List<Integer> bufferCapacities;
    private List<Boolean> availability;

    public BufferState(){
        this.bufferLines = new ArrayList<>();
        this.bufferCapacities = List.of(14, 14, 14, 14, 16, 16, 16, 16, 16);
        this.availability = new ArrayList<>();

        for(int i = 0; i < 9; i++){
            bufferLines.add((new ArrayList<>()));
            availability.add(true);
        }
    }


}
