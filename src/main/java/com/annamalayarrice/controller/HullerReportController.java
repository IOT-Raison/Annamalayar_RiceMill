package com.annamalayarrice.controller;

import com.annamalayarrice.dto.HullerReportDto;
import com.annamalayarrice.service.ExcelExportService;
import com.annamalayarrice.service.HullerReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/reports/huller")
@CrossOrigin
public class HullerReportController {

    private final HullerReportService hullerReportService;
    private final ExcelExportService excelExportService;

    public HullerReportController(
            HullerReportService hullerReportService,
            ExcelExportService excelExportService) {

        this.hullerReportService = hullerReportService;
        this.excelExportService = excelExportService;
    }

    @GetMapping("/completed")
    public ResponseEntity<List<HullerReportDto>> getCompletedHullerReports(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startTime,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endTime) {

        List<HullerReportDto> reports =
                hullerReportService.getCompletedHullerReports(
                        startTime,
                        endTime
                );

        return ResponseEntity.ok(reports);
    }

    @GetMapping("/export")
public ResponseEntity<byte[]> exportHullerReport(
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime startTime,

        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime endTime) throws IOException {

    List<HullerReportDto> reports =
            hullerReportService.getCompletedHullerReports(
                    startTime,
                    endTime
            );

    byte[] excelFile =
            excelExportService.exportHullerReport(reports);

    DateTimeFormatter fileNameFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");

    String fileName =
            "Huller_Batch_Report_"
            + startTime.format(fileNameFormatter)
            + "_to_"
            + endTime.format(fileNameFormatter)
            + ".xlsx";

    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + fileName + "\""
            )
            .contentType(
                    MediaType.APPLICATION_OCTET_STREAM
            )
            .body(excelFile);
}
}