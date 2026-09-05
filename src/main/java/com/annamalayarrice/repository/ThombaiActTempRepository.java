package com.annamalayarrice.repository;

import com.annamalayarrice.entity.ThombaiActTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ThombaiActTempRepository
        extends JpaRepository<ThombaiActTemp, Integer> {


    @Query(value = """
        SELECT
            DATEADD(
                MINUTE,
                DATEDIFF(MINUTE, 0, LOG_TIME),
                0
            ) AS logTime,

            AVG(CAST(THOMBAI_BIN_1_ACT_TEMP AS FLOAT)),
            AVG(CAST(THOMBAI_BIN_2_ACT_TEMP AS FLOAT)),
            AVG(CAST(THOMBAI_BIN_3_ACT_TEMP AS FLOAT)),
            AVG(CAST(THOMBAI_BIN_4_ACT_TEMP AS FLOAT)),
            AVG(CAST(THOMBAI_BIN_5_ACT_TEMP AS FLOAT)),
            AVG(CAST(THOMBAI_BIN_6_ACT_TEMP AS FLOAT))

        FROM dbo.THOMBAI_ACT_TEMP

        WHERE LOG_TIME >= :startTime
          AND LOG_TIME <= :endTime

        GROUP BY
            DATEADD(
                MINUTE,
                DATEDIFF(MINUTE, 0, LOG_TIME),
                0
            )

        ORDER BY logTime
        """,
        nativeQuery = true)
    List<Object[]> findOneMinuteAverage(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}