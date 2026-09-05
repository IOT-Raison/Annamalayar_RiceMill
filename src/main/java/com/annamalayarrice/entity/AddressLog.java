package com.annamalayarrice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "ADDRESS_LOG", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressLog {

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

    @Column(name = "DRYER_1_ACT_TEMP", nullable = true)
    private Float dryer1ActTemp;

    @Column(name = "DRYER_1_VALVE_PERCENTAGE", nullable = true)
    private Float dryer1ValvePercentage;

    @Column(name = "DRYER_2_ACT_TEMP", nullable = true)
    private Float dryer2ActTemp;

    @Column(name = "DRYER_2_VALVE_PERCENTAGE", nullable = true)
    private Float dryer2ValvePercentage;

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

    @Column(name = "POWER_HOUSE_L1_A", nullable = true)
    private Float powerHouseL1A;

    @Column(name = "POWER_HOUSE_L2_A", nullable = true)
    private Float powerHouseL2A;

    @Column(name = "POWER_HOUSE_L3_A", nullable = true)
    private Float powerHouseL3A;

    @Column(name = "POWER_HOUSE_L1_V", nullable = true)
    private Float powerHouseL1V;

    @Column(name = "POWER_HOUSE_L2_V", nullable = true)
    private Float powerHouseL2V;

    @Column(name = "POWER_HOUSE_L3_V", nullable = true)
    private Float powerHouseL3V;

    @Column(name = "POWER_HOUSE_PF", nullable = true)
    private Float powerHousePf;

    @Column(name = "POWER_HOUSE_KW", nullable = true)
    private Float powerHouseKw;

    @Column(name = "POWER_HOUSE_ACTIVE_KW", nullable = true)
    private Float powerHouseActiveKw;

    @Column(name = "POWER_HOUSE_KWH", nullable = true)
    private Float powerHouseKwh;

    @Column(name = "POWER_HOUSE_MAX_DEMAND", nullable = true)
    private Float powerHouseMaxDemand;

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