package com.annamalayarrice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThombaiTankDto {

    private Integer valveNo;

    private Double setTemperature;

    private Integer onDelaySeconds;

    private Integer boilingTimeSeconds;

    private Integer cycleCount;

    private Integer totalCycleTimeSeconds;

    private List<ThombaiCycleDto> cycles;
}