package com.study.SpringSecurity.repository;

import com.study.SpringSecurity.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository < 객체, 기본키값으로 Long>
    // int 말고 Long 을 쓰는 이유는 데이터베이스에 자료가 워낙 많아서
    Optional<User> findByUsername(String username);
}
