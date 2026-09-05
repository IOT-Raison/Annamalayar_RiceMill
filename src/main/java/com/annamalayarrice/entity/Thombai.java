package com.annamalayarrice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "THOMBAI", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Thombai {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PaddyID", nullable = true)
    private Integer paddyId;

    @Column(name = "PaddyName", nullable = true)
    private String paddyName;

    @Column(name = "MachineStatus", nullable = true)
    private Integer machineStatus;

    @Column(name = "DryerStatus", nullable = true)
    private String dryerStatus;

    @Column(name = "V1_STEP1", nullable = true)
    private Integer v1Step1;

    @Column(name = "V1_STEP2", nullable = true)
    private Integer v1Step2;

    @Column(name = "V1_STEP3", nullable = true)
    private Integer v1Step3;

    @Column(name = "V1_STEP4", nullable = true)
    private Integer v1Step4;

    @Column(name = "V1_STEP5", nullable = true)
    private Integer v1Step5;

    @Column(name = "V1_STEP6", nullable = true)
    private Integer v1Step6;

    @Column(name = "V1_STEP7", nullable = true)
    private Integer v1Step7;

    @Column(name = "V1_STEP8", nullable = true)
    private Integer v1Step8;

    @Column(name = "V1_STEP9", nullable = true)
    private Integer v1Step9;

    @Column(name = "V1_STEP10", nullable = true)
    private Integer v1Step10;

    @Column(name = "V1_STEP11", nullable = true)
    private Integer v1Step11;

    @Column(name = "V1_STEP12", nullable = true)
    private Integer v1Step12;

    @Column(name = "V1_STEP13", nullable = true)
    private Integer v1Step13;

    @Column(name = "V2_STEP1", nullable = true)
    private Integer v2Step1;

    @Column(name = "V2_STEP2", nullable = true)
    private Integer v2Step2;

    @Column(name = "V2_STEP3", nullable = true)
    private Integer v2Step3;

    @Column(name = "V2_STEP4", nullable = true)
    private Integer v2Step4;

    @Column(name = "V2_STEP5", nullable = true)
    private Integer v2Step5;

    @Column(name = "V2_STEP6", nullable = true)
    private Integer v2Step6;

    @Column(name = "V2_STEP7", nullable = true)
    private Integer v2Step7;

    @Column(name = "V2_STEP8", nullable = true)
    private Integer v2Step8;

    @Column(name = "V2_STEP9", nullable = true)
    private Integer v2Step9;

    @Column(name = "V2_STEP10", nullable = true)
    private Integer v2Step10;

    @Column(name = "V2_STEP11", nullable = true)
    private Integer v2Step11;

    @Column(name = "V2_STEP12", nullable = true)
    private Integer v2Step12;

    @Column(name = "V2_STEP13", nullable = true)
    private Integer v2Step13;

    @Column(name = "V3_STEP1", nullable = true)
    private Integer v3Step1;

    @Column(name = "V3_STEP2", nullable = true)
    private Integer v3Step2;

    @Column(name = "V3_STEP3", nullable = true)
    private Integer v3Step3;

    @Column(name = "V3_STEP4", nullable = true)
    private Integer v3Step4;

    @Column(name = "V3_STEP5", nullable = true)
    private Integer v3Step5;

    @Column(name = "V3_STEP6", nullable = true)
    private Integer v3Step6;

    @Column(name = "V3_STEP7", nullable = true)
    private Integer v3Step7;

    @Column(name = "V3_STEP8", nullable = true)
    private Integer v3Step8;

    @Column(name = "V3_STEP9", nullable = true)
    private Integer v3Step9;

    @Column(name = "V3_STEP10", nullable = true)
    private Integer v3Step10;

    @Column(name = "V3_STEP11", nullable = true)
    private Integer v3Step11;

    @Column(name = "V3_STEP12", nullable = true)
    private Integer v3Step12;

    @Column(name = "V3_STEP13", nullable = true)
    private Integer v3Step13;

    @Column(name = "V4_STEP1", nullable = true)
    private Integer v4Step1;

    @Column(name = "V4_STEP2", nullable = true)
    private Integer v4Step2;

    @Column(name = "V4_STEP3", nullable = true)
    private Integer v4Step3;

    @Column(name = "V4_STEP4", nullable = true)
    private Integer v4Step4;

    @Column(name = "V4_STEP5", nullable = true)
    private Integer v4Step5;

    @Column(name = "V4_STEP6", nullable = true)
    private Integer v4Step6;

    @Column(name = "V4_STEP7", nullable = true)
    private Integer v4Step7;

    @Column(name = "V4_STEP8", nullable = true)
    private Integer v4Step8;

    @Column(name = "V4_STEP9", nullable = true)
    private Integer v4Step9;

    @Column(name = "V4_STEP10", nullable = true)
    private Integer v4Step10;

    @Column(name = "V4_STEP11", nullable = true)
    private Integer v4Step11;

    @Column(name = "V4_STEP12", nullable = true)
    private Integer v4Step12;

    @Column(name = "V4_STEP13", nullable = true)
    private Integer v4Step13;

    @Column(name = "V5_STEP1", nullable = true)
    private Integer v5Step1;

    @Column(name = "V5_STEP2", nullable = true)
    private Integer v5Step2;

    @Column(name = "V5_STEP3", nullable = true)
    private Integer v5Step3;

    @Column(name = "V5_STEP4", nullable = true)
    private Integer v5Step4;

    @Column(name = "V5_STEP5", nullable = true)
    private Integer v5Step5;

    @Column(name = "V5_STEP6", nullable = true)
    private Integer v5Step6;

    @Column(name = "V5_STEP7", nullable = true)
    private Integer v5Step7;

    @Column(name = "V5_STEP8", nullable = true)
    private Integer v5Step8;

    @Column(name = "V5_STEP9", nullable = true)
    private Integer v5Step9;

    @Column(name = "V5_STEP10", nullable = true)
    private Integer v5Step10;

    @Column(name = "V5_STEP11", nullable = true)
    private Integer v5Step11;

    @Column(name = "V5_STEP12", nullable = true)
    private Integer v5Step12;

    @Column(name = "V5_STEP13", nullable = true)
    private Integer v5Step13;

    @Column(name = "V6_STEP1", nullable = true)
    private Integer v6Step1;

    @Column(name = "V6_STEP2", nullable = true)
    private Integer v6Step2;

    @Column(name = "V6_STEP3", nullable = true)
    private Integer v6Step3;

    @Column(name = "V6_STEP4", nullable = true)
    private Integer v6Step4;

    @Column(name = "V6_STEP5", nullable = true)
    private Integer v6Step5;

    @Column(name = "V6_STEP6", nullable = true)
    private Integer v6Step6;

    @Column(name = "V6_STEP7", nullable = true)
    private Integer v6Step7;

    @Column(name = "V6_STEP8", nullable = true)
    private Integer v6Step8;

    @Column(name = "V6_STEP9", nullable = true)
    private Integer v6Step9;

    @Column(name = "V6_STEP10", nullable = true)
    private Integer v6Step10;

    @Column(name = "V6_STEP11", nullable = true)
    private Integer v6Step11;

    @Column(name = "V6_STEP12", nullable = true)
    private Integer v6Step12;

    @Column(name = "V6_STEP13", nullable = true)
    private Integer v6Step13;

    @Column(name = "V1_ON_DLY", nullable = true)
    private Integer v1OnDly;

    @Column(name = "V2_ON_DLY", nullable = true)
    private Integer v2OnDly;

    @Column(name = "V3_ON_DLY", nullable = true)
    private Integer v3OnDly;

    @Column(name = "V4_ON_DLY", nullable = true)
    private Integer v4OnDly;

    @Column(name = "V5_ON_DLY", nullable = true)
    private Integer v5OnDly;

    @Column(name = "V6_ON_DLY", nullable = true)
    private Integer v6OnDly;

    @Column(name = "V1_BOIL_TIME", nullable = true)
    private Integer v1BoilTime;

    @Column(name = "V2_BOIL_TIME", nullable = true)
    private Integer v2BoilTime;

    @Column(name = "V3_BOIL_TIME", nullable = true)
    private Integer v3BoilTime;

    @Column(name = "V4_BOIL_TIME", nullable = true)
    private Integer v4BoilTime;

    @Column(name = "V5_BOIL_TIME", nullable = true)
    private Integer v5BoilTime;

    @Column(name = "V6_BOIL_TIME", nullable = true)
    private Integer v6BoilTime;

    @Column(name = "V1_TEMPERATURE", nullable = true)
    private Integer v1Temperature;

    @Column(name = "V2_TEMPERATURE", nullable = true)
    private Integer v2Temperature;

    @Column(name = "V3_TEMPERATURE", nullable = true)
    private Integer v3Temperature;

    @Column(name = "V4_TEMPERATURE", nullable = true)
    private Integer v4Temperature;

    @Column(name = "V5_TEMPERATURE", nullable = true)
    private Integer v5Temperature;

    @Column(name = "V6_TEMPERATURE", nullable = true)
    private Integer v6Temperature;

    @Column(name = "TOTAL_MIN", nullable = true)
    private Integer totalMin;

    @Column(name = "TOTAL_HOUR", nullable = true)
    private Integer totalHour;

    @Column(name = "TOTAL_VALVE_COUNT", nullable = true)
    private Integer totalValveCount;

    @Column(name = "LOG_TIME", nullable = true)
    private LocalDateTime logTime;

}