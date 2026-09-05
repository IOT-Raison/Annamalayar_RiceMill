package com.annamalayarrice.service;

import com.annamalayarrice.entity.BatchNotification;
import com.annamalayarrice.entity.Huller;
import com.annamalayarrice.repository.BatchNotificationRepository;
import com.annamalayarrice.repository.HullerRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HullerWhatsAppService {

    private final HullerRepository hullerRepository;

    private final BatchNotificationRepository
            batchNotificationRepository;

    private final WhatsappService whatsappService;


    public HullerWhatsAppService(
            HullerRepository hullerRepository,
            BatchNotificationRepository batchNotificationRepository,
            WhatsappService whatsappService) {

        this.hullerRepository =
                hullerRepository;

        this.batchNotificationRepository =
                batchNotificationRepository;

        this.whatsappService =
                whatsappService;
    }


    // =========================================================
    // CHECK EVERY 10 SECONDS
    // =========================================================

   @Scheduled(fixedDelay = 10000)
public void checkCompletedHullerBatch() {

    try {

        // =====================================================
        // GET ONLY THE LAST ROW OF HULLER TABLE
        // =====================================================

        Optional<Huller> latestRecord =
                hullerRepository
                        .findTopByOrderByDatetimeFieldDesc();


        if (latestRecord.isEmpty()) {

            System.out.println(
                    "No Huller data found."
            );

            return;
        }


        Huller huller =
                latestRecord.get();


        System.out.println(
                "Latest Huller record:"
        );

        System.out.println(
                "Huller ID: " +
                huller.getId()
        );

        System.out.println(
                "Batch Status: " +
                huller.getBatchStatus()
        );

        System.out.println(
                "Date Time: " +
                huller.getDatetimeField()
        );


        // =====================================================
        // CHECK THE ACTUAL LAST ROW STATUS
        // =====================================================

        if (!Integer.valueOf(2).equals(
                huller.getBatchStatus())) {

            System.out.println(
                    "Latest Huller row is NOT completed."
            );

            System.out.println(
                    "Batch Status = " +
                    huller.getBatchStatus()
            );

            return;
        }


        // =====================================================
        // ONLY STATUS = 2 WILL REACH HERE
        // =====================================================

        processCompletedBatch(huller);


    } catch (Exception e) {

        System.err.println(
                "Huller WhatsApp checker error: "
                        + e.getMessage()
        );

        e.printStackTrace();
    }
}


    // =========================================================
    // PROCESS COMPLETED HULLER
    // =========================================================

private void processCompletedBatch(
        Huller huller) {

    /*
     * Example:
     *
     * PADDY_INPUT_BIN = SILO 3
     *
     * PADDY_SILO_3_ID = 7
     *
     * Paddy ID = 7
     *
     * PADDY_NAME_3 = AMMAN SONA (DOUBLE)
     *
     * Material = AMMAN SONA (DOUBLE)
     */


    Integer paddyId =
            getPaddyIdFromInputBin(huller);


    if (paddyId == null) {

        System.out.println(
                "Paddy ID not found for Huller ID: "
                        + huller.getId()
        );

        return;
    }


    // =====================================================
    // CHECK WHETHER MESSAGE WAS ALREADY SENT
    // =====================================================

    Optional<BatchNotification> existingNotification =
            batchNotificationRepository
                    .findByPaddyID(paddyId);


    if (existingNotification.isPresent()) {

        BatchNotification notification =
                existingNotification.get();


        if (Boolean.TRUE.equals(
                notification.getMessageSent())) {

            System.out.println(
                    "WhatsApp already sent for Paddy ID: "
                            + paddyId
            );

            return;
        }
    }


    // =====================================================
    // GET MATERIAL
    // =====================================================

    String material =
            getMaterialFromInputBin(huller);


    // =====================================================
    // FIND START RECORD
    // =====================================================

    Huller startRecord =
            findStartRecord(
                    paddyId,
                    huller.getPaddyInputBin(),
                    huller.getDatetimeField()
            );


    if (startRecord == null) {

        System.out.println(
                "START record not found for Paddy ID: "
                        + paddyId
        );

        return;
    }


    // =====================================================
    // SEND WHATSAPP
    // =====================================================

    boolean messageSent =
            whatsappService.sendHullerCompletedMessage(

                    startRecord
                            .getDatetimeField()
                            .format(WHATSAPP_DATE_FORMAT),

                    huller
                            .getDatetimeField()
                            .format(WHATSAPP_DATE_FORMAT),

                    paddyId,

                    material,

                    huller.getPaddyKg(),

                    huller.getRiceKg(),

                    huller.getBrokenKg(),

                    huller.getKwh()
            );


    // =====================================================
    // MESSAGE NOT SENT
    // =====================================================

    if (!messageSent) {

        System.out.println(
                "WhatsApp message NOT SENT for Paddy ID: "
                        + paddyId
        );

        System.out.println(
                "MESSAGE_SENT remains FALSE."
        );

        return;
    }


    // =====================================================
    // ONLY AFTER SUCCESSFUL TWILIO SEND
    // SAVE NOTIFICATION
    // =====================================================

    BatchNotification notification;

    if (existingNotification.isPresent()) {

        notification =
                existingNotification.get();

    } else {

        notification =
                new BatchNotification();

        notification.setPaddyID(paddyId);
    }


    notification.setMessageSent(true);

    notification.setSentTime(
            LocalDateTime.now()
    );


    batchNotificationRepository.save(
            notification
    );


    System.out.println(
            "WhatsApp notification saved successfully."
    );

    System.out.println(
            "Paddy ID: " + paddyId
    );

    System.out.println(
            "MESSAGE_SENT = TRUE"
    );
}

    // =========================================================
    // GET PADDY ID BASED ON INPUT BIN
    // =========================================================

    private Integer getPaddyIdFromInputBin(
            Huller huller) {

        String bin =
                huller.getPaddyInputBin();


        if (bin == null) {
            return null;
        }


        bin =
                bin.trim()
                        .toUpperCase();


        switch (bin) {

            case "BIN1":
            case "SILO 1":
            case "SILO1":

                return huller.getPaddySilo1Id();


            case "BIN2":
            case "SILO 2":
            case "SILO2":

                return huller.getPaddySilo2Id();


            case "BIN3":
            case "SILO 3":
            case "SILO3":

                return huller.getPaddySilo3Id();


            case "BIN4":
            case "SILO 4":
            case "SILO4":

                return huller.getPaddySilo4Id();


            default:

                System.out.println(
                        "Unknown PADDY_INPUT_BIN: "
                                + bin
                );

                return null;
        }
    }


    // =========================================================
    // GET MATERIAL
    // =========================================================

    private String getMaterialFromInputBin(
            Huller huller) {

        String bin =
                huller.getPaddyInputBin();


        if (bin == null) {
            return null;
        }


        bin =
                bin.trim()
                        .toUpperCase();


        switch (bin) {

            case "BIN1":
            case "SILO 1":
            case "SILO1":

                return huller.getPaddyName1();


            case "BIN2":
            case "SILO 2":
            case "SILO2":

                return huller.getPaddyName2();


            case "BIN3":
            case "SILO 3":
            case "SILO3":

                return huller.getPaddyName3();


            case "BIN4":
            case "SILO 4":
            case "SILO4":

                return huller.getPaddyName4();


            default:

                return null;
        }
    }


    // =========================================================
    // FIND START RECORD
    // =========================================================

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
    // =========================================================
    // COMPARE BIN
    // =========================================================

    // private boolean sameBin(
    //         String bin1,
    //         String bin2) {

    //     return normalizeBin(bin1)
    //             .equals(
    //                     normalizeBin(bin2)
    //             );
    // }


    // private String normalizeBin(
    //         String bin) {

    //     return bin
    //             .trim()
    //             .toUpperCase()
    //             .replace(" ", "");
    // }

@Scheduled(cron = "0 0 0 */10 * *")
@Transactional
public void deleteOldNotifications() {

    LocalDateTime cutoffTime =
            LocalDateTime.now().minusDays(10);

    int deleted =
            batchNotificationRepository.deleteOlderThan(cutoffTime);

    System.out.println(
            "Deleted " + deleted +
            " old notification records."
    );
}

private static final DateTimeFormatter WHATSAPP_DATE_FORMAT =
        DateTimeFormatter.ofPattern(
                "dd-MM-yyyy | hh:mm:ss a"
        );

}