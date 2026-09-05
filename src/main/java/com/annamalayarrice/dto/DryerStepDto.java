package com.annamalayarrice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DryerStepDto {

    private Integer stepNo;

    private Integer setTemperature;

    private Integer setMinutes;

    private Integer setHours;

    private Integer setBlowerHz;

    private Integer setRollerHz;
}