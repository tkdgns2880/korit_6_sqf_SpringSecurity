package com.study.SpringSecurity.domain.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // id 에 key 값이라고 정의 해놓은 코드
    private Long id;
    @Column(unique = true, nullable = false) // username 에 unique(중복불가)를 true(활성화) 넣어준다
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
}
