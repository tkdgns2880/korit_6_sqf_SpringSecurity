package com.study.SpringSecurity.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class TimePrintAspect {

    @Pointcut("@annotation(com.study.SpringSecurity.aspect.annotation.TimeAop)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        CodeSignature signature = (CodeSignature) proceedingJoinPoint.getSignature();
        StopWatch stopWatch = new StopWatch(); // StopWatch - 스프링 안에 들어있는 객체
        stopWatch.start(); // stopWatch 실행 부가기능
        Object result = proceedingJoinPoint.proceed(); // 핵심기능
        stopWatch.stop(); // stopWatch 정지 부가기능

        String infoPrint = "ClassName(" + signature.getDeclaringType().getSimpleName() + ") MethodName (" + signature.getName() + ")";
        String linePrint = "";
        for(int i = 0; i < infoPrint.length(); i++) {
            linePrint += "-";
        }
        log.info("{}", linePrint);
        log.info("{}", infoPrint);
        log.info("Time : {}초", stopWatch.getTotalTimeSeconds());
        log.info("{}", linePrint);

        return result;

    }
}
