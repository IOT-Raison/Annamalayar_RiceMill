package com.annamalayarrice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HullerAmsGraphDto {

    private LocalDateTime logTime;

    private Float silky1Ams;
    private Float silky2Ams;

    private Float whitner1Ams;
    private Float whitner2Ams;
    private Float whitner3Ams;
}