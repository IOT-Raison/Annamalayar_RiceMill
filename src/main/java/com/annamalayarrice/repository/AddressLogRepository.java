package com.annamalayarrice.repository;

import com.annamalayarrice.entity.AddressLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AddressLogRepository
        extends JpaRepository<AddressLog, Integer> {


    // =========================================================
    // GET COMMON LOG DATA FOR BATCH TIME WINDOW
    // =========================================================

    List<AddressLog>
    findByLogTimeBetweenOrderByLogTimeAsc(
            LocalDateTime startTime,
            LocalDateTime endTime
    );


    // =========================================================
    // FIRST RECORD
    // =========================================================

    AddressLog findTopByLogTimeGreaterThanEqualOrderByLogTimeAsc(
            LocalDateTime startTime
    );


    // =========================================================
    // LAST RECORD
    // =========================================================

    AddressLog findTopByLogTimeLessThanEqualOrderByLogTimeDesc(
            LocalDateTime endTime
    );


 Optional<AddressLog> findTopByOrderByLogTimeDesc();


@Query(value = """
    SELECT
        DATEADD(
            MINUTE,
            DATEDIFF(MINUTE, 0, LOG_TIME),
            0
        ) AS logTime,

        AVG(SILKY_1_AMS) AS silky1Ams,
        AVG(SILKY_2_AMS) AS silky2Ams,
        AVG(WHITNER_1_AMS) AS whitner1Ams,
        AVG(WHITNER_2_AMS) AS whitner2Ams,
        AVG(WHITNER_3_AMS) AS whitner3Ams

    FROM ADDRESS_LOG

    WHERE LOG_TIME BETWEEN :startTime AND :endTime

    GROUP BY
        DATEADD(
            MINUTE,
            DATEDIFF(MINUTE, 0, LOG_TIME),
            0
        )

    ORDER BY
        DATEADD(
            MINUTE,
            DATEDIFF(MINUTE, 0, LOG_TIME),
            0
        )
    """,
    nativeQuery = true)
List<Object[]> findOneMinuteAmsAverage(
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
);
}