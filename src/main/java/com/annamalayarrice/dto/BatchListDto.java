package com.annamalayarrice.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BatchListDto {

    private Integer paddyId;

    private String material;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;
}