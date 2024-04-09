package com.example.library.domain.book.infrastructure;

import com.example.library.domain.book.domain.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaBookRepository extends JpaRepository<BookEntity, Long> {
    Optional<BookEntity> findByBookCode(Long bookCode);
}
