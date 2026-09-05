package com.annamalayarrice.service;

import com.annamalayarrice.dto.ThombaiCycleDto;
import com.annamalayarrice.dto.ThombaiReportDto;
import com.annamalayarrice.dto.ThombaiTankDto;
import com.annamalayarrice.dto.ThombaiTemperatureDto;
import com.annamalayarrice.entity.Steam;
import com.annamalayarrice.entity.Thombai;
import com.annamalayarrice.repository.SteamRepository;
import com.annamalayarrice.repository.ThombaiActTempRepository;
import com.annamalayarrice.repository.ThombaiRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ThombaiReportService {

    private final ThombaiRepository thombaiRepository;

    private final ThombaiActTempRepository thombaiActTempRepository;

    private final SteamRepository steamRepository;


    // =====================================================
    // MAIN THOMBAI REPORT
    // =====================================================

    public ThombaiReportDto getReport(Integer paddyId) {

        // =====================================================
        // 1. FIND THOMBAI START
        // MachineStatus = 1
        // =====================================================

        Thombai start =
                thombaiRepository
                        .findStart(paddyId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Thombai START not found for PaddyID: "
                                                + paddyId
                                )
                        );


        // =====================================================
        // 2. FIND THOMBAI STOP
        // MachineStatus = 2
        // =====================================================

        Thombai stop =
                thombaiRepository
                        .findStop(
                                paddyId,
                                start.getLogTime()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Thombai STOP not found for PaddyID: "
                                                + paddyId
                                )
                        );


        // =====================================================
        // 3. THOMBAI START / END TIME
        // =====================================================

        LocalDateTime startTime =
                start.getLogTime();

        LocalDateTime endTime =
                stop.getLogTime();


        // =====================================================
        // 4. THOMBAI TOTAL DURATION
        //
        // Duration = End - Start
        // =====================================================

        long durationMinutes =
                Duration.between(
                        startTime,
                        endTime
                ).toMinutes();


        // =====================================================
        // 5. FIND PRE-STEAMING START
        //
        // STEAM MachineStatus = 1
        // =====================================================

        Steam preSteamingStart =
                steamRepository
                        .findStart(paddyId)
                        .orElse(null);


        // =====================================================
        // 6. FIND PRE-STEAMING STOP
        //
        // STEAM MachineStatus = 2
        // =====================================================

        Steam preSteamingStop = null;

        if (preSteamingStart != null) {

            preSteamingStop =
                    steamRepository
                            .findStop(
                                    paddyId,
                                    preSteamingStart.getLogTime()
                            )
                            .orElse(null);
        }


        // =====================================================
        // 7. PRE-STEAMING TIME
        // =====================================================

        LocalDateTime preSteamingStartTime = null;

        LocalDateTime preSteamingEndTime = null;

        Long preSteamingDurationMinutes = null;


        if (preSteamingStart != null) {

            preSteamingStartTime =
                    preSteamingStart.getLogTime();
        }


        if (preSteamingStop != null) {

            preSteamingEndTime =
                    preSteamingStop.getLogTime();
        }


        if (preSteamingStartTime != null
                && preSteamingEndTime != null) {

            preSteamingDurationMinutes =
                    Duration.between(
                            preSteamingStartTime,
                            preSteamingEndTime
                    ).toMinutes();
        }


        // =====================================================
        // 8. THOMBAI ACTUAL TEMPERATURE
        //
        // THOMBAI_ACT_TEMP does not contain PaddyID.
        //
        // Therefore:
        //
        // Thombai START
        //       ↓
        // Actual temperature records
        //       ↓
        // Thombai STOP
        //
        // Repository returns 1-minute averages.
        // =====================================================

        List<Object[]> rawTemperature =
                thombaiActTempRepository.findOneMinuteAverage(
                        startTime,
                        endTime
                );


        List<ThombaiTemperatureDto> temperature =
                rawTemperature.stream()
                        .map(row ->
                                new ThombaiTemperatureDto(

                                        convertDateTime(row[0]),

                                        toDouble(row[1]),

                                        toDouble(row[2]),

                                        toDouble(row[3]),

                                        toDouble(row[4]),

                                        toDouble(row[5]),

                                        toDouble(row[6])
                                )
                        )
                        .toList();


        // =====================================================
        // 9. BUILD SIX VALVES
        //
        // Each valve has its own:
        // - Temperature
        // - On delay
        // - Boiling time
        // - Cycle count
        // - Cycle list
        // - Total cycle time
        //
        // Example:
        //
        // V1 = 6 cycles
        // V2 = 9 cycles
        // V3 = 5 cycles
        //
        // Each valve is independent.
        // =====================================================

        List<ThombaiTankDto> valves =
                buildValveData(stop);


        // =====================================================
        // 10. AVERAGE BOILING TIME
        //
        // Only valves having active cycles
        // are included.
        // =====================================================

        Double averageBoilingTime =
                calculateAverageBoilingTime(stop);


        // =====================================================
        // 11. RETURN COMPLETE THOMBAI REPORT
        // =====================================================

        return new ThombaiReportDto(

                // -------------------------------------------------
                // THOMBAI TIME
                // -------------------------------------------------

                startTime,

                endTime,

                durationMinutes,


                // -------------------------------------------------
                // PRE-STEAMING TIME
                // -------------------------------------------------

                preSteamingStartTime,

                preSteamingEndTime,

                preSteamingDurationMinutes,


                // -------------------------------------------------
                // THOMBAI PARAMETERS
                // -------------------------------------------------

                averageBoilingTime,

                start.getDryerStatus(),

                stop.getTotalValveCount(),

                stop.getTotalMin(),

                stop.getTotalHour(),


                // -------------------------------------------------
                // VALVES
                // -------------------------------------------------

                valves,


                // -------------------------------------------------
                // ACTUAL TEMPERATURE
                // -------------------------------------------------

                temperature
        );
    }


    // =====================================================
    // BUILD SIX VALVE DATA
    // =====================================================

    private List<ThombaiTankDto> buildValveData(
            Thombai data
    ) {

        List<ThombaiTankDto> valves =
                new ArrayList<>();


        // =====================================================
        // VALVE 1
        // =====================================================

        valves.add(
                buildValve(
                        1,

                        data.getV1Temperature(),

                        data.getV1OnDly(),

                        data.getV1BoilTime(),

                        data.getV1Step1(),
                        data.getV1Step2(),
                        data.getV1Step3(),
                        data.getV1Step4(),
                        data.getV1Step5(),
                        data.getV1Step6(),
                        data.getV1Step7(),
                        data.getV1Step8(),
                        data.getV1Step9(),
                        data.getV1Step10(),
                        data.getV1Step11(),
                        data.getV1Step12(),
                        data.getV1Step13()
                )
        );


        // =====================================================
        // VALVE 2
        // =====================================================

        valves.add(
                buildValve(
                        2,

                        data.getV2Temperature(),

                        data.getV2OnDly(),

                        data.getV2BoilTime(),

                        data.getV2Step1(),
                        data.getV2Step2(),
                        data.getV2Step3(),
                        data.getV2Step4(),
                        data.getV2Step5(),
                        data.getV2Step6(),
                        data.getV2Step7(),
                        data.getV2Step8(),
                        data.getV2Step9(),
                        data.getV2Step10(),
                        data.getV2Step11(),
                        data.getV2Step12(),
                        data.getV2Step13()
                )
        );


        // =====================================================
        // VALVE 3
        // =====================================================

        valves.add(
                buildValve(
                        3,

                        data.getV3Temperature(),

                        data.getV3OnDly(),

                        data.getV3BoilTime(),

                        data.getV3Step1(),
                        data.getV3Step2(),
                        data.getV3Step3(),
                        data.getV3Step4(),
                        data.getV3Step5(),
                        data.getV3Step6(),
                        data.getV3Step7(),
                        data.getV3Step8(),
                        data.getV3Step9(),
                        data.getV3Step10(),
                        data.getV3Step11(),
                        data.getV3Step12(),
                        data.getV3Step13()
                )
        );


        // =====================================================
        // VALVE 4
        // =====================================================

        valves.add(
                buildValve(
                        4,

                        data.getV4Temperature(),

                        data.getV4OnDly(),

                        data.getV4BoilTime(),

                        data.getV4Step1(),
                        data.getV4Step2(),
                        data.getV4Step3(),
                        data.getV4Step4(),
                        data.getV4Step5(),
                        data.getV4Step6(),
                        data.getV4Step7(),
                        data.getV4Step8(),
                        data.getV4Step9(),
                        data.getV4Step10(),
                        data.getV4Step11(),
                        data.getV4Step12(),
                        data.getV4Step13()
                )
        );


        // =====================================================
        // VALVE 5
        // =====================================================

        valves.add(
                buildValve(
                        5,

                        data.getV5Temperature(),

                        data.getV5OnDly(),

                        data.getV5BoilTime(),

                        data.getV5Step1(),
                        data.getV5Step2(),
                        data.getV5Step3(),
                        data.getV5Step4(),
                        data.getV5Step5(),
                        data.getV5Step6(),
                        data.getV5Step7(),
                        data.getV5Step8(),
                        data.getV5Step9(),
                        data.getV5Step10(),
                        data.getV5Step11(),
                        data.getV5Step12(),
                        data.getV5Step13()
                )
        );


        // =====================================================
        // VALVE 6
        // =====================================================

        valves.add(
                buildValve(
                        6,

                        data.getV6Temperature(),

                        data.getV6OnDly(),

                        data.getV6BoilTime(),

                        data.getV6Step1(),
                        data.getV6Step2(),
                        data.getV6Step3(),
                        data.getV6Step4(),
                        data.getV6Step5(),
                        data.getV6Step6(),
                        data.getV6Step7(),
                        data.getV6Step8(),
                        data.getV6Step9(),
                        data.getV6Step10(),
                        data.getV6Step11(),
                        data.getV6Step12(),
                        data.getV6Step13()
                )
        );


        return valves;
    }


    // =====================================================
    // BUILD ONE VALVE
    // =====================================================

