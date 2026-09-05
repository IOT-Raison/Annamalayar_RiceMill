package com.annamalayarrice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "DRYER_1", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dryer1 {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PaddyID", nullable = true)
    private Integer paddyId;

    @Column(name = "PaddyName", nullable = false)
    private String paddyName;

    @Column(name = "MachineStatus", nullable = true)
    private Integer machineStatus;

    @Column(name = "BinName", nullable = false)
    private String binName;

    @Column(name = "TOTAL_RUN_MIN", nullable = true)
    private Integer totalRunMin;

    @Column(name = "TOTAL_RUN_HOUR", nullable = true)
    private Integer totalRunHour;

    @Column(name = "STEP_1_SET_TEMP", nullable = true)
    private Integer step1SetTemp;

    @Column(name = "STEP_1_SET_MIN", nullable = true)
    private Integer step1SetMin;

    @Column(name = "STEP_1_SET_HOUR", nullable = true)
    private Integer step1SetHour;

    @Column(name = "STEP_1_SET_BLOWER_HZ", nullable = true)
    private Integer step1SetBlowerHz;

    @Column(name = "STEP_1_SET_ROLLER_HZ", nullable = true)
    private Integer step1SetRollerHz;

    @Column(name = "STEP_2_SET_TEMP", nullable = true)
    private Integer step2SetTemp;

    @Column(name = "STEP_2_SET_MIN", nullable = true)
    private Integer step2SetMin;

    @Column(name = "STEP_2_SET_HOUR", nullable = true)
    private Integer step2SetHour;

    @Column(name = "STEP_2_SET_BLOWER_HZ", nullable = true)
    private Integer step2SetBlowerHz;

    @Column(name = "STEP_2_SET_ROLLER_HZ", nullable = true)
    private Integer step2SetRollerHz;

    @Column(name = "STEP_3_SET_TEMP", nullable = true)
    private Integer step3SetTemp;

    @Column(name = "STEP_3_SET_MIN", nullable = true)
    private Integer step3SetMin;

    @Column(name = "STEP_3_SET_HOUR", nullable = true)
    private Integer step3SetHour;

    @Column(name = "STEP_3_SET_BLOWER_HZ", nullable = true)
    private Integer step3SetBlowerHz;

    @Column(name = "STEP_3_SET_ROLLER_HZ", nullable = true)
    private Integer step3SetRollerHz;

    @Column(name = "STEP_4_SET_TEMP", nullable = true)
    private Integer step4SetTemp;

    @Column(name = "STEP_4_SET_MIN", nullable = true)
    private Integer step4SetMin;

    @Column(name = "STEP_4_SET_HOUR", nullable = true)
    private Integer step4SetHour;

    @Column(name = "STEP_4_SET_BLOWER_HZ", nullable = true)
    private Integer step4SetBlowerHz;

    @Column(name = "STEP_4_SET_ROLLER_HZ", nullable = true)
    private Integer step4SetRollerHz;

    @Column(name = "STEP_5_SET_TEMP", nullable = true)
    private Integer step5SetTemp;

    @Column(name = "STEP_5_SET_MIN", nullable = true)
    private Integer step5SetMin;

    @Column(name = "STEP_5_SET_HOUR", nullable = true)
    private Integer step5SetHour;

    @Column(name = "STEP_5_SET_BLOWER_HZ", nullable = true)
    private Integer step5SetBlowerHz;

    @Column(name = "STEP_5_SET_ROLLER_HZ", nullable = true)
    private Integer step5SetRollerHz;

    @Column(name = "STEP_6_SET_TEMP", nullable = true)
    private Integer step6SetTemp;

    @Column(name = "STEP_6_SET_MIN", nullable = true)
    private Integer step6SetMin;

    @Column(name = "STEP_6_SET_HOUR", nullable = true)
    private Integer step6SetHour;

    @Column(name = "STEP_6_SET_BLOWER_HZ", nullable = true)
    private Integer step6SetBlowerHz;

    @Column(name = "STEP_6_SET_ROLLER_HZ", nullable = true)
    private Integer step6SetRollerHz;

    @Column(name = "STEP_7_SET_TEMP", nullable = true)
    private Integer step7SetTemp;

    @Column(name = "STEP_7_SET_MIN", nullable = true)
    private Integer step7SetMin;

    @Column(name = "STEP_7_SET_HOUR", nullable = true)
    private Integer step7SetHour;

    @Column(name = "STEP_7_SET_BLOWER_HZ", nullable = true)
    private Integer step7SetBlowerHz;

    @Column(name = "STEP_7_SET_ROLLER_HZ", nullable = true)
    private Integer step7SetRollerHz;

    @Column(name = "STEP_8_SET_TEMP", nullable = true)
    private Integer step8SetTemp;

    @Column(name = "STEP_8_SET_MIN", nullable = true)
    private Integer step8SetMin;

    @Column(name = "STEP_8_SET_HOUR", nullable = true)
    private Integer step8SetHour;

    @Column(name = "STEP_8_SET_BLOWER_HZ", nullable = true)
    private Integer step8SetBlowerHz;

    @Column(name = "STEP_8_SET_ROLLER_HZ", nullable = true)
    private Integer step8SetRollerHz;

    @Column(name = "STEP_9_SET_TEMP", nullable = true)
    private Integer step9SetTemp;

    @Column(name = "STEP_9_SET_MIN", nullable = true)
    private Integer step9SetMin;

    @Column(name = "STEP_9_SET_HOUR", nullable = true)
    private Integer step9SetHour;

    @Column(name = "STEP_9_SET_BLOWER_HZ", nullable = true)
    private Integer step9SetBlowerHz;

    @Column(name = "STEP_9_SET_ROLLER_HZ", nullable = true)
    private Integer step9SetRollerHz;

    @Column(name = "STEP_10_SET_TEMP", nullable = true)
    private Integer step10SetTemp;

    @Column(name = "STEP_10_SET_MIN", nullable = true)
    private Integer step10SetMin;

    @Column(name = "STEP_10_SET_HOUR", nullable = true)
    private Integer step10SetHour;

    @Column(name = "STEP_10_SET_BLOWER_HZ", nullable = true)
    private Integer step10SetBlowerHz;

    @Column(name = "STEP_10_SET_ROLLER_HZ", nullable = true)
    private Integer step10SetRollerHz;

    @Column(name = "STEP_11_SET_MIN", nullable = true)
    private Integer step11SetMin;

    @Column(name = "STEP_11_SET_HOUR", nullable = true)
    private Integer step11SetHour;

    @Column(name = "STEP_11_SET_BLOWER_HZ", nullable = true)
    private Integer step11SetBlowerHz;

    @Column(name = "STEP_11_SET_ROLLER_HZ", nullable = true)
    private Integer step11SetRollerHz;

    @Column(name = "STEP_12_SET_TEMP", nullable = true)
    private Integer step12SetTemp;

    @Column(name = "STEP_12_SET_MIN", nullable = true)
    private Integer step12SetMin;

    @Column(name = "STEP_12_SET_HOUR", nullable = true)
    private Integer step12SetHour;

    @Column(name = "STEP_12_SET_BLOWER_HZ", nullable = true)
    private Integer step12SetBlowerHz;

    @Column(name = "STEP_12_SET_ROLLER_HZ", nullable = true)
    private Integer step12SetRollerHz;

    @Column(name = "STEP_13_SET_TEMP", nullable = true)
    private Integer step13SetTemp;

    @Column(name = "STEP_13_SET_MIN", nullable = true)
    private Integer step13SetMin;

    @Column(name = "STEP_13_SET_HOUR", nullable = true)
    private Integer step13SetHour;

    @Column(name = "STEP_13_SET_BLOWER_HZ", nullable = true)
    private Integer step13SetBlowerHz;

    @Column(name = "STEP_13_SET_ROLLER_HZ", nullable = true)
    private Integer step13SetRollerHz;

    @Column(name = "STEP_14_SET_TEMP", nullable = true)
    private Integer step14SetTemp;

    @Column(name = "STEP_14_SET_MIN", nullable = true)
    private Integer step14SetMin;

    @Column(name = "STEP_14_SET_HOUR", nullable = true)
    private Integer step14SetHour;

    @Column(name = "STEP_14_SET_BLOWER_HZ", nullable = true)
    private Integer step14SetBlowerHz;

    @Column(name = "STEP_14_SET_ROLLER_HZ", nullable = true)
    private Integer step14SetRollerHz;

    @Column(name = "STEP_15_SET_TEMP", nullable = true)
    private Integer step15SetTemp;

    @Column(name = "STEP_15_SET_MIN", nullable = true)
    private Integer step15SetMin;

    @Column(name = "STEP_15_SET_HOUR", nullable = true)
    private Integer step15SetHour;

    @Column(name = "STEP_15_SET_BLOWER_HZ", nullable = true)
    private Integer step15SetBlowerHz;

    @Column(name = "STEP_15_SET_ROLLER_HZ", nullable = true)
    private Integer step15SetRollerHz;

    @Column(name = "STEP_16_SET_TEMP", nullable = true)
    private Integer step16SetTemp;

    @Column(name = "STEP_16_SET_MIN", nullable = true)
    private Integer step16SetMin;

    @Column(name = "STEP_16_SET_HOUR", nullable = true)
    private Integer step16SetHour;

    @Column(name = "STEP_16_SET_BLOWER_HZ", nullable = true)
    private Integer step16SetBlowerHz;

    @Column(name = "STEP_16_SET_ROLLER_HZ", nullable = true)
    private Integer step16SetRollerHz;

    @Column(name = "STEP_17_SET_TEMP", nullable = true)
    private Integer step17SetTemp;

    @Column(name = "STEP_17_SET_MIN", nullable = true)
    private Integer step17SetMin;

    @Column(name = "STEP_17_SET_HOUR", nullable = true)
    private Integer step17SetHour;

    @Column(name = "STEP_17_SET_BLOWER_HZ", nullable = true)
    private Integer step17SetBlowerHz;

    @Column(name = "STEP_17_SET_ROLLER_HZ", nullable = true)
    private Integer step17SetRollerHz;

    @Column(name = "STEP_18_SET_TEMP", nullable = true)
    private Integer step18SetTemp;

    @Column(name = "STEP_18_SET_MIN", nullable = true)
    private Integer step18SetMin;

    @Column(name = "STEP_18_SET_HOUR", nullable = true)
    private Integer step18SetHour;

    @Column(name = "STEP_18_SET_BLOWER_HZ", nullable = true)
    private Integer step18SetBlowerHz;

    @Column(name = "STEP_18_SET_ROLLER_HZ", nullable = true)
    private Integer step18SetRollerHz;

    @Column(name = "STEP_19_SET_TEMP", nullable = true)
    private Integer step19SetTemp;

    @Column(name = "STEP_19_SET_MIN", nullable = true)
    private Integer step19SetMin;

    @Column(name = "STEP_19_SET_HOUR", nullable = true)
    private Integer step19SetHour;

    @Column(name = "STEP_19_SET_BLOWER_HZ", nullable = true)
    private Integer step19SetBlowerHz;

    @Column(name = "STEP_19_SET_ROLLER_HZ", nullable = true)
    private Integer step19SetRollerHz;

    @Column(name = "STEP_20_SET_TEMP", nullable = true)
    private Integer step20SetTemp;

    @Column(name = "STEP_20_SET_MIN", nullable = true)
    private Integer step20SetMin;

    @Column(name = "STEP_20_SET_HOUR", nullable = true)
    private Integer step20SetHour;

    @Column(name = "STEP_20_SET_BLOWER_HZ", nullable = true)
    private Integer step20SetBlowerHz;

    @Column(name = "STEP_20_SET_ROLLER_HZ", nullable = true)
    private Integer step20SetRollerHz;

    @Column(name = "STEP_11_SET_TEMP", nullable = true)
    private Integer step11SetTemp;

    @Column(name = "LOG_TIME", nullable = true)
    private LocalDateTime logTime;

}