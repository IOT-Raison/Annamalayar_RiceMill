package com.annamalayarrice.repository;

import com.annamalayarrice.entity.Dryer2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface Dryer2Repository
        extends JpaRepository<Dryer2, Integer> {

    @Query("""
        SELECT d
        FROM Dryer2 d
        WHERE d.paddyId = :paddyId
          AND d.machineStatus = 1
        ORDER BY d.logTime ASC
    """)
    Optional<Dryer2> findStart(
            @Param("paddyId") Integer paddyId
    );

    @Query("""
        SELECT d
        FROM Dryer2 d
        WHERE d.paddyId = :paddyId
          AND d.machineStatus = 2
          AND d.logTime > :startTime
        ORDER BY d.logTime ASC
    """)
    Optional<Dryer2> findStop(
            @Param("paddyId") Integer paddyId,
            @Param("startTime") LocalDateTime startTime
    );
}