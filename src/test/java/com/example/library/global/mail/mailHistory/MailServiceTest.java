package com.example.library.global.mail.mailHistory;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

//    @InjectMocks
//    private MailService mailService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private MailRepository mailRepository;
//
//    @Mock
//    private MailHistoryRepository mailHistoryRepository;
//
//
//    @Mock
//    private JavaMailSender javaMailSender;
//
//    @Test
//    void sendMailAndSaveTest(){
//        //given
//        when(userRepository.findByUserNo(1L)).thenReturn(Optional.ofNullable(UserEntity.createOfficialUser().userNo(1L).userId("sunghyun").userEmail("sunghyun7895@naver.com").build()));
//        when(mailRepository.findByMailType("RENT")).thenReturn(Optional.ofNullable(MailEntity.builder().mailType("RENT").mailNo(1L).mailContent("렌트입니다").build()));
//        MailDto mailDto = new MailDto(1L, MailType.MAIL_JOIN);
//
//        MailHistoryEntity entity = mailService.sendMailAndSave(mailDto);
//        System.out.println(entity);
//
//
//
//    }


}