package com.ddproject.alarm.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmService {
    SseEmitter connectAlarm(String username);
    void send(Long alarmId, String username);

}
