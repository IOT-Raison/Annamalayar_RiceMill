package com.annamalayarrice.repository;

import com.annamalayarrice.entity.Thombai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ThombaiRepository
        extends JpaRepository<Thombai, Integer> {


   @Query("""
        SELECT t
        FROM Thombai t
        WHERE t.paddyId = :paddyId
          AND t.machineStatus = 1
        ORDER BY t.logTime ASC
    """)
    Optional<Thombai> findStart(
            @Param("paddyId") Integer paddyId
    );

    @Query("""
        SELECT t
        FROM Thombai t
        WHERE t.paddyId = :paddyId
          AND t.machineStatus = 2
          AND t.logTime > :startTime
        ORDER BY t.logTime ASC
    """)
    Optional<Thombai> findStop(
            @Param("paddyId") Integer paddyId,
            @Param("startTime") LocalDateTime startTime
    );
}