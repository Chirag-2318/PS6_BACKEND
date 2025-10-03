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
    public int handleOvenExit(@Valid @RequestBody OvenExitRequest request) {
        int response = sequencingService.determineBufferLine(request.getBufferLines(), request.getIncomingVehicle(), request.getLastVehiclesInLines(), request.getLineAvailability());
        return response;
    }

    @PostMapping("/select-for-painting")
    public int selectForPainting(@Valid @RequestBody PaintingRequest request) {
        int response = sequencingService.determineVehicleForPainting(request.getBufferLines(), request.getCurrentOvenColor(), request.getLineAvailability());
        return response;
    }


}
