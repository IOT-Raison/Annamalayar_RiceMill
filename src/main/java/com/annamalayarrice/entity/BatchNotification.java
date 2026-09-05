package com.annamalayarrice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "BATCH_NOTIFICATION")
@Data
public class BatchNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PADDY_ID", nullable = false, unique = true)
    private Integer paddyID;

    @Column(name = "MESSAGE_SENT")
    private Boolean messageSent = false;

    @Column(name = "SENT_TIME")
    private LocalDateTime sentTime;
}