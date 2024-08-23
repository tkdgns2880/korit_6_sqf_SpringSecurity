package com.study.SpringSecurity.service;

import com.study.SpringSecurity.Dto.request.ReqSignupDto;
import com.study.SpringSecurity.aspect.annotation.TimeAop;
import com.study.SpringSecurity.domain.entity.Role;
import com.study.SpringSecurity.domain.entity.User;
import com.study.SpringSecurity.repository.RoleRepository;
import com.study.SpringSecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
// @Autowired 사용하지않고 @RequiredArgsConstructor
// 메소드를 가져온다
public class SignupService {

//    @Autowired private UserRepository userRepository;
//    @Autowired private RoleRepository roleRepository;
//    @Autowired private UserRoleRepository userRoleRepository;
//    @Autowired private BCryptPasswordEncoder passwordEncoder;

    // 클레스에 @RequiredArgsConstructor 어너테이션을 활용해
    //   final 달아주면 @Autowired 어너테이션을 달지 않아도 메소드를 가져올수 있다
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @TimeAop
    @Transactional(rollbackFor = Exception.class)
    // 예외가 터치면 rollback 해라 메소드가 모두 실행되면 저장된다
//    메서드를 실행할 때 스프링은 트랜잭션을 시작하고,
//      메서드가 정상적으로 종료되면 트랜잭션을 commit하고, 예외가 발생하면 트랜잭션을 rollback합니다
    public User signup(ReqSignupDto dto) {
        User user = dto.toEntity(passwordEncoder);
        Role role = roleRepository.findByName("ROLE_USER").orElseGet(
                () -> roleRepository.save(Role.builder().name("ROLE_USER").build())
        );
        user.setRoles(Set.of(role));
        user = userRepository.save(user);
        // save(user)에서 아이디 값을 가져와 user 에 값을 넣는다
//        UserRole userRole = UserRole.builder()
//                .user(user)
//                .role(role)
//                .build();
//        userRole = userRoleRepository.save(userRole);
        return user;
    } // User(메소드)에 @ManyToMany 정의된 @JoinTable 값이 save에 저장이 된다

    public boolean isDuplicatedUsername(String username) {
        return userRepository.findByUsername(username).isPresent(); // isPresent : 값이 있는지 확인하는 것임(NULL인지 아닌지)
    }
}
