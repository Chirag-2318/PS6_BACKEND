package com.SNB_MS.PS5.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OvenExitResponse {
    private int assignedLineNumber;

    public int getAssignedLineNumber() {
        return assignedLineNumber;
    }

    public void setAssignedLineNumber(int assignedLineNumber) {
        this.assignedLineNumber = assignedLineNumber;
    }
}
