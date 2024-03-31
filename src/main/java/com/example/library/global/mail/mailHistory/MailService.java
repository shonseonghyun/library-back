package com.example.library.global.mail.mailHistory;

import com.example.library.domain.user.entity.UserEntity;
import com.example.library.domain.user.repository.UserRepository;
import com.example.library.exception.ErrorCode;
import com.example.library.exception.exceptions.MailTypeNotFoundException;
import com.example.library.exception.exceptions.UserNotFoundException;
import com.example.library.global.event.SendedMailEvent;
import com.example.library.global.mail.enums.MailType;
import com.example.library.global.mail.mailForm.entity.MailEntity;
import com.example.library.global.mail.mailForm.repository.MailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MailHistoryRepository mailHistoryRepository;
    private final UserRepository userRepository;
    private final MailRepository mailRepository;

    public MailHistoryEntity sendMailAndSave(MailDto mailDto){
        MailEntity mailEntity = mailRepository.findByMailType(mailDto.getMailType().getType()).orElseThrow(()-> new MailTypeNotFoundException(ErrorCode.MAIL_TYPE_NOT_FOUND));
        UserEntity selectedUser  = null;
        String content = null;

        //회원탈퇴인 경우
        if(mailDto.getMailType().equals(MailType.MAIL_DELETE)){
            content = mailDto.getMailType().getContent(mailDto.getUserId(),mailEntity.getMailContent());
            mailDto = new MailDto(mailDto.getUserNo(),
                    mailDto.getEmail(),
                    mailDto.getMailType(),
                    content,
                    "O"
            );
        }else{
            selectedUser = userRepository.findByUserNo(mailDto.getUserNo()).orElseThrow(()->new UserNotFoundException(ErrorCode.USERNO_NOT_FOUND));
            content = mailDto.getMailType().getContent(selectedUser.getUserId(),mailEntity.getMailContent());
            mailDto = new MailDto(mailDto.getUserNo(),
                    selectedUser.getUserEmail(),
                    mailDto.getMailType(),
                    content,
                    "O"
            );
        }

        log.info(String.format("메일 발송 대상:[%s]/메일 타입:[%s]/발송내용: [%s]",mailDto.getEmail(),mailEntity.getMailType(),content));

        try{
            send(mailDto);
        }catch (MailException e){
            mailDto.setSendFailFlg();
        }

        return mailHistoryRepository.save(mailDto.toEntity());
    }

    private void send(MailDto mailDto){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mailDto.getEmail());
        simpleMailMessage.setSubject(mailDto.getMailType().getSubject());
        simpleMailMessage.setText(mailDto.getContent());
        javaMailSender.send(simpleMailMessage);
    }

    @Async
    @TransactionalEventListener(value = SendedMailEvent.class,phase = TransactionPhase.AFTER_COMMIT)
    public void handle(SendedMailEvent event){
        this.sendMailAndSave(event.getMailDto());
    }
}
