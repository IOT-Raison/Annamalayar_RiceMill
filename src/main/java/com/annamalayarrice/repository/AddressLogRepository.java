package com.annamalayarrice.repository;

import com.annamalayarrice.entity.AddressLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

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
}