package com.study.SpringSecurity.controller;

import com.study.SpringSecurity.exception.ValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;

// Component이기 떄문에 IOC안에서 터진 예외만 가져올 수 있다.
@RestControllerAdvice
/*
     RestControllerAdvice 컴포먼트로 IOC 안에서만 터진 객체의 예외만 가져올수 있다
        IOC 밖에서 터는 객체의 예외는 가져올수 없다
 */
public class ExceptionControllerAdvice {

    @ExceptionHandler(ValidException.class)
    public ResponseEntity<?> validException(ValidException validException) {
        // validException 안에는 메세지랑 필드에러가 들어있다.
        return ResponseEntity.badRequest().body(validException.getFieldErrors());
        // getFieldErrors 에러들이 들어있다
        // validException 매개변수로 받아와 getFieldErrors get으로 에러코드의 정의를 가져온다
    }
    // 동일한 username이 없을때 Exception
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> usernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.badRequest().body(Set.of(new FieldError("authentication", "authentication", e.getMessage())));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsException(BadCredentialsException e) {
        return ResponseEntity.badRequest().body(Set.of(new FieldError("authentication", "authentication", e.getMessage())));
    }
}
