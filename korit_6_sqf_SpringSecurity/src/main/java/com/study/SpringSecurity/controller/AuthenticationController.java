package com.study.SpringSecurity.controller;

import com.study.SpringSecurity.Dto.request.ReqSignupDto;
import com.study.SpringSecurity.aspect.annotation.ParamsAop;
import com.study.SpringSecurity.aspect.annotation.ValidAop;
import com.study.SpringSecurity.service.SigninService;
import com.study.SpringSecurity.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private SignupService signupService;

    @Autowired
    private SigninService signinService;

    @ValidAop
    @ParamsAop
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody ReqSignupDto dto, BindingResult bindingResult) {
        return ResponseEntity.created(null).body(signupService.signup(dto));
    }
    @ValidAop
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody ReqSignupDto dto, BeanPropertyBindingResult bindingResult) {
        signinService.signin(dto);
        return ResponseEntity.ok().body(signinService.signin(dto));
    }
}

//        if(bindingResult.hasErrors()) {
//            // bindingResult 에 hasErrors 에러가 있으면 밑에 리턴값을 실행한다
//            return ResponseEntity.badRequest().body(bindingResult.getFieldError());
//        }
