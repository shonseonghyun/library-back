package com.example.library.global.mail.mailHistory;

import com.example.library.global.mail.enums.MailType;
import lombok.Getter;

@Getter
public class MailDto {
    private Long userNo;
    private String email;
//    private String bookName;
    private MailType mailType;
    private String content;
    private String flg;
    private String userId;

    
    public MailDto(Long userNo,MailType type){
        this.userNo=userNo;
        this.mailType=type;
    };

    //회원 탈퇴 위한 생성자
    public MailDto(Long userNo,String userId,String email ,MailType type){
        this.userNo= userNo;
        this.userId=userId;
        this.email = email;
        this.mailType=type;
    };

    public MailDto(Long userNo, String email, MailType mailType, String content, String flg) {
        this.userNo = userNo;
        this.email = email;
        this.mailType = mailType;
        this.content = content;
        this.flg = flg;
    }

    //이메일 재발송 배치 위한 생성자
    public MailDto(String email, MailType type, String content) {
        this.email=email;
        this.mailType=type;
        this.content=content;
    }

    public MailHistoryEntity toEntity() {
        return MailHistoryEntity.builder()
                .userNo(userNo)
                .email(email)
                .content(content)
                .mailType(mailType)
                .flg(flg)
                .build()
                ;
    }

    public void setSendFailFlg() {
        flg="X";
    }
}
