package com.ddproject.alarm.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class EmitterRepository {
    private Map<String, SseEmitter> emitterMap = new HashMap<>();

    public Optional<SseEmitter> get(String username) {
        final String key = getKey(username);
        return Optional.ofNullable(emitterMap.get(key));
    }
    public void delete(String username) {
        emitterMap.remove(getKey(username));
    }
    public SseEmitter save(String username, SseEmitter sseEmitter) {
        final String key = getKey(username);
        emitterMap.put(key, sseEmitter);
        return sseEmitter;
    }

    private String getKey(String username) {
        return "" + username;
    }
}
