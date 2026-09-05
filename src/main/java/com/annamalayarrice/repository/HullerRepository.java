package com.annamalayarrice.repository;

import com.annamalayarrice.entity.Huller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HullerRepository
        extends JpaRepository<Huller, Integer> {

        List<Huller> findByDatetimeFieldBetweenOrderByDatetimeFieldAsc(
            LocalDateTime startTime,
            LocalDateTime endTime
    );

     /*
     * Historical completed batches
     */// Completed records for selected date range
    List<Huller> findByDatetimeFieldBetweenAndBatchStatusOrderByDatetimeFieldAsc(
            LocalDateTime startTime,
            LocalDateTime endTime,
            Integer batchStatus
    );


    /*
     * Get latest completed Huller batch
     */
Optional<Huller> findTopByOrderByDatetimeFieldDesc();



    /*
     * Used by live polling.
     *
     * Get completed records after a particular ID.
     */
    List<Huller> findByIdGreaterThanAndBatchStatusOrderByIdAsc(
            Integer id,
            Integer batchStatus
    );


    // BIN1
    @Query("""
        SELECT h
        FROM Huller h
        WHERE h.paddySilo1Id = :paddyId
          AND h.batchStatus = 1
          AND h.datetimeField <= :endTime
        ORDER BY h.datetimeField DESC
    """)
    List<Huller> findStartForBin1(
            @Param("paddyId") Integer paddyId,
            @Param("endTime") LocalDateTime endTime
    );

    // BIN2
    @Query("""
        SELECT h
        FROM Huller h
        WHERE h.paddySilo2Id = :paddyId
          AND h.batchStatus = 1
          AND h.datetimeField <= :endTime
        ORDER BY h.datetimeField DESC
    """)
    List<Huller> findStartForBin2(
            @Param("paddyId") Integer paddyId,
            @Param("endTime") LocalDateTime endTime
    );

    // BIN3
    @Query("""
        SELECT h
        FROM Huller h
        WHERE h.paddySilo3Id = :paddyId
          AND h.batchStatus = 1
          AND h.datetimeField <= :endTime
        ORDER BY h.datetimeField DESC
    """)
    List<Huller> findStartForBin3(
            @Param("paddyId") Integer paddyId,
            @Param("endTime") LocalDateTime endTime
    );

    // BIN4
    @Query("""
        SELECT h
        FROM Huller h
        WHERE h.paddySilo4Id = :paddyId
          AND h.batchStatus = 1
          AND h.datetimeField <= :endTime
        ORDER BY h.datetimeField DESC
    """)
    List<Huller> findStartForBin4(
            @Param("paddyId") Integer paddyId,
            @Param("endTime") LocalDateTime endTime
    );

List<Huller> findAllByOrderByDatetimeFieldDesc();



}