package com.study.SpringSecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

}

// AOP - 핵심기능 . 부가기능
// AOP는 관점을 기준으로 묶어 개발하는 방식을 의미한다.
// 관점이란 어떤 기능을 구현할 때 그 기능을 '핵심 기능' 과 '부가 기능'으로 구분해 각각 하나의 관점으로 보는 것을 의미한다.
// '핵심 기능'은 비지니스 로직을 구현하는 과정에서 비지니스 로직이 처리하려는 목적 기능을 말합니다.
