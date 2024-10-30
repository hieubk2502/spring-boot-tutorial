package com.restful.templateRestful.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public class ResponseData<T> {
    int status;
    String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;

    public ResponseData(int status, String message){
        this.status = status;
        this.message = message;
    }
}
