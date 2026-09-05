package com.annamalayarrice.dto;

import lombok.Data;

@Data
public class BatchReportDto {

    private PreCleaningReportDto paddyProduction;
    private ThombaiReportDto thombai;
    private DryerReportDto dryer;
    private HullerReportDto huller;

    public BatchReportDto(
            PreCleaningReportDto paddyProduction,
            ThombaiReportDto thombai,
            DryerReportDto dryer,
            HullerReportDto huller) {

        this.paddyProduction = paddyProduction;
        this.thombai = thombai;
        this.dryer = dryer;
        this.huller = huller;
    }
}