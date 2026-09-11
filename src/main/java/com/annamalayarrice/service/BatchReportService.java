package com.annamalayarrice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import java.util.List;
import com.annamalayarrice.dto.BatchListDto;
import com.annamalayarrice.dto.HullerReportDto;
import com.annamalayarrice.repository.HullerRepository;
import com.annamalayarrice.dto.BatchReportDto;
import com.annamalayarrice.dto.DryerReportDto;
import com.annamalayarrice.dto.PreCleaningReportDto;
import com.annamalayarrice.dto.ThombaiReportDto;
import com.annamalayarrice.entity.Huller;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BatchReportService {

    private final PreCleaningService preCleaningService;
    private final ThombaiReportService thombaiReportService;
    private final DryerReportService dryerReportService;
    private final HullerReportService hullerReportService;
    private final HullerRepository hullerRepository;


    public BatchReportDto getCompleteBatchReport(
            Integer paddyId) {

        // =====================================================
        // 1. PADDY PRODUCTION
        // =====================================================

        PreCleaningReportDto paddyProduction =
                preCleaningService.getPreCleaningReport(
                        paddyId
                );


        // =====================================================
        // 2. THOMBAI
        // =====================================================

        ThombaiReportDto thombai =
                thombaiReportService.getReport(
                        paddyId
                );


        // =====================================================
        // 3. DRYER
        // =====================================================

        DryerReportDto dryer =
                dryerReportService.getReport(
                        paddyId
                );


        // =====================================================
        // 4. HULLER
        // =====================================================

        /*
         * Dryer gives us the BinName.
         *
         * Example:
         *
         * Paddy ID = 10
         * BinName  = BIN4
         *
         * HullerReportService will then search:
         *
         * PADDY_INPUT_BIN = BIN4
         * PADDY_SILO_4_ID = 10
         *
         * HullerReportService finds:
         *
         * BATCH_STATUS = 1 -> Start DateTime
         * BATCH_STATUS = 2 -> End DateTime
         */

        String binName = dryer.getBinName();

        HullerReportDto huller =
                hullerReportService.getHullerReport(
                        paddyId,
                        binName
                );


        // =====================================================
        // 5. COMPLETE BATCH REPORT
        // =====================================================

// =====================================================
// 5. BATCH SUMMARY
// =====================================================

// Batch ID
Integer batchId = paddyId;

// Batch Name
String batchName = huller.getMaterial();

// Batch start = earliest process start
LocalDateTime batchStartTime =
        paddyProduction.getStartTime();

if (thombai.getStartTime().isBefore(batchStartTime)) {
    batchStartTime = thombai.getStartTime();
}

if (dryer.getStartTime().isBefore(batchStartTime)) {
    batchStartTime = dryer.getStartTime();
}

if (huller.getStartDateTime().isBefore(batchStartTime)) {
    batchStartTime = huller.getStartDateTime();
}


// Batch end = latest process end
LocalDateTime batchEndTime =
        paddyProduction.getEndTime();

if (thombai.getEndTime().isAfter(batchEndTime)) {
    batchEndTime = thombai.getEndTime();
}

if (dryer.getEndTime().isAfter(batchEndTime)) {
    batchEndTime = dryer.getEndTime();
}

if (huller.getEndDateTime().isAfter(batchEndTime)) {
    batchEndTime = huller.getEndDateTime();
}


// Total process time
long totalProcessTimeMinutes =
        java.time.Duration.between(
                batchStartTime,
                batchEndTime
        ).toMinutes();


// =====================================================
// HULLER OUTPUT SUMMARY
// =====================================================

Float paddyWeight = huller.getPaddy();
Float riceOut = huller.getRice();
Float broken = huller.getBroken();
Float loss = huller.getLoss();

Float ricePercent = huller.getRicePercent();
Float brokenPercent = huller.getBrokenPercent();
Float lossPercent = huller.getLossPercent();

Float totalEnergy = huller.getKwh();


// =====================================================
// RETURN COMPLETE REPORT
// =====================================================

return new BatchReportDto(

        batchId,
        batchName,

        batchStartTime,
        batchEndTime,

        totalProcessTimeMinutes,

        paddyWeight,
        riceOut,
        broken,
        loss,

        ricePercent,
        brokenPercent,
        lossPercent,

        totalEnergy,

        paddyProduction,
        thombai,
        dryer,
        huller
);
    }


    // =========================================================
// GET ALL BATCHES BETWEEN START AND END DATE/TIME
// =========================================================

public List<BatchListDto> getBatches(
        LocalDateTime startTime,
        LocalDateTime endTime) {

    List<BatchListDto> batches = new ArrayList<>();

    // =====================================================
    // GET COMPLETED HULLER BATCHES
    // BATCH_STATUS = 2
    // =====================================================

    List<Huller> completedRecords =
            hullerRepository
                    .findByDatetimeFieldBetweenAndBatchStatusOrderByDatetimeFieldAsc(
                            startTime,
                            endTime,
                            2
                    );


    // =====================================================
    // PROCESS EACH COMPLETED BATCH
    // =====================================================

    for (Huller huller : completedRecords) {

        String binName = huller.getPaddyInputBin();

        if (binName == null ||
                binName.trim().isEmpty()) {
            continue;
        }


        String normalizedBin =
                binName.trim()
                        .toUpperCase()
                        .replace(" ", "");


        Integer paddyId = null;
        String material = null;


        // =================================================
        // GET PADDY ID FROM CORRECT SILO
        // =================================================

        switch (normalizedBin) {

            case "BIN1":
            case "SILO1":

                paddyId = huller.getPaddySilo1Id();
                material = huller.getPaddyName1();
                break;


            case "BIN2":
            case "SILO2":

                paddyId = huller.getPaddySilo2Id();
                material = huller.getPaddyName2();
                break;


            case "BIN3":
            case "SILO3":

                paddyId = huller.getPaddySilo3Id();
                material = huller.getPaddyName3();
                break;


            case "BIN4":
            case "SILO4":

                paddyId = huller.getPaddySilo4Id();
                material = huller.getPaddyName4();
                break;


            default:
                continue;
        }


        // =================================================
        // INVALID PADDY ID
        // =================================================

        if (paddyId == null) {
            continue;
        }


        // =================================================
        // FIND MATCHING HULLER START
        // STATUS = 1
        // =================================================

        Huller startRecord =
                findHullerStartRecord(
                        paddyId,
                        normalizedBin,
                        huller.getDatetimeField()
                );


        if (startRecord == null) {
            continue;
        }


        // =================================================
        // CREATE COMPLETE BATCH LIST
        // =================================================

        BatchListDto dto = new BatchListDto();

        dto.setPaddyId(paddyId);

        dto.setMaterial(material);

        // HULLER STATUS = 1
        // Batch START DateTime
        dto.setStartDateTime(
                startRecord.getDatetimeField()
        );

        // HULLER STATUS = 2
        // Batch END DateTime
        dto.setEndDateTime(
                huller.getDatetimeField()
        );


        batches.add(dto);
    }


    return batches;
}

private Huller findHullerStartRecord(
        Integer paddyId,
        String binName,
        LocalDateTime endTime) {

    List<Huller> records =
            hullerRepository
                    .findByDatetimeFieldBetweenOrderByDatetimeFieldAsc(
                            LocalDateTime.of(2000, 1, 1, 0, 0),
                            endTime
                    );

    Huller startRecord = null;

    for (Huller huller : records) {

        String inputBin = huller.getPaddyInputBin();

        if (inputBin == null) {
            continue;
        }

        String normalizedInputBin =
                inputBin.trim()
                        .toUpperCase()
                        .replace(" ", "");


        if (!normalizedInputBin.equals(binName)) {
            continue;
        }


        Integer siloPaddyId = null;


        switch (binName) {

            case "BIN1":
            case "SILO1":
                siloPaddyId = huller.getPaddySilo1Id();
                break;

            case "BIN2":
            case "SILO2":
                siloPaddyId = huller.getPaddySilo2Id();
                break;

            case "BIN3":
            case "SILO3":
                siloPaddyId = huller.getPaddySilo3Id();
                break;

            case "BIN4":
            case "SILO4":
                siloPaddyId = huller.getPaddySilo4Id();
                break;

            default:
                continue;
        }


        if (siloPaddyId == null ||
                !siloPaddyId.equals(paddyId)) {
            continue;
        }


        if (Integer.valueOf(1).equals(
                huller.getBatchStatus())) {

            startRecord = huller;
        }
    }


    return startRecord;
}

}