package com.youn.intly.user.application;

import com.youn.intly.user.domain.Role;
import com.youn.intly.user.domain.User;
import com.youn.intly.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long enroll() {
        User user = User.builder()
                .username("YOUN")
                .role(Role.BASIC)
                .build();

        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }
}