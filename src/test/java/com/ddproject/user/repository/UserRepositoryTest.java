package com.ddproject.user.repository;


import com.ddproject.user.domain.User;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;


@SpringBootTest
@Log4j2
class UserRepositoryTest {

    @Autowired
    private UserRepository sut;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testInserts() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            User user = User.builder()
                    .username("user" + i)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            sut.save(user);
        });
    }

}