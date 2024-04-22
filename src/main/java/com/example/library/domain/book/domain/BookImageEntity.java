package com.example.library.domain.book.domain;

import com.example.library.global.entityListener.Entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "book_image")
public class BookImageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_no")
    private Long imgNo;

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="book_no")
    private BookEntity book;

    private String originalFileName;

    private String newFileName;

    private String filePath;

    private Long fileSize;

    public void setBook(BookEntity bookEntity){
        this.book = bookEntity;
    }
}
