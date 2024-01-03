package com.ddproject.alarm.service.impl;

import com.ddproject.alarm.repository.EmitterRepository;
import com.ddproject.alarm.service.AlarmService;
import com.ddproject.global.exception.CustomException;
import com.ddproject.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {
    private final static Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final static String ALARM_NAME = "alarm";
    private final EmitterRepository emitterRepository;
    @Override
    public SseEmitter connectAlarm(String username) {
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(username, sseEmitter);
        sseEmitter.onCompletion(() -> emitterRepository.delete(username));
        sseEmitter.onTimeout(() -> emitterRepository.delete(username));
        try {
            sseEmitter.send(SseEmitter.event().id("id").name(ALARM_NAME).data("connect completed"));
        } catch (IOException ioException) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return sseEmitter;
    }

    @Override
    public void send(Long alarmId, String username) {
        emitterRepository.get(username).ifPresentOrElse(sseEmitter -> {
            try {
                sseEmitter.send(SseEmitter.event().id(alarmId.toString()).name(ALARM_NAME).data("new alarm"));
            } catch(IOException exception) {
                emitterRepository.delete(username);
                throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }, () -> log.info(""));

    }
}
