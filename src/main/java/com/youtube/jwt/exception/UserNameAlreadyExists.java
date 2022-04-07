package com.youtube.jwt.exception;


import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(value = HttpStatus.OK)
public class UserNameAlreadyExists extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public UserNameAlreadyExists(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s is already exists with %s:'%s'",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
