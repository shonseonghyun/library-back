package com.example.library.global.file;

import com.example.library.exception.ErrorCode;
import com.example.library.exception.exceptions.UploadFileFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Component
public class FileService {
    @Value("${file.upload.path}")
    private String uploadPath;

    public String getNewFileName(MultipartFile file){
        String originalFileName = file.getOriginalFilename();

        return UUID.randomUUID()+"."+originalFileName.substring(originalFileName.lastIndexOf(".")+1);
    }

    public String getUploadFilePath(){
        String folderPath = LocalDate.now().format(DateTimeFormatter.ofPattern("/yyyy/MM/dd"));
        String uploadFolderPath = uploadPath+folderPath;

        //폴더 생성
        File uploadFolder = new File(uploadPath,folderPath);
        if(uploadFolder.exists() == false){
            uploadFolder.mkdirs();
        }

        // 폴더
        return uploadFolderPath;
    }

    public void uploadFile(MultipartFile file,String uploadFilePath,String newFileName){
        try {
            file.transferTo(new File(uploadFilePath+"/"+newFileName ));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new UploadFileFailException(ErrorCode.UPLOAD_FILE_FAIL);
        }
    }


}
