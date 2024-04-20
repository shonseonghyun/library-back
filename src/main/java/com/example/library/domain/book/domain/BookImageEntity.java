package com.example.library.domain.book.domain;

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
public class BookImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgNo;

    //case1) BookEntity가 주인
//    @OneToOne(mappedBy = "bookImage") //targetEntity(BookEntity)의 필드명과 동일해야한다.
//    private BookEntity book;

    //case2) BookEntity가 주인
    @OneToOne
    @JoinColumn(name="book_no")
    private BookEntity book;

    private String originalFileName;

    private String newFileName;

    private String filePath;

    private Long fileSize;

    public void setBookCase1(BookEntity bookEntity){
        this.book = bookEntity;
    }
}
