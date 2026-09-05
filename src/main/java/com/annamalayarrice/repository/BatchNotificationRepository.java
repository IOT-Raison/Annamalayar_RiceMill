package com.annamalayarrice.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.annamalayarrice.entity.BatchNotification;

public interface BatchNotificationRepository
        extends JpaRepository<BatchNotification, Long> {

        Optional<BatchNotification> findByPaddyID(
            Integer paddyID
    );

        // Delete records older than today
    @Modifying
    @Query("DELETE FROM BatchNotification b WHERE b.sentTime < :cutoffTime")
    int deleteOlderThan(LocalDateTime cutoffTime);
}

