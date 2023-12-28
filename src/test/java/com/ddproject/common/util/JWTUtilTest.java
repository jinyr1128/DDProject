package com.ddproject.common.util;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class JWTUtilTest {
    @Autowired
    private JWTUtil jwtUtil;
    @Test
    void name() {
        String jwtStr = jwtUtil.generateToken(Map.of("username", "user1"),1);

        log.info(jwtStr);

        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);

        log.info(claim.get("username"));
    }
}