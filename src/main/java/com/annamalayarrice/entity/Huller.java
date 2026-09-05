package com.annamalayarrice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "HULLER", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Huller {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DATETIME_FIELD", nullable = false)
    private LocalDateTime datetimeField;

    @Column(name = "PADDY_SILO_1_ID", nullable = true)
    private Integer paddySilo1Id;

    @Column(name = "PADDY_NAME_1", nullable = true)
    private String paddyName1;

    @Column(name = "PADDY_SILO_2_ID", nullable = true)
    private Integer paddySilo2Id;

    @Column(name = "PADDY_NAME_2", nullable = true)
    private String paddyName2;

    @Column(name = "PADDY_SILO_3_ID", nullable = true)
    private Integer paddySilo3Id;

    @Column(name = "PADDY_NAME_3", nullable = true)
    private String paddyName3;

    @Column(name = "PADDY_SILO_4_ID", nullable = true)
    private Integer paddySilo4Id;

    @Column(name = "PADDY_NAME_4", nullable = true)
    private String paddyName4;

    @Column(name = "RICE_SILO_1_ID", nullable = true)
    private Integer riceSilo1Id;

    @Column(name = "RICE_NAME_1", nullable = true)
    private String riceName1;

    @Column(name = "RICE_SILO_2_ID", nullable = true)
    private Integer riceSilo2Id;

    @Column(name = "RICE_NAME_2", nullable = true)
    private String riceName2;

    @Column(name = "RICE_SILO_3_ID", nullable = true)
    private Integer riceSilo3Id;

    @Column(name = "RICE_NAME_3", nullable = true)
    private String riceName3;

    @Column(name = "RICE_SILO_4_ID", nullable = true)
    private Integer riceSilo4Id;

    @Column(name = "RICE_NAME_4", nullable = true)
    private String riceName4;

    @Column(name = "PADDY_INPUT_BIN", nullable = true)
    private String paddyInputBin;

    @Column(name = "RICE_OUTPUT_BIN", nullable = true)
    private String riceOutputBin;

    @Column(name = "PADDY_KG", nullable = true)
    private Float paddyKg;

    @Column(name = "RICE_KG", nullable = true)
    private Float riceKg;

    @Column(name = "BROKEN_KG", nullable = true)
    private Float brokenKg;

    @Column(name = "KWH", nullable = true)
    private Float kwh;

    @Column(name = "BATCH_STATUS", nullable = true)
    private Integer batchStatus;

}