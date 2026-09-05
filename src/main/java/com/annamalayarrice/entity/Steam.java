package com.annamalayarrice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "STEAM", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Steam {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PaddyID", nullable = true)
    private Integer paddyId;

    @Column(name = "PaddyName", nullable = true)
    private String paddyName;

    @Column(name = "MachineStatus", nullable = true)
    private Integer machineStatus;

    @Column(name = "LOG_TIME", nullable = true)
    private LocalDateTime logTime;

}