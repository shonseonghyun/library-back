package com.example.library.domain.book.infrastructure;

import com.example.library.domain.book.domain.BookImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaBookImageRepository extends JpaRepository<BookImageEntity, Long> {
}
