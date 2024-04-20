package com.example.library.domain.book.api;

import com.example.library.domain.book.application.BookService;
import com.example.library.domain.book.application.dto.BookRegReqDto;
import com.example.library.domain.book.application.dto.UserInquiryBookResDto;
import com.example.library.domain.book.domain.dto.BookSearchPagingResDto;
import com.example.library.domain.book.enums.InquiryCategory;
import com.example.library.exception.ErrorCode;
import com.example.library.global.response.ApiResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/book")
@Tag(name = "book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/inquiry/{category}/{inquiryWord}")
    public ApiResponseDto inquirySimpleCategory(@PathVariable("category") String category,@PathVariable("inquiryWord") String inquiryWord,@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int size, @RequestParam(required = false) Long cachedCount) {
        Pageable pageable = PageRequest.of(page-1,size);
        BookSearchPagingResDto bookSearchPagingResDto= bookService.inquirySimpleCategory(InquiryCategory.getCategory(category),inquiryWord,pageable,cachedCount);
        return ApiResponseDto.createRes(ErrorCode.SUC, bookSearchPagingResDto);
    }

//    @PutMapping("/update/{bookCode}")
//    public ApiResponseDto update(@Valid @RequestBody BookDto bookDto, @PathVariable("bookCode") Long bookCode) {
//        BookDto book = bookService.update(bookDto, bookCode);
//        return ApiResponseDto.createRes(ErrorCode.SUC, bookDto);
//    }

//    @DeleteMapping("/delete/{bookCode}")
//    public ApiResponseDto delete(@PathVariable("bookCode") Long bookCode) {
//        bookService.delete(bookCode);
//        return ApiResponseDto.createRes(ErrorCode.SUC);
//    }

    @GetMapping("/{bookNo}")
    public ApiResponseDto inquiryBook(@PathVariable("bookNo") Long bookNo){
        UserInquiryBookResDto userInquiryBookResDto = bookService.inquiryBook(bookNo);
        return ApiResponseDto.createRes(ErrorCode.SUC,userInquiryBookResDto);
    }

    @PostMapping(value = "/reg")
    public ApiResponseDto regBook(@RequestPart BookRegReqDto bookRegReqDto ,@RequestPart(value = "file") MultipartFile file) throws IOException {
        bookService.regBook(bookRegReqDto,file);
        return ApiResponseDto.createRes(ErrorCode.SUC);
    }

}
