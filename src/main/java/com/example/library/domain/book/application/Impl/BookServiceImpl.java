package com.example.library.domain.book.application.Impl;

import com.example.library.domain.book.domain.BookEntity;
import com.example.library.domain.book.domain.repository.BookRepository;
import com.example.library.domain.book.enums.BookState;
import com.example.library.domain.book.application.BookService;
import com.example.library.domain.book.application.dto.BookAddDto;
import com.example.library.domain.book.application.dto.BookDto;
import com.example.library.domain.rent.application.event.CheckedRentBookAvailableEvent;
import com.example.library.domain.rent.application.event.RentedBookEvent;
import com.example.library.domain.review.repository.ReviewRepository;
import com.example.library.domain.heart.domain.HeartRepository;
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
    private final ReviewRepository reviewRepository;
    private final HeartRepository heartRepository;

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
    public BookAddDto add(BookAddDto bookAddDto) {
        BookEntity book = BookEntity.builder()
                .bookName(bookAddDto.getBookName())
                .bookAuthor(bookAddDto.getBookAuthor())
                .bookContent(bookAddDto.getBookContent())
                .bookState(BookState.getBookState(bookAddDto.getBookState()))
                .bookPublisher(bookAddDto.getBookPublisher())
                .isbn(bookAddDto.getIsbn())
                .pubDate(bookAddDto.getPubDate())
                .bookLocation(bookAddDto.getBookLocation())
                .bookImage(bookAddDto.getBookImage())
                .build();

        bookRepository.save(book);

        return BookAddDto.add(book);
    }

    @Override
    public BookDto update(BookDto bookDto, Long bookCode) {
        BookEntity bookEntity = inquiryBook(bookCode);

        bookEntity.setBookName(bookDto.getBookName());
        bookEntity.setBookAuthor(bookDto.getBookAuthor());
        bookEntity.setBookContent(bookDto.getBookContent());
        bookEntity.setBookState(BookState.getBookState(bookDto.getBookState()));
        bookEntity.setBookPublisher(bookDto.getBookPublisher());
        bookEntity.setIsbn(bookDto.getIsbn());
        bookEntity.setPubDate(bookDto.getPubDate());
        bookEntity.setBookLocation(bookDto.getBookLocation());
        bookEntity.setBookImage(bookDto.getBookImage());

        bookRepository.save(bookEntity);

        return BookDto.detail(bookEntity);
    }

//    @Override
//    @Transactional
//    public void delete(Long bookCode) {
//        reviewRepository.deleteByBookBookCode(bookCode);
//        heartRepository.deleteByBookBookCode(bookCode);
//        bookRepository.deleteByBookCode(bookCode);
//    }

    public BookEntity inquiryBook(Long bookNo){
        return bookRepository.findByBookNo(bookNo);
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW) //해당 메소드 내에서 더티체킹이 되지 않는다. 이걸 해주기 위해 해당 트랜잭션 Requries_new를 세팅한다.
//    @TransactionalEventListener(value =  RentedSuccessEvent.class
//            ,phase = TransactionPhase.AFTER_COMMIT
//    ) //AfterComiit은 해당 메소드를 호출한 메소드의 트랜잭션이 완료된 이후 비동기로 해당 메소드를 진행한다
    @EventListener
    public void rentSuc(RentedBookEvent evt){
        BookEntity selectedBook = inquiryBook(evt.getBookNo());
        selectedBook.rentSuc();
    }

    @EventListener
    public void checkRentBookAvailable(CheckedRentBookAvailableEvent evt){
        BookEntity selectedBook = inquiryBook(evt.getBookNo());
        selectedBook.checkRentAvailable();
    }

    @EventListener
    public void returnSuc(CheckedRentBookAvailableEvent evt){
        BookEntity selectedBook = inquiryBook(evt.getBookNo());
        selectedBook.returnSuc();
    }

    @EventListener
    public void checkBookExist(Long bookNo){
        inquiryBook(bookNo);
    }

}
