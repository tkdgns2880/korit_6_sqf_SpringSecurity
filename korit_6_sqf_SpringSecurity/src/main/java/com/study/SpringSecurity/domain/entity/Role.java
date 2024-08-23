package com.study.SpringSecurity.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name; // 예시) ROLE_USER, ROLE_ADMIN, ROLE_MANAGER

//    @OneToMany(mappedBy = "role")
//    private Set<UserRole> userRoles = new HashSet<>();
    // HashSet<>() 초기화 및 널포인트가 뜨는걸 방지하기 위해서
}
