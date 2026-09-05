package com.annamalayarrice.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.annamalayarrice.dto.BatchListDto;
import com.annamalayarrice.dto.BatchReportDto;
import com.annamalayarrice.service.BatchReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports/batches")
@CrossOrigin
@RequiredArgsConstructor
public class BatchReportController {

    private final BatchReportService batchReportService;


    // =========================================================
    // 1. GET ALL BATCH IDs BETWEEN START AND END DATE/TIME
    // =========================================================

    @GetMapping
    public ResponseEntity<List<BatchListDto>> getBatches(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startTime,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endTime) {

        List<BatchListDto> batches =
                batchReportService.getBatches(
                        startTime,
                        endTime
                );

        return ResponseEntity.ok(batches);
    }


    // =========================================================
    // 2. GET COMPLETE REPORT FOR SELECTED BATCH
    // =========================================================

    @GetMapping("/{paddyId}")
    public ResponseEntity<BatchReportDto> getCompleteBatchReport(
            @PathVariable Integer paddyId) {

        BatchReportDto report =
                batchReportService.getCompleteBatchReport(
                        paddyId
                );

        return ResponseEntity.ok(report);
    }
}