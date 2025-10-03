package com.SNB_MS.PS5.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaintingResponse {
    private int selectedLineNumber;

    public int getSelectedLineNumber() {
        return selectedLineNumber;
    }

    public void setSelectedLineNumber(int selectedLineNumber) {
        this.selectedLineNumber = selectedLineNumber;
    }

    public PaintingResponse(int selectedLineNumber) {
        this.selectedLineNumber = selectedLineNumber;
    }
}
