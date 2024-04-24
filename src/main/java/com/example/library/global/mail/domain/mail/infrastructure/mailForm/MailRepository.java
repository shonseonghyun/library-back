package com.example.library.global.mail.domain.mail.infrastructure.mailForm;

import com.example.library.global.mail.domain.mail.domain.mailForm.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MailRepository extends JpaRepository<MailEntity, Long> {
    Optional<MailEntity> findByMailType(String type);
}
