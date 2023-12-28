package com.ddproject.user.validation;

import com.ddproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
@Log4j2
@RequiredArgsConstructor
public class SignupValidator {
    private final UserRepository userRepository;
    public boolean validateId(String id) {
        return userRepository.existsByUsername(id);
    }
    public boolean validateEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
