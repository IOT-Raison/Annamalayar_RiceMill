package com.annamalayarrice.service;

import com.annamalayarrice.entity.WhatsappContact;
import com.annamalayarrice.repository.WhatsappContactRepository;
import com.google.gson.Gson;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WhatsappService {

    private final WhatsappContactRepository contactRepository;

    @Value("${twilio.whatsapp.from}")
    private String whatsappFrom;

    @Value("${twilio.template.sid}")
    private String templateSid;

    @Value("${twilio.template.pf-alert.sid}")
    private String pfAlertTemplateSid;


    public WhatsappService(
            WhatsappContactRepository contactRepository) {

        this.contactRepository = contactRepository;
    }


    /**
     * Returns:
     * true  = Twilio accepted the message
     * false = message was not sent
     */
    public boolean sendHullerCompletedMessage(
            String startDateTime,
            String endDateTime,
            Integer paddyId,
            String material,
            Float paddy,
            Float rice,
            Float broken,
            Float kwh) {


        // ============================================
        // Calculate percentages
        // ============================================

        double ricePercentage = 0.0;
        double brokenPercentage = 0.0;

        if (paddy != null && paddy > 0) {

            if (rice != null) {
                ricePercentage =
                        (rice / paddy) * 100.0;
            }

            if (broken != null) {
                brokenPercentage =
                        (broken / paddy) * 100.0;
            }
        }


        // ============================================
        // Get active WhatsApp contacts
        // ============================================

        List<WhatsappContact> contacts =
                contactRepository.findByActiveTrue();


        // ============================================
        // NO CONTACT NUMBER
        // ============================================

        if (contacts == null || contacts.isEmpty()) {

            System.out.println(
                    "NO CONTACT TO SEND: " +
                    "WHATSAPP_CONTACT table has no active contacts."
            );

            return false;
        }


        // ============================================
        // Send to every active contact
        // ============================================

        boolean atLeastOneSent = false;

        for (WhatsappContact contact : contacts) {

            if (contact.getPhoneNumber() == null ||
                    contact.getPhoneNumber().trim().isEmpty()) {

                System.out.println(
                        "NO CONTACT NUMBER: " +
                        "Contact ID = " +
                        contact.getId()
                );

                continue;
            }


            try {

                boolean sent =
                        sendToContact(
                                contact,
                                startDateTime,
                                endDateTime,
                                paddyId,
                                material,
                                paddy,
                                rice,
                                ricePercentage,
                                broken,
                                brokenPercentage,
                                kwh
                        );

                if (sent) {
                    atLeastOneSent = true;
                }

            } catch (Exception e) {

                System.err.println(
                        "MESSAGE NOT REACHED / SEND FAILED"
                );

                System.err.println(
                        "To: " +
                        contact.getPhoneNumber()
                );

                System.err.println(
                        "Reason: " +
                        e.getMessage()
                );
            }
        }


        // ============================================
        // Final result
        // ============================================

        if (!atLeastOneSent) {

            System.out.println(
                    "MESSAGE NOT REACHED: " +
                    "Twilio did not successfully accept the message."
            );

            return false;
        }

        return true;
    }


    private boolean sendToContact(
            WhatsappContact contact,
            String startDateTime,
            String endDateTime,
            Integer paddyId,
            String material,
            Float paddy,
            Float rice,
            double ricePercentage,
            Float broken,
            double brokenPercentage,
            Float kwh) {


        // ============================================
        // Template variables
        // ============================================

        Map<String, Object> variables =
                new HashMap<>();

        variables.put(
                "1",
                startDateTime
        );

        variables.put(
                "2",
                endDateTime
        );

        variables.put(
                "3",
                String.valueOf(paddyId)
        );

        variables.put(
                "4",
                material != null
                        ? material
                        : ""
        );

        variables.put(
                "5",
                formatNumber(paddy)
        );

        variables.put(
                "6",
                formatNumber(rice)
        );

        variables.put(
                "7",
                String.format(
                        "%.2f",
                        ricePercentage
                )
        );

        variables.put(
                "8",
                formatNumber(broken)
        );

        variables.put(
                "9",
                String.format(
                        "%.2f",
                        brokenPercentage
                )
        );

        variables.put(
                "10",
                formatNumber(kwh)
        );


        String contentVariables =
                new Gson().toJson(variables);


        // ============================================
        // Send WhatsApp Template
        // ============================================

        Message message =
                Message.creator(
                        new PhoneNumber(
                                "whatsapp:" +
                                contact.getPhoneNumber().trim()
                        ),
                        new PhoneNumber(
                                whatsappFrom
                        ),
                        (String) null
                )
                .setContentSid(templateSid)
                .setContentVariables(contentVariables)
                .create();


        // ============================================
        // Twilio response
        // ============================================

        if (message == null ||
                message.getSid() == null) {

            System.err.println(
                    "MESSAGE NOT REACHED: " +
                    "Twilio returned no Message SID."
            );

            return false;
        }


        System.out.println(
                "TWILIO MESSAGE ACCEPTED"
        );

        System.out.println(
                "To: " +
                contact.getPhoneNumber()
        );

        System.out.println(
                "Message SID: " +
                message.getSid()
        );

        System.out.println(
                "Twilio Status: " +
                message.getStatus()
        );

        return true;
    }


    private String formatNumber(Float value) {

        if (value == null) {
            return "0.00";
        }

        return String.format(
                "%.2f",
                value
        );
    }

 public boolean sendPfLowAlert(
        Float pf,
        long durationMinutes) {

    List<WhatsappContact> contacts =
            contactRepository.findByActiveTrue();

    if (contacts == null || contacts.isEmpty()) {

        System.out.println(
                "NO CONTACT TO SEND PF ALERT: "
                        + "WHATSAPP_CONTACT table has no active contacts."
        );

        return false;
    }

    boolean atLeastOneSent = false;

    // =====================================================
    // IMPORTANT:
    // KEEP THE ACTUAL PF VALUE INCLUDING MINUS SIGN
    // =====================================================

    String pfValue =
            pf != null
                    ? String.format("%.2f", pf)
                    : "N/A";


    for (WhatsappContact contact : contacts) {

        if (contact.getPhoneNumber() == null ||
                contact.getPhoneNumber().trim().isEmpty()) {

            System.out.println(
                    "NO CONTACT NUMBER: Contact ID = "
                            + contact.getId()
            );

            continue;
        }

        try {

            // =================================================
            // TEMPLATE VARIABLES
            // =================================================

            Map<String, Object> variables =
                    new HashMap<>();


            // {{1}} = PF
            variables.put(
                    "1",
                    pfValue
            );


            // {{2}} = duration
            variables.put(
                    "2",
                    String.valueOf(durationMinutes)
            );


            String contentVariables =
                    new Gson().toJson(variables);


            // =================================================
            // DEBUG
            // =================================================

            System.out.println(
                    "=============================================="
            );

            System.out.println(
                    "PF WHATSAPP ALERT"
            );

            System.out.println(
                    "TO       : "
                            + contact.getPhoneNumber()
            );

            System.out.println(
                    "PF ACTUAL: "
                            + pf
            );

            System.out.println(
                    "PF VALUE : "
                            + pfValue
            );

            System.out.println(
                    "DURATION : "
                            + durationMinutes
                            + " minutes"
            );

            System.out.println(
                    "VARIABLES: "
                            + contentVariables
            );


            // =================================================
            // SEND TWILIO WHATSAPP
            // =================================================

            Message message =
                    Message.creator(

                            new PhoneNumber(
                                    "whatsapp:"
                                            + contact
                                            .getPhoneNumber()
                                            .trim()
                            ),

                            new PhoneNumber(
                                    whatsappFrom
                            ),

                            (String) null

                    )
                    .setContentSid(
                            pfAlertTemplateSid
                    )
                    .setContentVariables(
                            contentVariables
                    )
                    .create();


            // =================================================
            // CHECK RESPONSE
            // =================================================

            if (message == null ||
                    message.getSid() == null) {

                System.err.println(
                        "PF MESSAGE NOT REACHED: "
                                + "Twilio returned no Message SID."
                );

                continue;
            }


            System.out.println(
                    "PF WHATSAPP ACCEPTED"
            );

            System.out.println(
                    "TO: "
                            + contact.getPhoneNumber()
            );

            System.out.println(
                    "MESSAGE SID: "
                            + message.getSid()
            );

            System.out.println(
                    "TWILIO STATUS: "
                            + message.getStatus()
            );


            atLeastOneSent = true;


        } catch (Exception e) {

            System.err.println(
                    "PF WHATSAPP SEND FAILED"
            );

            System.err.println(
                    "TO: "
                            + contact.getPhoneNumber()
            );

            System.err.println(
                    "REASON: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }
    }


    if (!atLeastOneSent) {

        System.out.println(
                "PF MESSAGE NOT SENT TO ANY CONTACT"
        );

        return false;
    }


    return true;
}

}