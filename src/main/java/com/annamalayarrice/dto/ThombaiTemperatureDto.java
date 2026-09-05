package com.annamalayarrice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThombaiTemperatureDto {

    private LocalDateTime logTime;

    private Double bin1;

    private Double bin2;

    private Double bin3;

    private Double bin4;

    private Double bin5;

    private Double bin6;
}