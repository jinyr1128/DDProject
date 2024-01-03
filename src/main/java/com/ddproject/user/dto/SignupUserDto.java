package com.ddproject.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupUserDto {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message="최소 4자 이상, 10자 이하의 알파벳 소문자와 숫자 조합으로 작성해주세요")
    //최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)
    private String username;
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "올바른 이메일 형식으로 작성해주세요")
    private String email;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9]{8,15}+$", message = "최소 8자 이상, 15자 이하의 알파벳과 숫자 조합으로 작성해주세요")
    //최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)
    private String password;
}
