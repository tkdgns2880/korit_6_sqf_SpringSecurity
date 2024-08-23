package com.study.SpringSecurity.security.jwt;

import com.study.SpringSecurity.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider { // Jwt - 메인파일

    private final Key key;

    // JwtProvider 생성이 일어나기 위해서 secret(매개변수)가 필요하다
    // @Value 어너테이션에 yml ${jwt.secret}벨류값 가져와 문자열로 변경한다
    public JwtProvider(@Value("${jwt.secret}") String secret) {
        // key = Keys.hmacShaKeyFor(secret);
        // secret 을 바로 넣어주지 못한다
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
    public String removeBearer(String token) {
        // return token.substring(7);
        return token.substring("Bearer ".length());
                // substring() 지정한 지점부터 끝까지 짤라준다
    }
    public String generateUserToken(User user) {
        Date expireDate = new Date(new Date().getTime() + (1000l * 60 * 60 * 24 * 30));
        // (1000l(1초) * 60(1분) * 60(1시간) * 24(하루) * 30(한달))

        String token = Jwts.builder()
                .claim("userId", user.getId())
                // claim 키 벨류 값을 추가하기 위해 사용 키값을 사용하기 위해 암호화를 해야한다
                .expiration(expireDate)
                // 토큰 만료 시간을 지정해줘야한다
                .signWith(key, SignatureAlgorithm.HS256)
                // key 값의 토큰을 어떤 SignatureAlgorithm(알고리즘)으로 HS256호출해 암호화를 한다
                .compact();
        return token;
    }
    public Claims parseToken(String token) {
        JwtParser jwtParser = Jwts.parser() // 갖고온 토큰을 parser 으로 변환을 시켜야한다
                .setSigningKey(key) // Jwts 키값을 setSigningKey(key) 써라
                .build(); // 여기까지가 parser

        return jwtParser.parseClaimsJws(token).getPayload();
    }
}

// Jwt 원리
// 세션은 데이터 정보를 서버가 갖고있다 토큰은 클라이언트가 데이터 정보를 갖고있다
/**
 *  세션과 토큰의 차이점
 *  세션은 사용자의 데이터를 서버가 가지고 있다
 *  토큰은 클라이언트가 데이터를 가지고 있다
 *
 *  ex) 로그인 || 세션
 *      서버에 요청(아이디, 비밀번호)
 *      세션에 저장
 *      클라이언트한테 세션아이디만 반환
 *      다음에 다시 로그인할때 세션아이디로 세션을 판단해서 로그인 유무판단 가능하다
 *
 *  ex) 로그인 || 토큰
 *      서버에 요청
 *      토큰을 발급(암호화된 토큰을 발급)
 *      사용자한테 토큰을 반환
 *      다시 로그인 할때 사용자가 가지고 있는 토큰을 받아 서버에 있는 비밀번호로 해제 시도한다
 *          단) 토큰안에 중요정보는 넣으면 안된다 (https://jwt.io/)사이트에서 암호화된 토큰값을 넣으면 안에 정보를 볼수있다
 *              넣으면x 중요정보 ex) 사용자이름, 생년월일, 비밀번호 등
 *              주입o 정보 ex) userId
 *      서버에서 토큰 해제 후 토큰 안에있는 userId를 사용자 리스트에서 매칭
 *          매칭이 성공하면 로그인 성공된 페이지로 전환
 *              ex) 메인페이지
 *          매칭이 실패하면 로그인 페이지로 전환된다
 *              ex) 로그인 페이지
 */

// eyJhbGciOiJIUzUxMiJ9 - header/ 알고리즘으로 HS256 이 들어간다
// .eyJ1c2VySWQiOjEsImV4cCI6MTcyMjY3ODU4OH0 - payload
// .jjMvKuHQ5-8_ku898cuuxMc9oeCPtKFQzekVUb_qgE2Qwc_NqfItkn2W2-XkeuuLF_PuxV6EMqJ5yvuVny5G0Q

