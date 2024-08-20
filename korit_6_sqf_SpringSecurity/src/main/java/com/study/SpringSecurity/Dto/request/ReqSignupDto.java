package com.study.SpringSecurity.Dto.request;

import com.study.SpringSecurity.domain.entity.User;
import lombok.Data;

@Data
public class ReqSignupDto {
    // 회원가입
    private String username;
    private String password;
    private String checkPassword;
    private String name;

    // toEntity 만들기전에 username 이 중복인지 password를 가렸는지 확인해야 한다
    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .name(name)
                .build();
    }
}
