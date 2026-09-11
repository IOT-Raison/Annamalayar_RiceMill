package com.annamalayarrice.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BatchReportDto {

    // ==============================
    // BATCH SUMMARY
    // ==============================

    private Integer batchId;
    private String batchName;

    private LocalDateTime batchStartTime;
    private LocalDateTime batchEndTime;

    private Long totalProcessTimeMinutes;

    private Float paddyWeight;
    private Float riceOut;
    private Float broken;
    private Float loss;

    private Float ricePercent;
    private Float brokenPercent;
    private Float lossPercent;

    private Float totalEnergy;


    // ==============================
    // PROCESS REPORTS
    // ==============================

    private PreCleaningReportDto paddyProduction;
    private ThombaiReportDto thombai;
    private DryerReportDto dryer;
    private HullerReportDto huller;


    public BatchReportDto(
            Integer batchId,
            String batchName,
            LocalDateTime batchStartTime,
            LocalDateTime batchEndTime,
            Long totalProcessTimeMinutes,
            Float paddyWeight,
            Float riceOut,
            Float broken,
            Float loss,
            Float ricePercent,
            Float brokenPercent,
            Float lossPercent,
            Float totalEnergy,
            PreCleaningReportDto paddyProduction,
            ThombaiReportDto thombai,
            DryerReportDto dryer,
            HullerReportDto huller) {

        this.batchId = batchId;
        this.batchName = batchName;
        this.batchStartTime = batchStartTime;
        this.batchEndTime = batchEndTime;
        this.totalProcessTimeMinutes = totalProcessTimeMinutes;

        this.paddyWeight = paddyWeight;
        this.riceOut = riceOut;
        this.broken = broken;
        this.loss = loss;

        this.ricePercent = ricePercent;
        this.brokenPercent = brokenPercent;
        this.lossPercent = lossPercent;

        this.totalEnergy = totalEnergy;

        this.paddyProduction = paddyProduction;
        this.thombai = thombai;
        this.dryer = dryer;
        this.huller = huller;
    }
}