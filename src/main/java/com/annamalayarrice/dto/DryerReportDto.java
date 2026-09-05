package com.annamalayarrice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DryerReportDto {

    private Integer paddyId;

    private String paddyName;

    private String dryerStatus;

    private String binName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long durationMinutes;

    private Integer totalRunMin;

    private Integer totalRunHour;

    private Integer activeStepCount;

    private List<DryerStepDto> steps;

    private List<DryerTemperatureDto> temperature;
}