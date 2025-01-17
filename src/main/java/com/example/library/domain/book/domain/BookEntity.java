package com.example.library.domain.book.domain;

import com.example.library.domain.book.domain.converter.BookStateConverter;
import com.example.library.domain.book.enums.BookState;
import com.example.library.exception.ErrorCode;
import com.example.library.exception.exceptions.BookOnRentException;
import com.example.library.global.entityListener.Entity.ModifiedEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;

@Slf4j
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@Table(name = "book")
public class BookEntity extends ModifiedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookCode;

    @Column(nullable = false)
    private String bookName;

    @Column(nullable = false)
    private String bookAuthor;

    @Column(nullable = false)
    private String bookContent;

    @Column(nullable = false)
    @Convert(converter = BookStateConverter.class)
    private BookState bookState;

    @Column(nullable = false)
    private String bookPublisher;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String pubDt;

    @Column(nullable = false)
    private String bookLocation;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "book",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private BookImageEntity bookImage;

    //연관관계 편의메소드
    public void setBookImage(BookImageEntity bookImageEntity){
        this.bookImage= bookImageEntity;
        bookImageEntity.setBook(this);
    }

    public void rentSuc(){
        changeRentUnavailable();
    }
    private void changeRentUnavailable(){
        setBookState(BookState.RENT_UNAVAILABLE);
    }

    public void returnSuc(){
        changeRentAvailable();
    }
    private void changeRentAvailable(){
        setBookState(BookState.RENT_AVAILABLE);
    }

    public void checkRentAvailable(){
        isRentAvailableBook();
    }

    private void isRentAvailableBook(){
        if(BookState.RENT_UNAVAILABLE.equals(bookState)){
            log.error(String.format("해당 도서[%s]는 현재 대여 중인 도서입니다. 대여 상태 [%s]",bookCode.toString(),"대여 중"));
            throw new BookOnRentException(ErrorCode.BOOK_ON_RENT);
        }
    }
}
