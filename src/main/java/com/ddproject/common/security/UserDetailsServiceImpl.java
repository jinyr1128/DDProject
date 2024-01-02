package com.ddproject.common.security;

import com.ddproject.global.exception.CustomException;
import com.ddproject.global.exception.ErrorCode;
import com.ddproject.user.dto.UserDto;
import com.ddproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.ddproject.user.domain.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
//        User user = userRepository.findByUsername(username).orElseThrow(() -> {
//            throw new CustomException(ErrorCode.USER_NOT_FOUND, String.format("{} not founded", username));
//        });
//
//        UserDto userDto = new UserDto(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
//
//        return userDto;

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", username));
        });

        // UserDetailsImpl 객체를 반환하도록 변경
        return new UserDetailsImpl(user);
    }
}
