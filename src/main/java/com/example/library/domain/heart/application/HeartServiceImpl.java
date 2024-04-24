package com.example.library.domain.heart.application;

import com.example.library.domain.heart.domain.Heart;
import com.example.library.domain.heart.domain.HeartRepository;
import com.example.library.domain.heart.domain.dto.HeartResponseDto;
import com.example.library.domain.heart.application.dto.UserSelectHeartResDto;
import com.example.library.domain.heart.application.event.CheckBookExistEvent;
import com.example.library.domain.heart.application.event.CheckUserExistEvent;
import com.example.library.domain.user.application.event.UserDeletedEvent;
import com.example.library.exception.ErrorCode;
import com.example.library.exception.exceptions.HeartBookAlreadyException;
import com.example.library.global.Events;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService{

    private final HeartRepository heartRepository;

    @Override
    @Transactional(readOnly = true)
    public UserSelectHeartResDto getMyHeartList(Long heartNo, Long userNo,int pageSize) {
        Events.raise(new CheckUserExistEvent(userNo));
        List<HeartResponseDto.Response> heartResponseDtoList = heartRepository.findHeartsByUserNo(heartNo,userNo,pageSize);

        return UserSelectHeartResDto.builder()
                .userNo(userNo)
                .heartList(heartResponseDtoList)
                .build()
                ;
    }

    @Override
    @Transactional
    public void registerHeartBook(Long userNo,Long bookNo) {
        Events.raise(new CheckUserExistEvent(userNo));
        Events.raise(new CheckBookExistEvent(bookNo));
        checkAlreadyHeartBook(userNo,bookNo);
        Heart heart = Heart.builder()
                .userNo(userNo)
                .bookNo(bookNo)
                .build()
                ;

//        selectedUser.heartBook(heart);   // 해당 객체의 heartList에 새로운 heart엔티티가 추가되지 않는 문제.. 즉, 정합성 문제 발생
        heartRepository.save(heart);
    }

    @Override
    @Transactional
    public void removeHeartBook(Long userNo,Long bookNo) {
        Events.raise(new CheckUserExistEvent(userNo));
        Events.raise(new CheckBookExistEvent(bookNo));
//        Heart selectedHeart = heartRepository.findByUserNoAndBookNo(userNo,bookNo);
//        heartRepository.deleteById(selectedHeart.getHeartNo());
        heartRepository.deleteByUserNoAndBookNo(userNo,bookNo);
    }

    private void checkAlreadyHeartBook(Long userNo, Long bookNo){
       heartRepository.getByUserNoAndBookNo(userNo, bookNo)
               .ifPresent(a->{
                   throw new HeartBookAlreadyException(ErrorCode.HEARTBOOK_ALREADY);
               });
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void userDeletedEventHandle(UserDeletedEvent evt){
        log.info("비동기 userDeletedEventHandle - Heart");
        heartRepository.deleteByUserNo(evt.getUserNo()); //만약 heart삭제 시 에러 나면 롤백되나?
    }
}
