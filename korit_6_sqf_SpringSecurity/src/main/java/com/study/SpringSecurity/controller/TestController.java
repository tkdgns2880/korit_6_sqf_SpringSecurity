package com.study.SpringSecurity.controller;

import com.study.SpringSecurity.aspect.TestAspect2;
import com.study.SpringSecurity.security.principal.PrincipalUser;
import com.study.SpringSecurity.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
// Params = get 요청을 받을때 사용
//

// AOP를 이용해 부가기능, 핵심기능 을 만들어주겠다

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public ResponseEntity<?> get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();

        return ResponseEntity.ok(principalUser);
    }
}
//        System.out.println("get메소드 호출"); // 부가기능
//
//        System.out.println(testService.aopTest()); // 핵심기능
//        testService.aopTest2("정상훈", 31);
//        testService.aopTest3("010-7131-9648", "부산광역시 남구");
//
//        System.out.println("get메소드 리턴되기 직전"); // 부가기능
//        return ResponseEntity.ok("확인");

// https://hudi.blog/java-reflection/ 리플렉션
