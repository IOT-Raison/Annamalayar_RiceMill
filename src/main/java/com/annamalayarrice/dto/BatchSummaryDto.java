package com.annamalayarrice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchSummaryDto {

    private Integer batchId;

    private String batchName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Float paddyWeight;
}