package com.SNB_MS.PS5.controller;

import com.SNB_MS.PS5.model.OvenExitRequest;
import com.SNB_MS.PS5.model.OvenExitResponse;
import com.SNB_MS.PS5.model.PaintingRequest;
import com.SNB_MS.PS5.model.PaintingResponse;
import com.SNB_MS.PS5.service.SequencingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/sequencing")
@RequiredArgsConstructor
public class SequencingController {
    @Autowired
    private SequencingService sequencingService;

    @PostMapping("/oven-exit")
    public ResponseEntity<OvenExitResponse> handleOvenExit(
            @Valid @RequestBody OvenExitRequest request) {
        OvenExitResponse response = sequencingService.assignBufferLine(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/select-for-painting")
    public ResponseEntity<PaintingResponse> selectForPainting(
            @Valid @RequestBody PaintingRequest request) {
        PaintingResponse response = sequencingService.selectVehicleForPainting(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Smart Sequencing Service is running");
    }
}
