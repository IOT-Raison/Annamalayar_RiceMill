package com.annamalayarrice.controller;

import com.annamalayarrice.dto.DryerReportDto;
import com.annamalayarrice.service.DryerReportService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports/dryer")
@RequiredArgsConstructor
public class DryerReportController {

    private final DryerReportService dryerReportService;


    @GetMapping("/{paddyId}")
    public DryerReportDto getDryerReport(
            @PathVariable Integer paddyId
    ) {

        return dryerReportService.getReport(
                paddyId
        );
    }
}