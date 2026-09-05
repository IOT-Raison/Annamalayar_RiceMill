package com.annamalayarrice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "THOMBAI_ACT_TEMP", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThombaiActTemp {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "LOG_TIME", nullable = true)
    private LocalDateTime logTime;

    @Column(name = "THOMBAI_BIN_1_ACT_TEMP", nullable = true)
    private Integer thombaiBin1ActTemp;

    @Column(name = "THOMBAI_BIN_2_ACT_TEMP", nullable = true)
    private Integer thombaiBin2ActTemp;

    @Column(name = "THOMBAI_BIN_3_ACT_TEMP", nullable = true)
    private Integer thombaiBin3ActTemp;

    @Column(name = "THOMBAI_BIN_4_ACT_TEMP", nullable = true)
    private Integer thombaiBin4ActTemp;

    @Column(name = "THOMBAI_BIN_5_ACT_TEMP", nullable = true)
    private Integer thombaiBin5ActTemp;

    @Column(name = "THOMBAI_BIN_6_ACT_TEMP", nullable = true)
    private Integer thombaiBin6ActTemp;

}