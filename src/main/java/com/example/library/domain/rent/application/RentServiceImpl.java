package com.example.library.domain.rent.application;

import com.example.library.domain.rent.application.event.CheckedRentBookAvailableEvent;
import com.example.library.domain.rent.application.event.RentedBookEvent;
import com.example.library.domain.rent.application.event.ReturnedBookEvent;
import com.example.library.domain.rent.domain.RentManager;
import com.example.library.domain.rent.domain.RentRepository;
import com.example.library.domain.rent.domain.dto.RentStatusResponseDto;
import com.example.library.global.Events;
import com.example.library.global.eventListener.SendedMailEvent;
import com.example.library.global.mail.enums.MailType;
import com.example.library.global.mail.mailHistory.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService{

    private final RentRepository rentRepository;

    public void createRentManager(Long userNo){
        log.info(String.format("[createRentManager] rent Manager 생성: 유저번호[%s]",userNo.toString()));

        RentManager rentManager = RentManager.createRentManager()
                .userNo(userNo)
                .build()
                ;
        rentRepository.save(rentManager);

        log.info("[createRentManager] rent Manager 생성 완료");
    }

    //두 개의 에그리거트가 변경되므로 응용서비스에 각각 변경되야할 각 애그리거트로 진행
    @Override
    @Transactional
    public void rentBook(Long userNo,Long bookNo) {
        RentManager rentManager = rentRepository.findRentManagerByUserNo(userNo);
        //동시성은 어떻게 처리할까?
        Events.raise(new CheckedRentBookAvailableEvent(bookNo)); //해당 메소드와 동일한 트랜잭션을 물고 잇다.
        Events.raise(new RentedBookEvent(bookNo));
        rentManager.rentBook(bookNo);
        rentRepository.save(rentManager);
        Events.raise(new SendedMailEvent(new MailDto(userNo, MailType.MAIL_RENT)));
    }
    
    @Override
    @Transactional //외부API 도서 상태 변경 롤백에 대한 처리를 모르므로 유지
    public void returnBook(Long userNo, Long bookNo) {
        RentManager rentManager = rentRepository.findRentManagerWithRentedBookHistory(userNo,bookNo);
        rentManager.returnBook();
        Events.raise(new ReturnedBookEvent(bookNo));
        rentRepository.save(rentManager);
        Events.raise(new SendedMailEvent(new MailDto(userNo,MailType.MAIL_RETURN)));
    }

    @Override
    @Transactional //익셉션은 다 잡았다는 가정 하에 해낭 어노테이션 주석
    public void extendBook(Long userNo,Long bookNo){
        RentManager rentManager = rentRepository.findRentManagerWithRentedBookHistory(userNo,bookNo);
        // 만약 도서 예약이 존재했다면,
        // 도서 연장했지만 도서예약이 잡혀있다면 거절내야 한다라는 요구사항 등판
        rentManager.extendBook();
        rentRepository.save(rentManager);
        Events.raise(new SendedMailEvent(new MailDto(userNo,MailType.MAIL_EXTEND)));
    }

    public List<RentStatusResponseDto.Response> getCurrentRentStatus(Long userNo){
        List<RentStatusResponseDto.Response> rentStatusResponseDtoList = rentRepository.findUserRentStatus(userNo);
        return rentStatusResponseDtoList;
    }

}
