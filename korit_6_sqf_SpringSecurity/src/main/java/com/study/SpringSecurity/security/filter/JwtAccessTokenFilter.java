package com.study.SpringSecurity.security.filter;

import com.study.SpringSecurity.domain.entity.User;
import com.study.SpringSecurity.repository.UserRepository;
import com.study.SpringSecurity.security.jwt.JwtProvider;
import com.study.SpringSecurity.security.principal.PrincipalUser;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAccessTokenFilter extends GenericFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String bearerAccessToken = request.getHeader("Authorization"); // null 인지 확인하기 위해 if문을 사용
        if(bearerAccessToken !=null) {
            String accessToken = jwtProvider.removeBearer(bearerAccessToken);
            Claims claims = null;
            try{
                claims = jwtProvider.parseToken(accessToken);
            } catch (Exception error) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            Long userId = ((Integer) claims.get("userId")).longValue();
            // 리터럴 떄문에 Integer 변환 후 받아온다
            Optional<User> optionalUser = userRepository.findById(userId);
            if(optionalUser.isEmpty()) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            PrincipalUser principalUser = optionalUser.get().toPrincipalUser();
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalUser, principalUser.getPassword(), principalUser.getAuthorities());
            // UsernamePasswordAuthenticationToken 모든 권한을 다 넣어준다
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 토큰을 꺼내와야한다 Header에 Authorization(정해진 키값) 으로 키값을 가져온다

        // setAuthentication 인증되야 doFilter

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
