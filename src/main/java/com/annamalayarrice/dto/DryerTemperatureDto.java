package com.annamalayarrice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DryerTemperatureDto {

    private LocalDateTime logTime;

    private Double actualTemperature;

    private Double valvePercentage;
}