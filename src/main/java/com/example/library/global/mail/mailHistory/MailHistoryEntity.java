package com.example.library.global.mail.mailHistory;

import com.example.library.domain.book.domain.converter.BookStateConverter;
import com.example.library.global.entityListener.Entity.BaseEntity;
import com.example.library.global.mail.enums.MailType;
import com.example.library.global.mail.mailHistory.enums.MailTypeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mail_history")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MailHistoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyNo;

    private Long userNo;

    private String email;

    private String content;

    @Column(name = "type")
    @Convert(converter = MailTypeConverter.class)
    private MailType mailType;

    private String flg;

    public void sendSuccess(){
        this.flg="O";
    }

    public void sendFail(){
        this.flg="X";
    }
}
