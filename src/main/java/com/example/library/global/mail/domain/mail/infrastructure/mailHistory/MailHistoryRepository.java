package com.example.library.global.mail.domain.mail.infrastructure.mailHistory;

import com.example.library.global.mail.domain.mail.domain.mailHistory.MailHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailHistoryRepository extends JpaRepository<MailHistoryEntity,Long> {
}
