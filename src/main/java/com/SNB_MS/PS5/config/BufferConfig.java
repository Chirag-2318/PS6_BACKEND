package com.SNB_MS.PS5.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class BufferConfig {
    public static final int[] line_capacities = {14, 14, 14, 14, 16, 16, 16, 16, 16};

    // Lines L1-L4 (indices 0-3) from O1
    public static final int[] O1_PRIMARY_LINES = {0, 1, 2, 3};

    // Lines L5-L9 (indices 4-8) from O2
    public static final int[] O2_PRIMARY_LINES = {4, 5, 6, 7, 8};

    // Penalty for using O2 lines from O1
    public static final double CROSS_OVEN_PENALTY = 1.0;

    // Color distribution percentages
    public static final double[] COLOR_DISTRIBUTION = {
            0.40, 0.25, 0.12, 0.08, 0.03, 0.02, 0.02, 0.02, 0.02, 0.02, 0.02, 0.01
    };

    public static final String[] COLOR_CODES = {
            "C1", "C2", "C3", "C4", "C5", "C6",
            "C7", "C8", "C9", "C10", "C11", "C12"
    };
}
