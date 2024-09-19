package com.dashboard.dashboard.dto;

public class ErrorResponseDTO extends ResponseDTO{
    public ErrorResponseDTO(Integer code, String message) {
        super("failed", code, message);
    }

    public static ErrorResponseDTO of(Integer code,String message){
        return new ErrorResponseDTO(code,message);
    }

}