private ThombaiTankDto buildValve(
        Integer valveNo,
        Integer temperature,
        Integer onDelay,
        Integer boilingTime,
        Integer... stepValues
) {
    List<ThombaiCycleDto> cycles = new ArrayList<>();

    for (int i = 0; i < stepValues.length; i++) {

        Integer seconds = stepValues[i];

        // Only include populated cycles
        if (seconds != null && seconds > 0) {
            cycles.add(
                new ThombaiCycleDto(
                    i + 1,
                    seconds
                )
            );
        }
    }

    int cycleCount = cycles.size();

    int totalCycleTime = cycles.stream()
            .mapToInt(ThombaiCycleDto::getTimeSeconds)
            .sum();

    return new ThombaiTankDto(
            valveNo,
            temperature != null ? temperature.doubleValue() : null,
            onDelay,
            boilingTime,
            cycleCount,
            totalCycleTime,
            cycles
    );
}


    // =====================================================
    // AVERAGE BOILING TIME
    //
    // Only valves with at least one active cycle
    // are included.
    // =====================================================

    private Double calculateAverageBoilingTime(
            Thombai data
    ) {

        List<Integer> boilingTimes =
                new ArrayList<>();


        // =====================================================
        // V1
        // =====================================================

        if (hasValveCycles(
                data.getV1Step1(),
                data.getV1Step2(),
                data.getV1Step3(),
                data.getV1Step4(),
                data.getV1Step5(),
                data.getV1Step6(),
                data.getV1Step7(),
                data.getV1Step8(),
                data.getV1Step9(),
                data.getV1Step10(),
                data.getV1Step11(),
                data.getV1Step12(),
                data.getV1Step13()
        )) {

            if (data.getV1BoilTime() != null) {

                boilingTimes.add(
                        data.getV1BoilTime()
                );
            }
        }


        // =====================================================
        // V2
        // =====================================================

        if (hasValveCycles(
                data.getV2Step1(),
                data.getV2Step2(),
                data.getV2Step3(),
                data.getV2Step4(),
                data.getV2Step5(),
                data.getV2Step6(),
                data.getV2Step7(),
                data.getV2Step8(),
                data.getV2Step9(),
                data.getV2Step10(),
                data.getV2Step11(),
                data.getV2Step12(),
                data.getV2Step13()
        )) {

            if (data.getV2BoilTime() != null) {

                boilingTimes.add(
                        data.getV2BoilTime()
                );
            }
        }


        // =====================================================
        // V3
        // =====================================================

        if (hasValveCycles(
                data.getV3Step1(),
                data.getV3Step2(),
                data.getV3Step3(),
                data.getV3Step4(),
                data.getV3Step5(),
                data.getV3Step6(),
                data.getV3Step7(),
                data.getV3Step8(),
                data.getV3Step9(),
                data.getV3Step10(),
                data.getV3Step11(),
                data.getV3Step12(),
                data.getV3Step13()
        )) {

            if (data.getV3BoilTime() != null) {

                boilingTimes.add(
                        data.getV3BoilTime()
                );
            }
        }


        // =====================================================
        // V4
        // =====================================================

        if (hasValveCycles(
                data.getV4Step1(),
                data.getV4Step2(),
                data.getV4Step3(),
                data.getV4Step4(),
                data.getV4Step5(),
                data.getV4Step6(),
                data.getV4Step7(),
                data.getV4Step8(),
                data.getV4Step9(),
                data.getV4Step10(),
                data.getV4Step11(),
                data.getV4Step12(),
                data.getV4Step13()
        )) {

            if (data.getV4BoilTime() != null) {

                boilingTimes.add(
                        data.getV4BoilTime()
                );
            }
        }


        // =====================================================
        // V5
        // =====================================================

        if (hasValveCycles(
                data.getV5Step1(),
                data.getV5Step2(),
                data.getV5Step3(),
                data.getV5Step4(),
                data.getV5Step5(),
                data.getV5Step6(),
                data.getV5Step7(),
                data.getV5Step8(),
                data.getV5Step9(),
                data.getV5Step10(),
                data.getV5Step11(),
                data.getV5Step12(),
                data.getV5Step13()
        )) {

            if (data.getV5BoilTime() != null) {

                boilingTimes.add(
                        data.getV5BoilTime()
                );
            }
        }


        // =====================================================
        // V6
        // =====================================================

        if (hasValveCycles(
                data.getV6Step1(),
                data.getV6Step2(),
                data.getV6Step3(),
                data.getV6Step4(),
                data.getV6Step5(),
                data.getV6Step6(),
                data.getV6Step7(),
                data.getV6Step8(),
                data.getV6Step9(),
                data.getV6Step10(),
                data.getV6Step11(),
                data.getV6Step12(),
                data.getV6Step13()
        )) {

            if (data.getV6BoilTime() != null) {

                boilingTimes.add(
                        data.getV6BoilTime()
                );
            }
        }


        // =====================================================
        // NO BOILING TIME
        // =====================================================

        if (boilingTimes.isEmpty()) {

            return null;
        }


        // =====================================================
        // CALCULATE AVERAGE
        // =====================================================

        double average =
                boilingTimes.stream()
                        .mapToDouble(
                                Integer::doubleValue
                        )
                        .average()
                        .orElse(0);


        // =====================================================
        // TWO DECIMAL PLACES
        // =====================================================

        return Math.round(
                average * 100.0
        ) / 100.0;
    }


    // =====================================================
    // CHECK WHETHER VALVE HAS ACTIVE CYCLE
    // =====================================================

    private boolean hasValveCycles(
            Integer... values
    ) {

        for (Integer value : values) {

            if (value != null && value > 0) {

                return true;
            }
        }

        return false;
    }


    // =====================================================
    // CONVERT SQL NUMBER → DOUBLE
    // =====================================================

    private Double toDouble(
            Object value
    ) {

        if (value == null) {

            return null;
        }

        return ((Number) value).doubleValue();
    }


    // =====================================================
    // CONVERT SQL TIMESTAMP → LOCALDATETIME
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