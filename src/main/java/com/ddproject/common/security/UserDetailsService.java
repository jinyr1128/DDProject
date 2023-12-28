package com.ddproject.common.security;

import com.ddproject.user.dto.UserDto;
import com.ddproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.ddproject.user.domain.User;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> result = userRepository.findByUsername(username);
        User user = result.orElseThrow();

        UserDto userDto = new UserDto(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));

        return userDto;
    }
}
