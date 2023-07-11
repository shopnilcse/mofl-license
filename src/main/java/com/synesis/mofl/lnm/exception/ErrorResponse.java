package com.synesis.mofl.lnm.exception;

import com.synesis.mofl.lnm.payload.ApiResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse extends ApiResponse{

    private String error;

    public ErrorResponse(Boolean success, String messageEn, String messageBn, String error) {
        super(success, messageEn, messageBn);
        this.error = error;
    }
    
    public ErrorResponse(String messageEn, String messageBn, String error) {
        super(messageEn, messageBn);
        this.error = error;
    }
    
    public ErrorResponse(Boolean success, String messageEn, String messageBn, String error, Object data) {
        super(success, messageEn, messageBn, data);
        this.error = error;
    }
    
    public ErrorResponse(Boolean success, String messageEn, String messageBn, Object data) {
        super(success, messageEn, messageBn, data);
    }
}
