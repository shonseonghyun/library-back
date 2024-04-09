package com.example.library.domain.book.application.Impl;

import com.example.library.domain.book.application.BookService;
import com.example.library.domain.book.application.dto.BookModifiyReqDto;
import com.example.library.domain.book.application.dto.UserInquiryBookResDto;
import com.example.library.domain.book.domain.BookEntity;
import com.example.library.domain.book.domain.repository.BookRepository;
import com.example.library.domain.book.enums.BookState;
import com.example.library.domain.rent.application.event.CheckedRentBookAvailableEvent;
import com.example.library.domain.rent.application.event.RentedBookEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

//    @Override
//    @Transactional(readOnly = true)
//    public BookDto detailSearchByBookAuthor(String bookAuthor) {
//        BookEntity bookEntity = bookRepository.findByBookAuthor(bookAuthor)
//                .orElseThrow(() -> new BookNotFoundException(ErrorCode.BOOKAUTHOR_NOT_FOUND));
//
//        return BookDto.detail(bookEntity);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public BookDto detailSearchByBookName(String bookName) {
//        BookEntity bookEntity = bookRepository.findByBookName(bookName)
//                .orElseThrow(() -> new BookNotFoundException(ErrorCode.BOOKNAME_NOT_FOUND));
//
//        return BookDto.detail(bookEntity);
//    }
//
//    @Override
//    public BookSimpleDto simpleSearchByBookAuthor(String bookAuthor) {
//        BookEntity bookEntity = bookRepository.findByBookAuthor(bookAuthor)
//                .orElseThrow(() -> new BookNotFoundException(ErrorCode.BOOKAUTHOR_NOT_FOUND));
//
//        return BookSimpleDto.simple(bookEntity);
//    }
//
//    @Override
//    public BookSimpleDto simpleSearchByBookName(String bookName) {
//        BookEntity bookEntity = bookRepository.findByBookName(bookName)
//                .orElseThrow(() -> new BookNotFoundException(ErrorCode.BOOKNAME_NOT_FOUND));
//
//        return BookSimpleDto.simple(bookEntity);
//    }

    @Override
    @Transactional
    public BookModifiyReqDto add(BookModifiyReqDto bookModifiyReqDto) {
        BookEntity book = BookEntity.builder()
                .bookName(bookModifiyReqDto.getBookName())
                .bookAuthor(bookModifiyReqDto.getBookAuthor())
                .bookContent(bookModifiyReqDto.getBookContent())
                .bookState(BookState.getBookState(bookModifiyReqDto.getBookState()))
                .bookPublisher(bookModifiyReqDto.getBookPublisher())
                .isbn(bookModifiyReqDto.getIsbn())
                .pubDate(bookModifiyReqDto.getPubDate())
                .bookLocation(bookModifiyReqDto.getBookLocation())
                .bookImage(bookModifiyReqDto.getBookImage())
                .build();

        bookRepository.save(book);

        return BookModifiyReqDto.add(book);
    }

//    @Override
//    public BookDto update(BookDto bookDto, Long bookCode) {
//        BookEntity bookEntity = inquiryBook(bookCode);
//
//        bookEntity.setBookName(bookDto.getBookName());
//        bookEntity.setBookAuthor(bookDto.getBookAuthor());
//        bookEntity.setBookContent(bookDto.getBookContent());
//        bookEntity.setBookState(BookState.getBookState(bookDto.getBookState()));
//        bookEntity.setBookPublisher(bookDto.getBookPublisher());
//        bookEntity.setIsbn(bookDto.getIsbn());
//        bookEntity.setPubDate(bookDto.getPubDate());
//        bookEntity.setBookLocation(bookDto.getBookLocation());
//        bookEntity.setBookImage(bookDto.getBookImage());
//
//        bookRepository.save(bookEntity);
//
//        return BookDto.detail(bookEntity);
//    }

//    @Override
//    @Transactional
//    public void delete(Long bookCode) {
//        reviewRepository.deleteByBookBookCode(bookCode);
//        heartRepository.deleteByBookBookCode(bookCode);
//        bookRepository.deleteByBookCode(bookCode);
//    }


    @Override
    @Transactional(readOnly = true)
    public UserInquiryBookResDto inquiryBook(Long bookNo){
        BookEntity selectedBook = bookRepository.findByBookNo(bookNo);
        UserInquiryBookResDto userInquiryBookResDtos = UserInquiryBookResDto.from(selectedBook);
        return userInquiryBookResDtos;
    }

    @EventListener
    public void rentSuc(RentedBookEvent evt){
        BookEntity selectedBook = bookRepository.findByBookNo(evt.getBookNo());
        selectedBook.rentSuc();
    }

    @EventListener
    public void checkRentBookAvailable(CheckedRentBookAvailableEvent evt){
        BookEntity selectedBook = bookRepository.findByBookNo(evt.getBookNo());
        selectedBook.checkRentAvailable();
    }

    @EventListener
    public void returnSuc(CheckedRentBookAvailableEvent evt){
        BookEntity selectedBook = bookRepository.findByBookNo(evt.getBookNo());
        selectedBook.returnSuc();
    }

    @EventListener
    public void checkBookExist(Long bookNo){
        bookRepository.findByBookNo(bookNo);
    }
}
