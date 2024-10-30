package com.restful.templateRestful.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class ResponseSuccess extends ResponseEntity {

    // PUT, PATCH, DELETE
    public ResponseSuccess(HttpStatus status, String message) {
        super(new Payload(status.value(), message), HttpStatus.OK);
    }

    // GET, POST
    public ResponseSuccess(HttpStatus status, String message, Object data) {
        super(new Payload(status.value(), message, data), status);
    }

    public ResponseSuccess(Payload body, HttpStatus status) {
        super(body, status);
    }

    public ResponseSuccess(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public ResponseSuccess(Payload payload, MultiValueMap<String, String> headers, int rawStatus) {
        super(payload, headers, rawStatus);
    }

    public ResponseSuccess(Payload payload, MultiValueMap<String, String> headers, HttpStatus status) {
        super(payload, headers, status);
    }

    @Getter
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Payload {
        final int status;
        final String message;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Object data;

        public Payload(int status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}
