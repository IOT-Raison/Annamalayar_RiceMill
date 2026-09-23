package com.annamalayarrice.service;

import com.annamalayarrice.repository.BatchNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BatchNotificationCleanupService {

    private final BatchNotificationRepository batchNotificationRepository;

    // Run once every month
    // 1st day of every month at 2:00 AM
    @Scheduled(cron = "0 0 2 1 * *")
    @Transactional
    public void deleteOldBatchNotifications() {

        LocalDateTime cutoffTime =
                LocalDateTime.now().minusMonths(1);

        int deleted =
                batchNotificationRepository
                        .deleteOlderThan(cutoffTime);

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "BATCH NOTIFICATION CLEANUP"
        );

        System.out.println(
                "CUTOFF TIME : " + cutoffTime
        );

        System.out.println(
                "DELETED     : " + deleted
        );

        System.out.println(
                "=============================================="
        );
    }
}