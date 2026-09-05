package com.annamalayarrice.repository;

import com.annamalayarrice.entity.Dryer1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface Dryer1Repository
        extends JpaRepository<Dryer1, Integer> {

    @Query("""
        SELECT d
        FROM Dryer1 d
        WHERE d.paddyId = :paddyId
          AND d.machineStatus = 1
        ORDER BY d.logTime ASC
    """)
    Optional<Dryer1> findStart(
            @Param("paddyId") Integer paddyId
    );

    @Query("""
        SELECT d
        FROM Dryer1 d
        WHERE d.paddyId = :paddyId
          AND d.machineStatus = 2
          AND d.logTime > :startTime
        ORDER BY d.logTime ASC
    """)
    Optional<Dryer1> findStop(
            @Param("paddyId") Integer paddyId,
            @Param("startTime") LocalDateTime startTime
    );
}