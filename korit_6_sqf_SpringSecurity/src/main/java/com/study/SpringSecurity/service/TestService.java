package com.study.SpringSecurity.service;

import com.study.SpringSecurity.aspect.annotation.Test2Aop;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public String aopTest() {
        return "AOP 테스트 입니다.";
    }

    @Test2Aop // Test2Aop 어너테이션을 달아주면 여기서만 호출해서 사용한다
    public void aopTest2(String name, int age) {
        System.out.println("이름 : " + name);
        System.out.println("나이 : " + age);
        System.out.println("AOP 테스트2 입니다");
    }
    @Test2Aop
    public void aopTest3(String phone, String address) {
        System.out.println("AOP 테스트3 입니다");
    }
}
