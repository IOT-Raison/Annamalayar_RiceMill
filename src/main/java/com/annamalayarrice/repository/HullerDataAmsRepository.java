package com.annamalayarrice.repository;

import com.annamalayarrice.entity.HullerDataAms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HullerDataAmsRepository
        extends JpaRepository<HullerDataAms, Integer> {


    // =========================================================
    // GET AMS DATA FOR BATCH TIME WINDOW
    // =========================================================

    List<HullerDataAms>
    findByLogTimeBetweenOrderByLogTimeAsc(
            LocalDateTime startTime,
            LocalDateTime endTime
    );


    // =========================================================
    // FIRST RECORD
    // =========================================================

    HullerDataAms
    findTopByLogTimeGreaterThanEqualOrderByLogTimeAsc(
            LocalDateTime startTime
    );


    // =========================================================
    // LAST RECORD
    // =========================================================

    HullerDataAms
    findTopByLogTimeLessThanEqualOrderByLogTimeDesc(
            LocalDateTime endTime
    );
}