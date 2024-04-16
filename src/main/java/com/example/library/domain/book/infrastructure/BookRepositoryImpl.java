package com.example.library.domain.book.infrastructure;

import com.example.library.domain.book.domain.BookEntity;
import com.example.library.domain.book.domain.dto.BookSearchResponseDto;
import com.example.library.domain.book.domain.dto.QBookSearchResponseDto_Response;
import com.example.library.domain.book.domain.repository.BookRepository;
import com.example.library.domain.book.enums.InquiryCategory;
import com.example.library.exception.ErrorCode;
import com.example.library.exception.exceptions.BookNotFoundException;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.library.domain.book.domain.QBookEntity.bookEntity;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final SpringDataJpaBookRepository bookRepository;

    @Override
    public BookEntity findByBookNo(Long bookNo) {
        BookEntity bookDomain= bookRepository.findByBookCode(bookNo)
                .orElseThrow(() -> new BookNotFoundException(ErrorCode.BOOKCODE_NOT_FOUND));
        return bookDomain;
    }

    @Override
    public BookEntity save(BookEntity book) {
        return bookRepository.save(book);
    }

    @Override
    public List<BookSearchResponseDto.Response> findBooksBySimpleCategory(InquiryCategory category, String inquiryWord) {
        List<BookSearchResponseDto.Response> result = jpaQueryFactory.select(new QBookSearchResponseDto_Response(bookEntity.bookCode,bookEntity.bookName,bookEntity.bookAuthor,bookEntity.pubDt,bookEntity.bookState,bookEntity.bookImage))
                .from(bookEntity)
                .where(getSearchCategory(category,inquiryWord))
                .fetch()
                ;
        return result;
    }

    private BooleanExpression getSearchCategory(InquiryCategory category, String inquiryWord){
        if(InquiryCategory.BOOK_NAME.equals(category)){
            return bookEntity.bookName.contains(inquiryWord);
        }

        else if(InquiryCategory.BOOK_AUTHOR.equals(category)){
            return bookEntity.bookAuthor.contains(inquiryWord);
        }

        return null;
    }
}
