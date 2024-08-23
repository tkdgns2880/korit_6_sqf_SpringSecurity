package com.study.SpringSecurity.domain.entity;

import com.study.SpringSecurity.security.principal.PrincipalUser;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@Entity
public class User {

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto_increment 기능
    // id 에 key 값이라고 정의 해놓은 코드
    private Long id;
    @Column(unique = true, nullable = false) // username 에 unique(중복불가)를 true(활성화) 넣어준다
    // unique 설정, nullable: notnull이라 생각하면됨
    private String username;
    @Column(nullable = true)
    private String password;
    @Column(nullable = true)
    private String name;

    // fetch: 엔터티를 조인했을 때 연관된 데이터를 언제 가져올지 결정(EAGER - 당장, LAZY - 나중에 사용할 때)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // Role 엔티티랑 다대다 관계
    @JoinTable(
            name = "user_roles", // 테이블 이름
            joinColumns = @JoinColumn(name = "user_id"), // 조인할 키
            inverseJoinColumns = @JoinColumn(name = "role_id") // 외래키
    )
    private Set<Role> roles;
//      @ManyToMany 만들어야 @JoinTable 사용 할 수있다

    //    @OneToMany(mappedBy = "user")
//    private Set<UserRole> userRoles = new HashSet<>();
    // HashSet<>() 초기화 및 널포인트가 뜨는걸 방지하기 위해서
    public PrincipalUser toPrincipalUser() {
        return PrincipalUser.builder()
                .userId(id)
                .username(username)
                .password(password)
                .roles(roles)
                .build();
    }
}
