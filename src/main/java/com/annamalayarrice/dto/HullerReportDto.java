package com.annamalayarrice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HullerReportDto {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private Integer paddyId;

    private String material;

    private Float paddy;
    private Float rice;
    private Float broken;
    private Float kwh;
    private Float ricePercent;
    private Float brokenPercent;
    private Float loss;
    private Float lossPercent;

        // =====================================================
    // HULLER AMS 1-MINUTE GRAPH
    // =====================================================

    private List<HullerAmsGraphDto> amsGraph;
}