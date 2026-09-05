package com.annamalayarrice.service;

import com.annamalayarrice.dto.DryerReportDto;
import com.annamalayarrice.dto.DryerStepDto;
import com.annamalayarrice.dto.DryerTemperatureDto;
import com.annamalayarrice.entity.Dryer1;
import com.annamalayarrice.entity.Dryer2;
import com.annamalayarrice.entity.Thombai;
import com.annamalayarrice.repository.D1TempLogRepository;
import com.annamalayarrice.repository.D2TempLogRepository;
import com.annamalayarrice.repository.Dryer1Repository;
import com.annamalayarrice.repository.Dryer2Repository;
import com.annamalayarrice.repository.ThombaiRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DryerReportService {

    private final ThombaiRepository thombaiRepository;

    private final Dryer1Repository dryer1Repository;

    private final Dryer2Repository dryer2Repository;

    private final D1TempLogRepository d1TempLogRepository;

    private final D2TempLogRepository d2TempLogRepository;


    // =====================================================
    // MAIN DRYER REPORT
    // =====================================================

    public DryerReportDto getReport(Integer paddyId) {

        // =====================================================
        // 1. GET THOMBAI RECORD
        // =====================================================

        Thombai thombai =
                thombaiRepository
                        .findStart(paddyId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Thombai START not found for PaddyID: "
                                                + paddyId
                                )
                        );


        // =====================================================
        // 2. GET DRYER STATUS
        // =====================================================

        String dryerStatus =
                thombai.getDryerStatus();


        if (dryerStatus == null ||
                dryerStatus.trim().isEmpty()) {

            throw new RuntimeException(
                    "DryerStatus not found for PaddyID: "
                            + paddyId
            );
        }


        // =====================================================
        // 3. DRYER 1
        // =====================================================

        if (dryerStatus.equalsIgnoreCase("DRYER 1")) {

            Dryer1 start =
                    dryer1Repository
                            .findStart(paddyId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Dryer 1 START not found for PaddyID: "
                                                    + paddyId
                                    )
                            );


            Dryer1 stop =
                    dryer1Repository
                            .findStop(
                                    paddyId,
                                    start.getLogTime()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Dryer 1 STOP not found for PaddyID: "
                                                    + paddyId
                                    )
                            );


            return buildDryer1Report(
                    start,
                    stop,
                    paddyId,
                    dryerStatus
            );
        }


        // =====================================================
        // 4. DRYER 2
        // =====================================================

        if (dryerStatus.equalsIgnoreCase("DRYER 2")) {

            Dryer2 start =
                    dryer2Repository
                            .findStart(paddyId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Dryer 2 START not found for PaddyID: "
                                                    + paddyId
                                    )
                            );


            Dryer2 stop =
                    dryer2Repository
                            .findStop(
                                    paddyId,
                                    start.getLogTime()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Dryer 2 STOP not found for PaddyID: "
                                                    + paddyId
                                    )
                            );


            return buildDryer2Report(
                    start,
                    stop,
                    paddyId,
                    dryerStatus
            );
        }


        throw new RuntimeException(
                "Invalid DryerStatus: "
                        + dryerStatus
        );
    }


    // =====================================================
    // DRYER 1 REPORT
    // =====================================================

    private DryerReportDto buildDryer1Report(
            Dryer1 start,
            Dryer1 stop,
            Integer paddyId,
            String dryerStatus
    ) {

        LocalDateTime startTime =
                start.getLogTime();

        LocalDateTime endTime =
                stop.getLogTime();


        long durationMinutes =
                Duration.between(
                        startTime,
                        endTime
                ).toMinutes();


        List<DryerStepDto> steps =
                buildDryer1Steps(stop);


        List<DryerTemperatureDto> temperature =
                buildDryer1Temperature(
                        startTime,
                        endTime
                );


        return new DryerReportDto(

                paddyId,

                stop.getPaddyName(),

                dryerStatus,

                stop.getBinName(),

                startTime,

                endTime,

                durationMinutes,

                stop.getTotalRunMin(),

                stop.getTotalRunHour(),

                steps.size(),

                steps,

                temperature
        );
    }


    // =====================================================
    // DRYER 2 REPORT
    // =====================================================

    private DryerReportDto buildDryer2Report(
            Dryer2 start,
            Dryer2 stop,
            Integer paddyId,
            String dryerStatus
    ) {

        LocalDateTime startTime =
                start.getLogTime();

        LocalDateTime endTime =
                stop.getLogTime();


        long durationMinutes =
                Duration.between(
                        startTime,
                        endTime
                ).toMinutes();


        List<DryerStepDto> steps =
                buildDryer2Steps(stop);


        List<DryerTemperatureDto> temperature =
                buildDryer2Temperature(
                        startTime,
                        endTime
                );


        return new DryerReportDto(

                paddyId,

                stop.getPaddyName(),

                dryerStatus,

                stop.getBinName(),

                startTime,

                endTime,

                durationMinutes,

                stop.getTotalRunMin(),

                stop.getTotalRunHour(),

                steps.size(),

                steps,

                temperature
        );
    }


    // =====================================================
    // DRYER 1 STEPS
    // =====================================================

    private List<DryerStepDto> buildDryer1Steps(
            Dryer1 data
    ) {

        List<DryerStepDto> steps =
                new ArrayList<>();


        addStep(
                steps,
                1,
                data.getStep1SetTemp(),
                data.getStep1SetMin(),
                data.getStep1SetHour(),
                data.getStep1SetBlowerHz(),
                data.getStep1SetRollerHz()
        );

        addStep(
                steps,
                2,
                data.getStep2SetTemp(),
                data.getStep2SetMin(),
                data.getStep2SetHour(),
                data.getStep2SetBlowerHz(),
                data.getStep2SetRollerHz()
        );

        addStep(
                steps,
                3,
                data.getStep3SetTemp(),
                data.getStep3SetMin(),
                data.getStep3SetHour(),
                data.getStep3SetBlowerHz(),
                data.getStep3SetRollerHz()
        );

        addStep(
                steps,
                4,
                data.getStep4SetTemp(),
                data.getStep4SetMin(),
                data.getStep4SetHour(),
                data.getStep4SetBlowerHz(),
                data.getStep4SetRollerHz()
        );

        addStep(
                steps,
                5,
                data.getStep5SetTemp(),
                data.getStep5SetMin(),
                data.getStep5SetHour(),
                data.getStep5SetBlowerHz(),
                data.getStep5SetRollerHz()
        );

        addStep(
                steps,
                6,
                data.getStep6SetTemp(),
                data.getStep6SetMin(),
                data.getStep6SetHour(),
                data.getStep6SetBlowerHz(),
                data.getStep6SetRollerHz()
        );

        addStep(
                steps,
                7,
                data.getStep7SetTemp(),
                data.getStep7SetMin(),
                data.getStep7SetHour(),
                data.getStep7SetBlowerHz(),
                data.getStep7SetRollerHz()
        );

        addStep(
                steps,
                8,
                data.getStep8SetTemp(),
                data.getStep8SetMin(),
                data.getStep8SetHour(),
                data.getStep8SetBlowerHz(),
                data.getStep8SetRollerHz()
        );

        addStep(
                steps,
                9,
                data.getStep9SetTemp(),
                data.getStep9SetMin(),
                data.getStep9SetHour(),
                data.getStep9SetBlowerHz(),
                data.getStep9SetRollerHz()
        );

        addStep(
                steps,
                10,
                data.getStep10SetTemp(),
                data.getStep10SetMin(),
                data.getStep10SetHour(),
                data.getStep10SetBlowerHz(),
                data.getStep10SetRollerHz()
        );

        addStep(
                steps,
                11,
                data.getStep11SetTemp(),
                data.getStep11SetMin(),
                data.getStep11SetHour(),
                data.getStep11SetBlowerHz(),
                data.getStep11SetRollerHz()
        );

        addStep(
                steps,
                12,
                data.getStep12SetTemp(),
                data.getStep12SetMin(),
                data.getStep12SetHour(),
                data.getStep12SetBlowerHz(),
                data.getStep12SetRollerHz()
        );

        addStep(
                steps,
                13,
                data.getStep13SetTemp(),
                data.getStep13SetMin(),
                data.getStep13SetHour(),
                data.getStep13SetBlowerHz(),
                data.getStep13SetRollerHz()
        );

        addStep(
                steps,
                14,
                data.getStep14SetTemp(),
                data.getStep14SetMin(),
                data.getStep14SetHour(),
                data.getStep14SetBlowerHz(),
                data.getStep14SetRollerHz()
        );

        addStep(
                steps,
                15,
                data.getStep15SetTemp(),
                data.getStep15SetMin(),
                data.getStep15SetHour(),
                data.getStep15SetBlowerHz(),
                data.getStep15SetRollerHz()
        );

        addStep(
                steps,
                16,
                data.getStep16SetTemp(),
                data.getStep16SetMin(),
                data.getStep16SetHour(),
                data.getStep16SetBlowerHz(),
                data.getStep16SetRollerHz()
        );

        addStep(
                steps,
                17,
                data.getStep17SetTemp(),
                data.getStep17SetMin(),
                data.getStep17SetHour(),
                data.getStep17SetBlowerHz(),
                data.getStep17SetRollerHz()
        );

        addStep(
                steps,
                18,
                data.getStep18SetTemp(),
                data.getStep18SetMin(),
                data.getStep18SetHour(),
                data.getStep18SetBlowerHz(),
                data.getStep18SetRollerHz()
        );

        addStep(
                steps,
                19,
                data.getStep19SetTemp(),
                data.getStep19SetMin(),
                data.getStep19SetHour(),
                data.getStep19SetBlowerHz(),
                data.getStep19SetRollerHz()
        );

        addStep(
                steps,
                20,
                data.getStep20SetTemp(),
                data.getStep20SetMin(),
                data.getStep20SetHour(),
                data.getStep20SetBlowerHz(),
                data.getStep20SetRollerHz()
        );


        return steps;
    }


    // =====================================================
    // DRYER 2 STEPS
    // =====================================================

    private List<DryerStepDto> buildDryer2Steps(
            Dryer2 data
    ) {

        List<DryerStepDto> steps =
                new ArrayList<>();


        addStep(steps, 1,
                data.getStep1SetTemp(),
                data.getStep1SetMin(),
                data.getStep1SetHour(),
                data.getStep1SetBlowerHz(),
                data.getStep1SetRollerHz());

        addStep(steps, 2,
                data.getStep2SetTemp(),
                data.getStep2SetMin(),
                data.getStep2SetHour(),
                data.getStep2SetBlowerHz(),
                data.getStep2SetRollerHz());

        addStep(steps, 3,
                data.getStep3SetTemp(),
                data.getStep3SetMin(),
                data.getStep3SetHour(),
                data.getStep3SetBlowerHz(),
                data.getStep3SetRollerHz());

        addStep(steps, 4,
                data.getStep4SetTemp(),
                data.getStep4SetMin(),
                data.getStep4SetHour(),
                data.getStep4SetBlowerHz(),
                data.getStep4SetRollerHz());

        addStep(steps, 5,
                data.getStep5SetTemp(),
                data.getStep5SetMin(),
                data.getStep5SetHour(),
                data.getStep5SetBlowerHz(),
                data.getStep5SetRollerHz());

        addStep(steps, 6,
                data.getStep6SetTemp(),
                data.getStep6SetMin(),
                data.getStep6SetHour(),
                data.getStep6SetBlowerHz(),
                data.getStep6SetRollerHz());

        addStep(steps, 7,
                data.getStep7SetTemp(),
                data.getStep7SetMin(),
                data.getStep7SetHour(),
                data.getStep7SetBlowerHz(),
                data.getStep7SetRollerHz());

        addStep(steps, 8,
                data.getStep8SetTemp(),
                data.getStep8SetMin(),
                data.getStep8SetHour(),
                data.getStep8SetBlowerHz(),
                data.getStep8SetRollerHz());

        addStep(steps, 9,
                data.getStep9SetTemp(),
                data.getStep9SetMin(),
                data.getStep9SetHour(),
                data.getStep9SetBlowerHz(),
                data.getStep9SetRollerHz());

        addStep(steps, 10,
                data.getStep10SetTemp(),
                data.getStep10SetMin(),
                data.getStep10SetHour(),
                data.getStep10SetBlowerHz(),
                data.getStep10SetRollerHz());

        addStep(steps, 11,
                data.getStep11SetTemp(),
                data.getStep11SetMin(),
                data.getStep11SetHour(),
                data.getStep11SetBlowerHz(),
                data.getStep11SetRollerHz());

        addStep(steps, 12,
                data.getStep12SetTemp(),
                data.getStep12SetMin(),
                data.getStep12SetHour(),
                data.getStep12SetBlowerHz(),
                data.getStep12SetRollerHz());

        addStep(steps, 13,
                data.getStep13SetTemp(),
                data.getStep13SetMin(),
                data.getStep13SetHour(),
                data.getStep13SetBlowerHz(),
                data.getStep13SetRollerHz());

        addStep(steps, 14,
                data.getStep14SetTemp(),
                data.getStep14SetMin(),
                data.getStep14SetHour(),
                data.getStep14SetBlowerHz(),
                data.getStep14SetRollerHz());

        addStep(steps, 15,
                data.getStep15SetTemp(),
                data.getStep15SetMin(),
                data.getStep15SetHour(),
                data.getStep15SetBlowerHz(),
                data.getStep15SetRollerHz());

        addStep(steps, 16,
                data.getStep16SetTemp(),
                data.getStep16SetMin(),
                data.getStep16SetHour(),
                data.getStep16SetBlowerHz(),
                data.getStep16SetRollerHz());

        addStep(steps, 17,
                data.getStep17SetTemp(),
                data.getStep17SetMin(),
                data.getStep17SetHour(),
                data.getStep17SetBlowerHz(),
                data.getStep17SetRollerHz());

        addStep(steps, 18,
                data.getStep18SetTemp(),
                data.getStep18SetMin(),
                data.getStep18SetHour(),
                data.getStep18SetBlowerHz(),
                data.getStep18SetRollerHz());

        addStep(steps, 19,
                data.getStep19SetTemp(),
                data.getStep19SetMin(),
                data.getStep19SetHour(),
                data.getStep19SetBlowerHz(),
                data.getStep19SetRollerHz());

        addStep(steps, 20,
                data.getStep20SetTemp(),
                data.getStep20SetMin(),
                data.getStep20SetHour(),
                data.getStep20SetBlowerHz(),
                data.getStep20SetRollerHz());


        return steps;
    }


    // =====================================================
    // ADD ACTIVE STEP
    // =====================================================

    private void addStep(
            List<DryerStepDto> steps,
            Integer stepNo,
            Integer temperature,
            Integer minutes,
            Integer hours,
            Integer blowerHz,
            Integer rollerHz
    ) {

        /*
         * A step is considered active when at least one
         * configuration value contains a value.
         */

        boolean active =
                isActive(temperature)
                        || isActive(minutes)
                        || isActive(hours)
                        || isActive(blowerHz)
                        || isActive(rollerHz);


        if (!active) {
            return;
        }


        steps.add(
                new DryerStepDto(
                        stepNo,
                        temperature,
                        minutes,
                        hours,
                        blowerHz,
                        rollerHz
                )
        );
    }


    private boolean isActive(Integer value) {

        return value != null && value > 0;
    }


    // =====================================================
    // DRYER 1 ACTUAL TEMPERATURE
    // =====================================================

    private List<DryerTemperatureDto> buildDryer1Temperature(
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {

        List<Object[]> rows =
                d1TempLogRepository.findOneMinuteAverage(
                        startTime,
                        endTime
                );


        List<DryerTemperatureDto> result =
                new ArrayList<>();


        for (Object[] row : rows) {

            result.add(
                    new DryerTemperatureDto(
                            convertDateTime(row[0]),
                            toDouble(row[1]),
                            toDouble(row[2])
                    )
            );
        }


        return result;
    }


    // =====================================================
    // DRYER 2 ACTUAL TEMPERATURE
    // =====================================================

    private List<DryerTemperatureDto> buildDryer2Temperature(
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {

        List<Object[]> rows =
                d2TempLogRepository.findOneMinuteAverage(
                        startTime,
                        endTime
                );


        List<DryerTemperatureDto> result =
                new ArrayList<>();


        for (Object[] row : rows) {

            result.add(
                    new DryerTemperatureDto(
                            convertDateTime(row[0]),
                            toDouble(row[1]),
                            toDouble(row[2])
                    )
            );
        }


        return result;
    }


    // =====================================================
    // SQL NUMBER → DOUBLE
    // =====================================================

    private Double toDouble(Object value) {

        if (value == null) {
            return null;
        }

        return ((Number) value).doubleValue();
    }


    // =====================================================
    // SQL TIMESTAMP → LOCALDATETIME
    // =====================================================

    private LocalDateTime convertDateTime(
            Object value
    ) {

        if (value == null) {
            return null;
        }


        if (value instanceof java.sql.Timestamp timestamp) {

            return timestamp.toLocalDateTime();
        }


        if (value instanceof LocalDateTime localDateTime) {

            return localDateTime;
        }


        return LocalDateTime.parse(
                value.toString()
        );
    }
}