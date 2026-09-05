package com.annamalayarrice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThombaiReportDto {

    // =====================================================
    // THOMBAI PROCESS TIME
    // =====================================================

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long durationMinutes;


    // =====================================================
    // PRE-STEAMING
    // =====================================================

    private LocalDateTime preSteamingStartTime;

    private LocalDateTime preSteamingEndTime;

    private Long preSteamingDurationMinutes;


    // =====================================================
    // THOMBAI PARAMETERS
    // =====================================================

    private Double averageBoilingTimeMinutes;

    private String dryerStatus;

    private Integer totalValveCount;

    private Integer totalMin;

    private Integer totalHour;


    // =====================================================
    // THOMBAI VALVE DATA
    // =====================================================

    private List<ThombaiTankDto> valves;


    // =====================================================
    // THOMBAI ACTUAL TEMPERATURE
    // =====================================================

    private List<ThombaiTemperatureDto> temperature;
}