package com.annamalayarrice.repository;

import com.annamalayarrice.entity.WhatsappContact;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WhatsappContactRepository
        extends JpaRepository<WhatsappContact, Long> {

    List<WhatsappContact> findByActiveTrue();

    boolean existsByPhoneNumber(String phoneNumber);
}