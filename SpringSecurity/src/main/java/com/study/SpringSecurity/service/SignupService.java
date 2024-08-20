package com.study.SpringSecurity.service;

import com.study.SpringSecurity.Dto.request.ReqSignupDto;
import com.study.SpringSecurity.domain.entity.User;
import com.study.SpringSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignupService {

    @Autowired
    private UserRepository userRepository;

    //회원가입
    public User signup(ReqSignupDto dto) {
        return userRepository.save(dto.toEntity());
        // save 의 리턴이 toEntity
    }
}
