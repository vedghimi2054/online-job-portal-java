package com.youtube.jwt.ApiResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiResponse {
    private String message;
    private Object data;

    public ApiResponse(String message) {
        this.message=message;
    }
    public ApiResponse(String message,Object data) {
        this.message=message;
        this.data=data;
    }
}
