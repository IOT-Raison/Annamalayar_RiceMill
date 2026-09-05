package com.annamalayarrice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "PaddyProduction", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaddyProduction {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PaddyID", nullable = false)
    private Integer paddyId;

    @Column(name = "PaddyName", nullable = false)
    private String paddyName;

    @Column(name = "MachineStatus", nullable = false)
    private Integer machineStatus;

    @Column(name = "PaddyActWeight", nullable = true)
    private Float paddyActWeight;

    @Column(name = "LOG_TIME", nullable = true)
    private LocalDateTime logTime;

}