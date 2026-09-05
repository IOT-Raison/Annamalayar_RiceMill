package com.annamalayarrice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "HULLER_DATA_AMS", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HullerDataAms {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "LOG_TIME", nullable = true)
    private LocalDateTime logTime;

    @Column(name = "SILKY_1_AMS", nullable = true)
    private Float silky1Ams;

    @Column(name = "SILKY_2_AMS", nullable = true)
    private Float silky2Ams;

    @Column(name = "WHITNER_1_AMS", nullable = true)
    private Float whitner1Ams;

    @Column(name = "WHITNER_2_AMS", nullable = true)
    private Float whitner2Ams;

    @Column(name = "WHITNER_3_AMS", nullable = true)
    private Float whitner3Ams;

    @Column(name = "HULLER_L1_A", nullable = true)
    private Float hullerL1A;

    @Column(name = "HULLER_L2_A", nullable = true)
    private Float hullerL2A;

    @Column(name = "HULLER_L3_A", nullable = true)
    private Float hullerL3A;

    @Column(name = "HULLER_L1_V", nullable = true)
    private Float hullerL1V;

    @Column(name = "HULLER_L2_V", nullable = true)
    private Float hullerL2V;

    @Column(name = "HULLER_L3_V", nullable = true)
    private Float hullerL3V;

    @Column(name = "HULLER_KWH", nullable = true)
    private Float hullerKwh;

}