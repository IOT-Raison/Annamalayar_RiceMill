package com.annamalayarrice.service;

import com.annamalayarrice.entity.AddressLog;
import com.annamalayarrice.repository.AddressLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PfMonitoringService {

    private final AddressLogRepository addressLogRepository;
    private final WhatsappService whatsappService;


    // =====================================================
    // PF MONITORING STATE
    // =====================================================

    /*
     * Stores the time when PF first entered the
     * critical condition.
     */
    private LocalDateTime criticalPfStartTime = null;


    /*
     * Prevents sending WhatsApp repeatedly while
     * the same PF critical condition continues.
     */
    private boolean alertSent = false;


    // =====================================================
    // CHECK EVERY 10 SECONDS
    // =====================================================

    @Scheduled(fixedDelay = 10000)
    public void checkPowerFactor() {

        try {

            // =================================================
            // GET LATEST ADDRESS_LOG RECORD
            // =================================================

            AddressLog latest =
                    addressLogRepository
                            .findTopByOrderByLogTimeDesc()
                            .orElse(null);


            if (latest == null) {

                System.out.println(
                        "PF MONITOR: No ADDRESS_LOG data"
                );

                return;
            }


 // =================================================
// GET PF AND LOG TIME
// =================================================

Float pf =
        latest.getPowerHousePf();

LocalDateTime logTime =
        latest.getLogTime();

if (pf == null || logTime == null) {

    System.out.println(
            "PF MONITOR: PF or LOG_TIME is null"
    );

    return;
}

            System.out.println(
                    "================================================="
            );

            System.out.println(
                    "PF MONITOR"
            );

            System.out.println(
                    "LOG TIME : " + logTime
            );

            System.out.println(
                    "PF       : " + pf
            );


            // =================================================
            // CRITICAL PF CONDITION
            //
            // PF > -0.85 IS CRITICAL
            //
            // Examples:
            //
            // -0.80 -> CRITICAL
            // -0.70 -> CRITICAL
            // -0.50 -> CRITICAL
            // -0.89 -> CRITICAL
            //
            // -0.90 -> NORMAL / RECOVERED
            // -0.91 -> NORMAL
            // -0.95 -> NORMAL
            // =================================================

            if (pf > -0.85f && pf < 0.85f) {


                // =================================================
                // FIRST CRITICAL PF RECORD
                // =================================================

                if (criticalPfStartTime == null) {

                    criticalPfStartTime = logTime;

                    System.out.println(
                            "PF CRITICAL STARTED AT: "
                                    + criticalPfStartTime
                    );

                    return;
                }


                // =================================================
                // CALCULATE CRITICAL DURATION
                // =================================================

                long durationMinutes =
                        Duration.between(
                                criticalPfStartTime,
                                logTime
                        ).toMinutes();


                long durationSeconds =
                        Duration.between(
                                criticalPfStartTime,
                                logTime
                        ).getSeconds();


                System.out.println(
                        "PF CRITICAL DURATION: "
                                + durationMinutes
                                + " minutes"
                );


                System.out.println(
                        "PF CRITICAL DURATION: "
                                + durationSeconds
                                + " seconds"
                );


                // =================================================
                // MORE THAN 5 MINUTES
                //
                // alertSent prevents repeated messages.
                // =================================================

                if (durationMinutes > 10 && !alertSent) {

                    System.out.println(
                            "***********************************************"
                    );

                    System.out.println(
                            "PF ALERT CONDITION MET"
                    );

                    System.out.println(
                            "PF       : " + pf
                    );

                    System.out.println(
                            "START    : " + criticalPfStartTime
                    );

                    System.out.println(
                            "CURRENT  : " + logTime
                    );

                    System.out.println(
                            "DURATION : " + durationMinutes
                                    + " minutes"
                    );


                    // =================================================
                    // SEND WHATSAPP
                    // =================================================

                    boolean sent =
                            whatsappService.sendPfLowAlert(
                                    pf,
                                    durationMinutes
                            );


                    // =================================================
                    // MESSAGE SUCCESSFULLY SENT
                    // =================================================

                    if (sent) {

                        alertSent = true;

                        System.out.println(
                                "PF ALERT SENT SUCCESSFULLY"
                        );

                        System.out.println(
                                "NO MORE PF ALERTS WILL BE SENT "
                                        + "UNTIL PF RECOVERS."
                        );

                    }

                    // =================================================
                    // MESSAGE FAILED
                    // =================================================

                    else {

                        System.out.println(
                                "PF ALERT NOT SENT"
                        );

                        /*
                         * alertSent remains false.
                         *
                         * Therefore the next scheduler cycle
                         * will try again.
                         */
                    }
                }


                // =================================================
                // ALERT ALREADY SENT
                // =================================================

                if (alertSent) {

                    System.out.println(
                            "PF ALERT ALREADY SENT - "
                                    + "NO REPEAT MESSAGE"
                    );
                }


            }

            // =================================================
            // PF NORMAL / RECOVERED
            //
            // PF <= -0.9
            // =================================================

            else {

                if (criticalPfStartTime != null) {

                    System.out.println(
                            "PF RECOVERED"
                    );

                    System.out.println(
                            "RECOVERY PF: " + pf
                    );
                }


                // =================================================
                // RESET MONITORING STATE
                // =================================================

                criticalPfStartTime = null;

                alertSent = false;


                System.out.println(
                        "PF MONITOR RESET"
                );

                System.out.println(
                        "A NEW CRITICAL EVENT CAN "
                                + "TRIGGER A NEW ALERT."
                );
            }


            System.out.println(
                    "================================================="
            );


        } catch (Exception e) {

            System.err.println(
                    "PF MONITOR ERROR: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}