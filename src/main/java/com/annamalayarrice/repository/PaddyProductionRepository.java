package com.annamalayarrice.repository;

import com.annamalayarrice.entity.PaddyProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaddyProductionRepository
        extends JpaRepository<PaddyProduction, Integer> {


   // ---------------------------------------------
    // PRE-CLEANING START
    // MachineStatus = 1
    // ---------------------------------------------
    @Query("""
        SELECT p
        FROM PaddyProduction p
        WHERE p.paddyId = :paddyId
          AND p.machineStatus = 1
        ORDER BY p.logTime ASC
    """)
    Optional<PaddyProduction> findPreCleaningStart(
            @Param("paddyId") Integer paddyId
    );


    // ---------------------------------------------
    // PRE-CLEANING STOP
    // First status = 2 after START
    // ---------------------------------------------
    @Query("""
        SELECT p
        FROM PaddyProduction p
        WHERE p.paddyId = :paddyId
          AND p.machineStatus = 2
          AND p.logTime > :startTime
        ORDER BY p.logTime ASC
    """)
    Optional<PaddyProduction> findPreCleaningStop(
            @Param("paddyId") Integer paddyId,
            @Param("startTime") LocalDateTime startTime
    );

    List<PaddyProduction> findByLogTimeBetweenOrderByLogTimeAsc(
        LocalDateTime startTime,
        LocalDateTime endTime
);

Optional<PaddyProduction>
findTopByPaddyIdAndMachineStatusAndLogTimeAfterOrderByLogTimeAsc(
        Integer paddyId,
        Integer machineStatus,
        LocalDateTime logTime
);

}