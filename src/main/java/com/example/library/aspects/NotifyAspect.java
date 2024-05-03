package com.example.library.aspects;

import com.example.library.annotation.NeedNotify;
import com.example.library.domain.user.application.dto.UserLoginReqDto;
import com.example.library.global.mail.domain.mail.application.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class NotifyAspect {

    private final MailService mailService;

    //메소드 정상 종료 시
    @AfterReturning(value = "@annotation(com.example.library.annotation.NeedNotify)",returning = "object")
    public void doNotifyService(JoinPoint joinPoint,Object object){
        log.info("NotifyService start");


        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        NeedNotify needNotifyAnnotation = methodSignature.getMethod().getAnnotation(NeedNotify.class);

        log.info(String.format("메일 타입[%s]",needNotifyAnnotation.type().toString()));

        Object[] args = joinPoint.getArgs();
        for(Object obj : args) {
            log.info("type : "+obj.getClass().getSimpleName());
            if(obj instanceof UserLoginReqDto){
                log.info("타입입니다.");
            }
            log.info("type : "+obj.getClass().getName());
            log.info("value : "+obj);
        }

        log.info(joinPoint.getSignature().getName());
    }

}
