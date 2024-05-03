package com.example.library.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Before("bean(*Controller)")
    public void beforeParameterLog(JoinPoint joinPoint){
        //클래스명 받아오기
        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        typeName = getTypeName(typeName);
        log.info("{} 진입",typeName);

        // 파라미터 받아오기
        Object[] args = joinPoint.getArgs();
        if (args.length <= 0) log.info("no parameter");
        for (Object arg : args) {
            if(arg!=null){
                log.info("parameter type = {}, parameter value = {}", arg.getClass().getSimpleName(),arg);
            }
        }
    }

    private String getTypeName(String typeName){
        int idx = typeName.lastIndexOf(".")+1;
        return typeName.substring(idx);

    }

}
