package com.example.library.domain.book.domain.repository;

import com.example.library.domain.book.domain.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Book;
import java.util.Optional;

public interface BookRepository {
    BookEntity findByBookNo(Long bookNo);
    BookEntity save(BookEntity book);
}
