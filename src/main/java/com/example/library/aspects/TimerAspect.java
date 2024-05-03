package com.example.library.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class TimerAspect {

    @Pointcut("@annotation(com.example.library.annotation.Timer)")//Timer 어노테이션이 붙은 메서드에만 적용
    private void enableTimer(){}

    @Around("enableTimer()")
    public Object timerPointCut(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed(); //메서드가 실행되는 부분

        stopWatch.stop();
        log.info(String.format("[%s] total time : %s", joinPoint.getSignature().getName(), String.valueOf(stopWatch.getTotalTimeSeconds())));

        return result;
    }
}
