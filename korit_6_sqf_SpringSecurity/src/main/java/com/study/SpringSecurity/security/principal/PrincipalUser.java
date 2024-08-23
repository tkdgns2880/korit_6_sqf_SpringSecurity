package com.study.SpringSecurity.security.principal;

import com.study.SpringSecurity.domain.entity.Role;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
public class PrincipalUser implements UserDetails {
    private Long userId;
    private String username;
    private String password;
    private Set<Role> roles;
    // PrincipalUser 생성할때 roles 만 넣어주면된다

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Collection - List<>, set 사용
        // GrantedAuthority 상속받아야 Collection 사용할수 있다

//        Set<GrantedAuthority> authorities = new HashSet<>();
//        for(Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return authorities;
        // 동일한 코드
        return roles.stream() // roles 의 객체를 stream 변환해주면 map 을 사용할수 있다
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                // SimpleGrantedAuthority 넣어줄때 getName() String (문자열)으로 만들어준다
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() { // 만료된 계정
        // 휴먼계정, 일회용계정
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 잠긴 계정
        // 비밀번호 5번 틀렸거나 휴먼계정
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 인증이 만료된 계정
        // 비밀번호 변경을 안했거나 인증요청을 못받았을때
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
