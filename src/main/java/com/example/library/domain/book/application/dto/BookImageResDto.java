package com.example.library.domain.book.application.dto;

import com.example.library.domain.book.domain.BookImageEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookImageResDto {
    String originalFileName;
    Long fileSize;
    String filePath;
    String newFileName;

    public static BookImageResDto from(BookImageEntity bookImageEntity){
        return BookImageResDto.builder()
               .originalFileName(bookImageEntity.getOriginalFileName())
               .newFileName(bookImageEntity.getNewFileName())
               .filePath(bookImageEntity.getFilePath())
               .fileSize(bookImageEntity.getFileSize())
               .build()
               ;
    }
}
