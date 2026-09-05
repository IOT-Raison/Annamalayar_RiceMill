package com.annamalayarrice.service;

import com.annamalayarrice.dto.HullerReportDto;
import com.annamalayarrice.entity.Huller;
import com.annamalayarrice.repository.HullerRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HullerReportService {

    private final HullerRepository hullerRepository;

    public HullerReportService(HullerRepository hullerRepository) {
        this.hullerRepository = hullerRepository;
    }

public HullerReportDto getHullerReport(
        Integer paddyId,
        String binName) {

    if (paddyId == null) {
        throw new RuntimeException("Paddy ID is required");
    }

    if (binName == null || binName.trim().isEmpty()) {
        throw new RuntimeException("BinName is required");
    }

    int binNumber = getBinNumber(binName);

String normalizedBin =
        getHullerSiloName(binNumber)
                .toUpperCase()
                .replace(" ", "");


    // =====================================================
    // GET HULLER RECORDS IN DESCENDING ORDER
    // =====================================================

    List<Huller> records =
            hullerRepository.findAllByOrderByDatetimeFieldDesc();


    Huller completedRecord = null;
    Huller startRecord = null;


    // =====================================================
    // FIND BATCH STATUS = 2
    // =====================================================

    for (Huller huller : records) {

        String inputBin = huller.getPaddyInputBin();

        if (inputBin == null) {
            continue;
        }

        String normalizedInputBin =
                inputBin.trim()
                        .toUpperCase()
                        .replace(" ", "");

        if (!normalizedInputBin.equals(normalizedBin)) {
            continue;
        }

        Integer siloPaddyId =
                getPaddySiloId(
                        huller,
                        binNumber
                );

        if (siloPaddyId == null ||
                !siloPaddyId.equals(paddyId)) {
            continue;
        }

        // First matching STATUS = 2
        // because records are DESCENDING
        if (Integer.valueOf(2).equals(
                huller.getBatchStatus())) {

            completedRecord = huller;
            break;
        }
    }


    if (completedRecord == null) {

        throw new RuntimeException(
                "Completed HULLER batch not found for " +
                "PaddyID: " + paddyId +
                ", BinName: " + binName
        );
    }


    // =====================================================
    // FIND BATCH STATUS = 1
    // =====================================================

    for (Huller huller : records) {

        String inputBin = huller.getPaddyInputBin();

        if (inputBin == null) {
            continue;
        }

        String normalizedInputBin =
                inputBin.trim()
                        .toUpperCase()
                        .replace(" ", "");

        if (!normalizedInputBin.equals(normalizedBin)) {
            continue;
        }

        Integer siloPaddyId =
                getPaddySiloId(
                        huller,
                        binNumber
                );

        if (siloPaddyId == null ||
                !siloPaddyId.equals(paddyId)) {
            continue;
        }

        // Must be before or equal to completed record
        if (huller.getDatetimeField()
                .isAfter(
                        completedRecord.getDatetimeField()
                )) {
            continue;
        }

        if (Integer.valueOf(1).equals(
                huller.getBatchStatus())) {

            startRecord = huller;
            break;
        }
    }


    if (startRecord == null) {

        throw new RuntimeException(
                "HULLER start record not found for " +
                "PaddyID: " + paddyId +
                ", BinName: " + binName
        );
    }


    // =====================================================
    // GET VALUES FROM COMPLETED RECORD
    // =====================================================

    Float paddyKg =
            completedRecord.getPaddyKg();

    Float riceKg =
            completedRecord.getRiceKg();

    Float brokenKg =
            completedRecord.getBrokenKg();

    Float kwh =
            completedRecord.getKwh();

    String material =
            getPaddyName(
                    completedRecord,
                    binNumber
            );


    // =====================================================
    // CALCULATE RICE %, BROKEN %, LOSS KG, LOSS %
    // =====================================================

    Float ricePercent = 0.0f;
    Float brokenPercent = 0.0f;
    Float lossKg = 0.0f;
    Float lossPercent = 0.0f;

    if (paddyKg != null && paddyKg > 0) {

        if (riceKg == null) {
            riceKg = 0.0f;
        }

        if (brokenKg == null) {
            brokenKg = 0.0f;
        }


        // Rice %
        ricePercent =
                (riceKg / paddyKg) * 100.0f;


        // Broken %
        brokenPercent =
                (brokenKg / paddyKg) * 100.0f;


        // Loss KG
        lossKg =
                paddyKg - riceKg - brokenKg;


        // Loss %
        lossPercent =
                (lossKg / paddyKg) * 100.0f;
    }


    // =====================================================
    // RETURN HULLER REPORT
    // =====================================================

    return new HullerReportDto(

            startRecord.getDatetimeField(),

            completedRecord.getDatetimeField(),

            paddyId,

            material,

            paddyKg,

            riceKg,

            brokenKg,

            kwh,

            ricePercent,

            brokenPercent,

            lossKg,

            lossPercent
    );
}


    // =========================================================
    // BIN NAME
    // =========================================================

private int getBinNumber(String binName) {

    if (binName == null || binName.trim().isEmpty()) {
        throw new RuntimeException("Bin name is empty");
    }

    String normalized = binName
            .trim()
            .toUpperCase()
            .replace(" ", "");

    switch (normalized) {
        case "BIN1":
        case "SILO1":
            return 1;

        case "BIN2":
        case "SILO2":
            return 2;

        case "BIN3":
        case "SILO3":
            return 3;

        case "BIN4":
        case "SILO4":
            return 4;

        default:
            throw new RuntimeException(
                    "Unsupported BinName: " + binName
            );
    }
}

private String getHullerSiloName(int binNumber) {

    switch (binNumber) {
        case 1:
            return "SILO 1";

        case 2:
            return "SILO 2";

        case 3:
            return "SILO 3";

        case 4:
            return "SILO 4";

        default:
            throw new RuntimeException(
                    "Unsupported bin number: " + binNumber
            );
    }
}

    // =========================================================
    // PADDY SILO ID
    // =========================================================

    private Integer getPaddySiloId(
            Huller huller,
            int binNumber) {

        switch (binNumber) {

            case 1:
                return huller.getPaddySilo1Id();

            case 2:
                return huller.getPaddySilo2Id();

            case 3:
                return huller.getPaddySilo3Id();

            case 4:
                return huller.getPaddySilo4Id();

            default:
                return null;
        }
    }


    // =========================================================
    // PADDY NAME / MATERIAL
    // =========================================================

    private String getPaddyName(
            Huller huller,
            int binNumber) {

        switch (binNumber) {

            case 1:
                return huller.getPaddyName1();

            case 2:
                return huller.getPaddyName2();

            case 3:
                return huller.getPaddyName3();

            case 4:
                return huller.getPaddyName4();

            default:
                return null;
        }
    }

public List<HullerReportDto> getCompletedHullerReports(
        LocalDateTime startTime,
        LocalDateTime endTime) {

    List<Huller> completedRecords =
            hullerRepository
                    .findByDatetimeFieldBetweenAndBatchStatusOrderByDatetimeFieldAsc(
                            startTime,
                            endTime,
                            2
                    );

    List<HullerReportDto> reports = new ArrayList<>();

    for (Huller endRecord : completedRecords) {

        String binName = endRecord.getPaddyInputBin();

        if (binName == null) {
            continue;
        }

        // =====================================================
        // GET PADDY ID FROM EXACT SILO
        // =====================================================

        Integer paddyId =
                getPaddyIdFromInputBin(endRecord);

        if (paddyId == null) {
            continue;
        }


        // =====================================================
        // FIND HULLER START RECORD
        // STATUS = 1
        // =====================================================

        Huller startRecord =
                findStartRecord(
                        paddyId,
                        binName,
                        endRecord.getDatetimeField()
                );

        if (startRecord == null) {
            continue;
        }


        // =====================================================
        // MATERIAL
        // =====================================================

        String material =
                getMaterialFromInputBin(endRecord);


        // =====================================================
        // GET VALUES
        // =====================================================

        Float paddy =
                endRecord.getPaddyKg();

        Float rice =
                endRecord.getRiceKg();

        Float broken =
                endRecord.getBrokenKg();

        Float kwh =
                endRecord.getKwh();


        // =====================================================
        // CALCULATE %
        // =====================================================

        Float ricePercent = 0.0f;
        Float brokenPercent = 0.0f;
        Float lossKg = 0.0f;
        Float lossPercent = 0.0f;


        if (paddy != null && paddy > 0) {

            if (rice == null) {
                rice = 0.0f;
            }

            if (broken == null) {
                broken = 0.0f;
            }


            // ---------------------------------------------
            // RICE %
            // ---------------------------------------------

            ricePercent =
                    (rice / paddy) * 100.0f;


            // ---------------------------------------------
            // BROKEN %
            // ---------------------------------------------

            brokenPercent =
                    (broken / paddy) * 100.0f;


            // ---------------------------------------------
            // LOSS KG
            // ---------------------------------------------

            lossKg =
                    paddy - rice - broken;


            // ---------------------------------------------
            // LOSS %
            // ---------------------------------------------

            lossPercent =
                    (lossKg / paddy) * 100.0f;
        }


        // =====================================================
        // CREATE DTO
        // =====================================================

        HullerReportDto dto =
                new HullerReportDto();


        dto.setStartDateTime(
                startRecord.getDatetimeField()
        );

        dto.setEndDateTime(
                endRecord.getDatetimeField()
        );

        dto.setPaddyId(
                paddyId
        );

        dto.setMaterial(
                material
        );

        dto.setPaddy(
                paddy
        );

        dto.setRice(
                rice
        );

        dto.setBroken(
                broken
        );

        dto.setKwh(
                kwh
        );

        // NEW FIELDS
        dto.setRicePercent(
                ricePercent
        );

        dto.setBrokenPercent(
                brokenPercent
        );

        dto.setLoss(
                lossKg
        );

        dto.setLossPercent(
                lossPercent
        );


        reports.add(dto);
    }

    return reports;
}
private Integer getPaddyIdFromInputBin(Huller huller) {

    String bin = huller.getPaddyInputBin();

    if (bin == null) {
        return null;
    }

    bin = bin.trim()
            .toUpperCase()
            .replace(" ", "");

    switch (bin) {

        case "BIN1":
        case "SILO1":
            return huller.getPaddySilo1Id();

        case "BIN2":
        case "SILO2":
            return huller.getPaddySilo2Id();

        case "BIN3":
        case "SILO3":
            return huller.getPaddySilo3Id();

        case "BIN4":
        case "SILO4":
            return huller.getPaddySilo4Id();

        default:
            return null;
    }
}

private String getMaterialFromInputBin(Huller huller) {

    String bin = huller.getPaddyInputBin();

    if (bin == null) {
        return null;
    }

    bin = bin.trim()
            .toUpperCase()
            .replace(" ", "");

    switch (bin) {

        case "BIN1":
        case "SILO1":
            return huller.getPaddyName1();

        case "BIN2":
        case "SILO2":
            return huller.getPaddyName2();

        case "BIN3":
        case "SILO3":
            return huller.getPaddyName3();

        case "BIN4":
        case "SILO4":
            return huller.getPaddyName4();

        default:
            return null;
    }
}

private Huller findStartRecord(
        Integer paddyId,
        String binName,
        LocalDateTime endTime) {

    if (binName == null) {
        return null;
    }

    String bin = binName.trim()
            .toUpperCase()
            .replace(" ", "");

    List<Huller> records;

    switch (bin) {

        case "BIN1":
        case "SILO1":

            records = hullerRepository.findStartForBin1(
                    paddyId,
                    endTime
            );
            break;

        case "BIN2":
        case "SILO2":

            records = hullerRepository.findStartForBin2(
                    paddyId,
                    endTime
            );
            break;

        case "BIN3":
        case "SILO3":

            records = hullerRepository.findStartForBin3(
                    paddyId,
                    endTime
            );
            break;

        case "BIN4":
        case "SILO4":

            records = hullerRepository.findStartForBin4(
                    paddyId,
                    endTime
            );
            break;

        default:
            throw new RuntimeException(
                    "Invalid Huller BinName: " + binName
            );
    }

    if (records.isEmpty()) {
        return null;
    }

    return records.get(0);
}
}