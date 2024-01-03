package com.ddproject.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {
    @NotBlank
    private String currentPw;
    @Pattern(regexp = "^[A-Za-z0-9]{8,15}+$", message = "최소 8자 이상, 15자 이하의 알파벳과 숫자 조합으로 작성해주세요")
    private String changePw;
}
