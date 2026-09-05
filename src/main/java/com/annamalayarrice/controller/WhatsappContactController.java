package com.annamalayarrice.controller;

import com.annamalayarrice.dto.WhatsappContactDTO;
import com.annamalayarrice.entity.WhatsappContact;
import com.annamalayarrice.service.WhatsappContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/whatsapp/contacts")
@CrossOrigin
@RequiredArgsConstructor
public class WhatsappContactController {

    private final WhatsappContactService service;


    // =========================================================
    // SAVE CONTACT
    // =========================================================

    @PostMapping
    public ResponseEntity<WhatsappContact> saveContact(
            @RequestBody WhatsappContactDTO dto) {

        return ResponseEntity.ok(
                service.saveContact(dto)
        );
    }


    // =========================================================
    // UPDATE CONTACT
    // =========================================================

    @PutMapping("/{id}")
    public ResponseEntity<WhatsappContact> updateContact(
            @PathVariable Long id,
            @RequestBody WhatsappContactDTO dto) {

        return ResponseEntity.ok(
                service.updateContact(id, dto)
        );
    }


    // =========================================================
    // ACTIVATE CONTACT
    // =========================================================

    @PutMapping("/{id}/activate")
    public ResponseEntity<WhatsappContact> activateContact(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.activateContact(id)
        );
    }


    // =========================================================
    // DEACTIVATE CONTACT
    // =========================================================

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<WhatsappContact> deactivateContact(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.deactivateContact(id)
        );
    }


    // =========================================================
    // GET ALL CONTACTS
    // =========================================================

    @GetMapping
    public ResponseEntity<List<WhatsappContact>> getAllContacts() {

        return ResponseEntity.ok(
                service.getAllContacts()
        );
    }


    // =========================================================
    // GET ACTIVE CONTACTS
    // =========================================================

    @GetMapping("/active")
    public ResponseEntity<List<WhatsappContact>> getActiveContacts() {

        return ResponseEntity.ok(
                service.getActiveContacts()
        );
    }


    // =========================================================
    // DELETE / DEACTIVATE
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(
            @PathVariable Long id) {

        service.deleteContact(id);

        return ResponseEntity.ok(
                "Contact deactivated successfully"
        );
    }
}