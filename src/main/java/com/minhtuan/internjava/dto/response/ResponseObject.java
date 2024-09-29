package com.minhtuan.internjava.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseObject <T>{
    private final Number code;
    private final String message;
    private T data;

    public ResponseObject(Number code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseObject(Number code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
