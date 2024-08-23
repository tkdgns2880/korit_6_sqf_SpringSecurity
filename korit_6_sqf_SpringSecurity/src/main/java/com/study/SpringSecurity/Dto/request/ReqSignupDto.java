package com.study.SpringSecurity.Dto.request;

import com.study.SpringSecurity.domain.entity.User;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Pattern;

@Data
public class ReqSignupDto {
    // 회원가입
    @Pattern(regexp = "^[a-z0-9]{4,12}$", message = "사용자이름은 4자이상 12이하의 영소문자, 숫자 조합이어야합니다.")
    // regexp 에 gpt에서 message 를 정확히 넣어서 만들어 달라고 한값을 넣는다
    private String username;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[~!@#$%^&*?])[A-Za-z\\\\d~!@#$%^&*?]{8,16}$",
            message = "비밀번호는 8자이상 16자이하의 영대소문, 숫자, 특수문자(~!@#$%^&*?)를 포함하여 조합해야합니다.")
    private String password;
    private String checkPassword;
    @Pattern(regexp = "^[가-힣]+$", message = "이름은 한글이어야합니다.")
    private String name;

    // toEntity 만들기전에 username 이 중복인지 password를 가렸는지 확인해야 한다
    public User toEntity(BCryptPasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();
    }
}
