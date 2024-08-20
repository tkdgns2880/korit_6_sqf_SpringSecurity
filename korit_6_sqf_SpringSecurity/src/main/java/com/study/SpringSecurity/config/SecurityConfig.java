package com.study.SpringSecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

// 추상메소드 실행 ctrl + o
// 자물쇠 = 잠긴자물쇠 - private 열린자물쇠 - public

@EnableWebSecurity // 우리가 만든 SecurityConfig를 적용시키겠다
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http - HttpSecurity 의 객체
        http.formLogin().disable();
        http.httpBasic().disable();
        // http.sessionManagement().disable();
        // 스프링 시큐리티가 세션을 생성하지도 않고 기존의 세션을 사용하지도 않겠다(세션을 삭제함)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 스프링 시큐리티가 세션을 생성하지 않겠다 기존의 세션을 완전히 사용하지 않겠다는 뜻은 아님(세션은 살려둠)
        // JWT 등의 토큰 인증방식을 사용할 때 설정하는 것
        http.cors();
        // CORS(Cross-Origin Resource Sharing)를 활성화하여 외부 도메인에서의 요청을 허용합니다.
        // cors() - 외부의 접근 요청을 허용해주는 역활
        http.csrf().disable();
        // CSRF(Cross-Site Request Forgery) 보호를 비활성화합니다. RESTful API나 JWT 인증을 사용할 때 주로 비활성화합니다.
        // csrf() - 위조 방지 스티커(토큰) / 인증된 주소인지 확인 판별 해주는 역활
        http.authorizeRequests()
                .antMatchers("/auth/**")
                // antMatchers(문자로) "auth/**" auth 로 시작하는 모든사이트
                .permitAll()
                .anyRequest()
                .authenticated();
    }
}
