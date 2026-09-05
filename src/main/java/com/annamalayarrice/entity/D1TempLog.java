package com.annamalayarrice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "D1_TEMP_LOG", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class D1TempLog {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "LOG_TIME", nullable = true)
    private LocalDateTime logTime;

    @Column(name = "DRYER_1_ACT_TEMP", nullable = true)
    private Float dryer1ActTemp;

    @Column(name = "DRYER_1_VALVE_PERCENTAGE", nullable = true)
    private Float dryer1ValvePercentage;

}