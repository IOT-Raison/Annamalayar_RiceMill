package com.annamalayarrice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreCleaningReportDto {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long durationMinutes;

    private Double weightBeforeCleaningKg;

    private Double weightAfterCleaningKg;

    private Double impuritiesRemovedKg;

    private Double lossRatePercentage;
}
