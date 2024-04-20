package com.example.library.domain.book.application.Impl;

import com.example.library.domain.book.application.BookService;
import com.example.library.domain.book.application.dto.BookRegReqDto;
import com.example.library.domain.book.application.dto.UserInquiryBookResDto;
import com.example.library.domain.book.domain.BookEntity;
import com.example.library.domain.book.domain.BookImageEntity;
import com.example.library.domain.book.domain.dto.BookSearchPagingResDto;
import com.example.library.domain.book.domain.dto.BookSearchResponseDto;
import com.example.library.domain.book.domain.repository.BookRepository;
import com.example.library.domain.book.enums.InquiryCategory;
import com.example.library.domain.rent.application.event.CheckedRentBookAvailableEvent;
import com.example.library.domain.rent.application.event.RentedBookEvent;
import com.example.library.global.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final FileService fileService;

    @Override
    public BookSearchPagingResDto inquirySimpleCategory(InquiryCategory category, String inquiryWord,Pageable pageable,Long cachedCount) {
        Page<BookSearchResponseDto.Response> pageResult = bookRepository.findBooksBySimpleCategory(category,inquiryWord,pageable,cachedCount);
        List<BookSearchResponseDto.Response> responseList=pageResult.getContent();
        int totalCount = (int)pageResult.getTotalElements(); //총 갯수
        return new BookSearchPagingResDto(totalCount,responseList);
    }

    @Override
    @Transactional(readOnly = true)
    public UserInquiryBookResDto inquiryBook(Long bookNo){
        BookEntity selectedBook = bookRepository.findByBookNo(bookNo);
        UserInquiryBookResDto userInquiryBookResDtos = UserInquiryBookResDto.from(selectedBook);
        return userInquiryBookResDtos;
    }

    @Override
    @Transactional
    public void regBook(BookRegReqDto bookRegReqDto, MultipartFile file) {
        //도서 엔티티 생성
        BookEntity bookEntity = bookRegReqDto.toBookEntity();

        //이미지 엔티티 생성
        String newFileName = fileService.getNewFileName(file);
        String uploadFilePath = fileService.getUploadFilePath();

        BookImageEntity bookImageEntity = BookImageEntity.builder()
                .originalFileName(file.getOriginalFilename())
                .fileSize(file.getSize())
                .newFileName(newFileName)
                .filePath(uploadFilePath)
                .build()
                ;

        bookEntity.setBookImage(bookImageEntity);
        bookRepository.save(bookEntity);
    }

    @Override
    @Transactional
    public void removeBook(Long bookNo) {
        BookEntity selectedBook = bookRepository.findByBookNo(bookNo);
        bookRepository.delete(selectedBook);
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
