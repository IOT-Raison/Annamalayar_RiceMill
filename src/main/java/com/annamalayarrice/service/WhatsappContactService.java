package com.annamalayarrice.service;

import com.annamalayarrice.dto.WhatsappContactDTO;
import com.annamalayarrice.entity.WhatsappContact;
import com.annamalayarrice.repository.WhatsappContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WhatsappContactService {

    private final WhatsappContactRepository repository;


    // =========================================================
    // SAVE CONTACT
    // =========================================================

    public WhatsappContact saveContact(
            WhatsappContactDTO dto) {

        String number =
                normalize(dto.getPhoneNumber());

        String name = dto.getName();


        if (repository.existsByPhoneNumber(number)) {

            throw new IllegalArgumentException(
                    "Phone number already exists"
            );
        }


        WhatsappContact contact = new WhatsappContact();

        contact.setName(name);
        contact.setPhoneNumber(number);

        // If active is not provided, make it active
        contact.setActive(
                dto.getActive() == null
                        ? true
                        : dto.getActive()
        );


        return repository.save(contact);
    }


    // =========================================================
    // UPDATE CONTACT
    // =========================================================

    public WhatsappContact updateContact(
            Long id,
            WhatsappContactDTO dto) {

        WhatsappContact contact =
                repository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Contact not found"
                                )
                        );


        String newNumber =
                normalize(dto.getPhoneNumber());


        // Check duplicate number
        if (!contact.getPhoneNumber()
                .equals(newNumber)
                &&
                repository.existsByPhoneNumber(newNumber)) {

            throw new IllegalArgumentException(
                    "Phone number already exists"
            );
        }


        contact.setName(dto.getName());
        contact.setPhoneNumber(newNumber);


        // Update active status if supplied
        if (dto.getActive() != null) {

            contact.setActive(
                    dto.getActive()
            );
        }


        return repository.save(contact);
    }


    // =========================================================
    // ACTIVATE CONTACT
    // =========================================================

    public WhatsappContact activateContact(
            Long id) {

        WhatsappContact contact =
                repository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Contact not found"
                                )
                        );


        contact.setActive(true);

        return repository.save(contact);
    }


    // =========================================================
    // DEACTIVATE CONTACT
    // =========================================================

    public WhatsappContact deactivateContact(
            Long id) {

        WhatsappContact contact =
                repository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Contact not found"
                                )
                        );


        contact.setActive(false);

        return repository.save(contact);
    }


    // =========================================================
    // GET ALL CONTACTS
    // =========================================================

    public List<WhatsappContact> getAllContacts() {

        return repository.findAll();
    }


    // =========================================================
    // GET ACTIVE CONTACTS
    // =========================================================

    public List<WhatsappContact> getActiveContacts() {

        return repository.findByActiveTrue();
    }

    // =========================================================
    // NORMALIZE PHONE NUMBER
    // =========================================================

    private String normalize(String raw) {

        if (raw == null || raw.isBlank()) {

            throw new IllegalArgumentException(
                    "Phone number is required"
            );
        }


        raw = raw
                .replaceAll("\\s+", "")
                .replaceAll("-", "");


        if (!raw.startsWith("+")) {

            throw new IllegalArgumentException(
                    "Phone number must include country code"
            );
        }


        return raw;
    }

    // PERMANENT DELETE
public void deleteContact(Long id) {

    WhatsappContact contact =
            repository.findById(id)
                    .orElseThrow(
                            () -> new RuntimeException(
                                    "Contact not found"
                            )
                    );

    repository.delete(contact);
}
}