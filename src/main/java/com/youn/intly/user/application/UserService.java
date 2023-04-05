package com.youn.intly.user.application;

import com.youn.intly.user.domain.User;
import com.youn.intly.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void enroll() {
        User user = User.builder()
                .username("YOUN")
                .build();

        userRepository.save(user);
    }
}