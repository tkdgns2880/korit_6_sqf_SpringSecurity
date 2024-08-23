package com.study.SpringSecurity.aspect;

import com.study.SpringSecurity.Dto.request.ReqSignupDto;
import com.study.SpringSecurity.exception.ValidException;
import com.study.SpringSecurity.service.SigninService;
import com.study.SpringSecurity.service.SignupService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.weaver.SignatureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

@Aspect
@Component
@Slf4j
public class ValidAspect {

    @Autowired
    private SignupService signupService;

    @Pointcut("@annotation(com.study.SpringSecurity.aspect.annotation.ValidAop)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable { // proceedingJoinPoint: 핵심기능 통째를 말함
        Object[] args = proceedingJoinPoint.getArgs();
        BeanPropertyBindingResult bindingResult = null;
        for(Object arg : args) {
            if(arg.getClass() == BeanPropertyBindingResult.class) {
                bindingResult = (BeanPropertyBindingResult) arg;
                break;
            }
        }
        switch(proceedingJoinPoint.getSignature().getName()) { // getSignature :
            case "signup" :
                validSignupDto(args, bindingResult);
                break;
        }

        for(FieldError fieldError : bindingResult.getFieldErrors()) {
            System.out.println(fieldError.getField()); // 멤버변수 이름(username)
            System.out.println(fieldError.getDefaultMessage()); //
        }

        if(bindingResult.hasErrors()) {
            throw new ValidException("유효성 검사 오류", bindingResult.getFieldErrors());
        }

        return proceedingJoinPoint.proceed();
    }
        private void validSignupDto(Object[] args, BeanPropertyBindingResult bindingResult){
            for (Object arg : args) {
                if (arg.getClass() == ReqSignupDto.class) { // 두 값이 일치하면
                    // arg 가 ReqSignupDto 일때만 밑에 정의가 실행된다
                    ReqSignupDto dto = (ReqSignupDto) arg;
                    if (!dto.getPassword().equals(dto.getCheckPassword())) {
                        FieldError fieldError = new FieldError("checkPassword", "checkPassword", "비밀번호가 일치하지 않습니다.");
                        // 생성자 생성 후 ObjectName, FieldName, Error
                        bindingResult.addError(fieldError);
                    }
                    if(signupService.isDuplicatedUsername(dto.getUsername())) {
                        FieldError fieldError = new FieldError("username", "username", "이미 존재하는 사용자이름입니다.");
                        bindingResult.addError(fieldError);
                    }
                    break;
                }
            }
        }

    }
}
//                    if(bindingResult.hasErrors()) {
//                        // bindingResult 에 hasErrors 에러가 있으면 밑에 리턴값을 실행한다
//                        throw new ValidException("유효성 검사 오류", bindingResult.getFieldErrors());
// bindingResult.getFieldErrors() 에러를 일시 ValidException 값이 전달되게 정의함

//        for(FieldError fieldError : bindingResult.getFieldErrors()) {
//            System.out.println(fieldError.getField());
//            // getField - 맴버변수 이름
//            System.out.println(fieldError.getDefaultMessage());
//            // getDefaultMessage - 정의한 메세지 호출