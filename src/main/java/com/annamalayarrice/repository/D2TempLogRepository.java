package com.annamalayarrice.repository;

import com.annamalayarrice.entity.D2TempLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface D2TempLogRepository
        extends JpaRepository<D2TempLog, Integer> {

    @Query(value = """
        SELECT
            DATEADD(
                MINUTE,
                DATEDIFF(MINUTE, 0, LOG_TIME),
                0
            ) AS LOG_MINUTE,

            AVG(DRYER_2_ACT_TEMP) AS AVG_TEMP,

            AVG(DRYER_2_VALVE_PERCENTAGE) AS AVG_VALVE

        FROM D2_TEMP_LOG

        WHERE LOG_TIME >= :startTime
          AND LOG_TIME <= :endTime

        GROUP BY
            DATEADD(
                MINUTE,
                DATEDIFF(MINUTE, 0, LOG_TIME),
                0
            )

        ORDER BY LOG_MINUTE
        """,
        nativeQuery = true)
    List<Object[]> findOneMinuteAverage(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}