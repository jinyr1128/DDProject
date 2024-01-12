package com.ddproject.global.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private String statusCode;
    private T result;

    public static <T> ApiResponse<Void> error(String statusCode){
        return new ApiResponse<>(statusCode, null);
    }
    public static <T> ApiResponse<T> error(String statusCode, T result){
        return new ApiResponse<>(statusCode, result);
    }
    //요청은 성공했지만 리턴해줄 값이 없는 경우
    public static ApiResponse<Void> success() {
        return new ApiResponse<>("SUCCESS", null);
    }

    //요청에 성공했고 결과값도 리턴해주는 경우
    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>("SUCCESS", result);
    }
}
