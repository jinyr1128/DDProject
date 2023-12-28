package com.ddproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckRequestDto {
    // type은 검증할 필드 예) checkId, checkPw 등등
    private String type;
    private String id;
    private String pw;
    private String email;

}
