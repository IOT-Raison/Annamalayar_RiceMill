package com.annamalayarrice.repository;

import com.annamalayarrice.entity.Steam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.Optional;

public interface SteamRepository
        extends JpaRepository<Steam, Integer> {

   @Query("""
        SELECT s
        FROM Steam s
        WHERE s.paddyId = :paddyId
          AND s.machineStatus = 1
        ORDER BY s.logTime ASC
    """)
    Optional<Steam> findStart(
            @Param("paddyId") Integer paddyId
    );


    @Query("""
        SELECT s
        FROM Steam s
        WHERE s.paddyId = :paddyId
          AND s.machineStatus = 2
          AND s.logTime > :startTime
        ORDER BY s.logTime ASC
    """)
    Optional<Steam> findStop(
            @Param("paddyId") Integer paddyId,
            @Param("startTime") LocalDateTime startTime
    );
}