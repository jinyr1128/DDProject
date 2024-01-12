package com.ddproject.user.service.impl;

import com.ddproject.alarm.dto.AlarmDto;
import com.ddproject.alarm.repository.AlarmRepository;
import com.ddproject.global.exception.CustomException;
import com.ddproject.global.exception.ErrorCode;
import com.ddproject.user.entity.User;
import com.ddproject.user.dto.CheckRequestDto;
import com.ddproject.user.dto.PasswordDto;
import com.ddproject.user.dto.SignupResponseDto;
import com.ddproject.user.dto.SignupUserDto;
import com.ddproject.user.repository.UserRepository;
import com.ddproject.user.service.UserService;
import com.ddproject.user.validation.SignupValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final SignupValidator signupValidator;
    private final PasswordEncoder passwordEncoder;
    private final AlarmRepository alarmRepository;


    // TODO : implement
    @Override
    @Transactional
    public SignupResponseDto signup(SignupUserDto signupUserDto) {
        userRepository.findByUsername(signupUserDto.getUsername()).ifPresent(it -> {
            throw new CustomException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", signupUserDto.getUsername()));
        });

        User user = User.builder()
                .username(signupUserDto.getUsername())
                .password(passwordEncoder.encode(signupUserDto.getPassword()))
                .email(signupUserDto.getEmail())
                .build();

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, SignupResponseDto.class);
    }

    @Override
    @Transactional
    public boolean validateSignup(CheckRequestDto checkRequestDto) {
        // 검증할 필드 id, email 등등
        String type = checkRequestDto.getType();

        // pw, id 등등
        if(type.equals("checkId")) {
            String id = checkRequestDto.getId();
            return signupValidator.validateId(id);
        } else if(type.equals("checkEmail")) {
            String email = checkRequestDto.getEmail();
            return signupValidator.validateEmail(email);
        }

        return false;
    }

    // TODO : implement
    @Override
    @Transactional
    public void changePw(PasswordDto passwordDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();

        boolean isCorrect = passwordEncoder.matches(passwordDto.getCurrentPw(), user.getPassword());
        if (!isCorrect) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD, "비밀번호를 확인해주세요");
        }

        if (passwordDto.getCurrentPw().equals(passwordDto.getChangePw())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD, "현재 패스워드와 같습니다.");
        }

        user.changePw(passwordEncoder.encode(passwordDto.getChangePw()));
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlarmDto> alarmList(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", username));
        });

        return alarmRepository.findAllByUser(user, pageable).map(alarm -> modelMapper.map(alarm, AlarmDto.class));


    }
    
}
