package com.example.library.domain.book.infrastructure;

import com.example.library.domain.book.domain.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpringDataJpaBookRepository extends JpaRepository<BookEntity, Long> {
    @Query(
            value = "select b from BookEntity b " +
                    "join fetch b.bookImage I " +
                    "where b.bookCode=:bookCode " +
                    "and b.bookCode= I.book.bookCode "
    )
    Optional<BookEntity> findByBookCode(@Param("bookCode") Long bookCode);
}
