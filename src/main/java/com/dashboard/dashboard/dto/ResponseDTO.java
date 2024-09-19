package com.dashboard.dashboard.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class ResponseDTO {
    private final String status;
    private final Integer code;
    private final String message;


    public static ResponseDTO of(String status, int code, String message) {
        return new ResponseDTO(status,code,message);
    }
}
