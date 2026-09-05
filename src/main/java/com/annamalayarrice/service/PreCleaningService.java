package com.annamalayarrice.service;


import com.annamalayarrice.dto.PreCleaningReportDto;
import com.annamalayarrice.entity.PaddyProduction;
import com.annamalayarrice.repository.PaddyProductionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class PreCleaningService {

    private final PaddyProductionRepository paddyProductionRepository;


    public PreCleaningReportDto getPreCleaningReport(
            Integer paddyId
    ) {

        // ---------------------------------------------
        // 1. FIND START
        // ---------------------------------------------
        PaddyProduction start =
                paddyProductionRepository
                        .findPreCleaningStart(paddyId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Pre-cleaning START not found for PaddyID: "
                                                + paddyId
                                )
                        );


        // ---------------------------------------------
        // 2. FIND STOP
        // ---------------------------------------------
        PaddyProduction stop =
                paddyProductionRepository
                        .findPreCleaningStop(
                                paddyId,
                                start.getLogTime()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Pre-cleaning STOP not found for PaddyID: "
                                                + paddyId
                                )
                        );


        // ---------------------------------------------
        // 3. START / END TIME
        // ---------------------------------------------
        var startTime = start.getLogTime();
        var endTime = stop.getLogTime();


        // ---------------------------------------------
        // 4. DURATION
        // ---------------------------------------------
        long durationMinutes =
                Duration.between(
                        startTime,
                        endTime
                ).toMinutes();


        // ---------------------------------------------
        // 5. WEIGHTS
        // ---------------------------------------------
        Double weightBefore =
                start.getPaddyActWeight() != null
                        ? start.getPaddyActWeight().doubleValue()
                        : null;

        Double weightAfter =
                stop.getPaddyActWeight() != null
                        ? stop.getPaddyActWeight().doubleValue()
                        : null;


        // ---------------------------------------------
        // 6. IMPURITIES REMOVED
        // ---------------------------------------------
        Double impuritiesRemoved = null;

        if (weightBefore != null && weightAfter != null) {

            impuritiesRemoved =
                    weightBefore - weightAfter;
        }


        // ---------------------------------------------
        // 7. LOSS %
        // ---------------------------------------------
        Double lossRatePercentage = null;

        if (weightBefore != null
                && weightBefore > 0
                && impuritiesRemoved != null) {

            lossRatePercentage =
                    (impuritiesRemoved / weightBefore) * 100;
        }


        // ---------------------------------------------
        // 8. ROUND VALUES
        // ---------------------------------------------
        if (impuritiesRemoved != null) {
            impuritiesRemoved =
                    Math.round(impuritiesRemoved * 100.0) / 100.0;
        }

        if (lossRatePercentage != null) {
            lossRatePercentage =
                    Math.round(lossRatePercentage * 100.0) / 100.0;
        }


        // ---------------------------------------------
        // 9. RETURN REPORT
        // ---------------------------------------------
        return new PreCleaningReportDto(
                startTime,
                endTime,
                durationMinutes,
                weightBefore,
                weightAfter,
                impuritiesRemoved,
                lossRatePercentage
        );
    }
}
