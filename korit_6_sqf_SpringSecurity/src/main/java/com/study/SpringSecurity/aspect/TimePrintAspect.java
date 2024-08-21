package com.study.SpringSecurity.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TimeAspect {

    @Pointcut("@annotation(com.study.SpringSecurity.aspect.annotation.ParamsAop)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        CodeSignature signature = (CodeSignature) proceedingJoinPoint.getSignature();
        String[] paramNames = signature.getParameterNames();
        Object[] args = proceedingJoinPoint.getArgs();

        String infoPrint = "ClassName(" + signature.getDeclaringType().getSimpleName() + ") MethodName (" + signature.getName() + ")";
        String linePrint = "";
        for(int i = 0; i < infoPrint.length(); i++) {
            linePrint += "-";
        }
        log.info("{}", linePrint);
        log.info("{}", infoPrint);
                // signature.getDeclaringType().getSimpleName()
                // signature 에 들어있는 getDeclaringType(클레스타입) 클레스 getSimpleName 클레스 이름만 가지고 와라
        for(int i = 0; i < paramNames.length; i++) {
            log.info("{} >>>> {}", paramNames[i], args[i]);
        }
        log.info("{}", linePrint);

        return proceedingJoinPoint.proceed();

    }
}
