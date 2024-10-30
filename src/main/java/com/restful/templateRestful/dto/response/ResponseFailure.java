package com.restful.templateRestful.dto.response;

public class ResponseFailure extends ResponseData {

    public ResponseFailure(int status, String message) {
        super(status, message);
    }
}
