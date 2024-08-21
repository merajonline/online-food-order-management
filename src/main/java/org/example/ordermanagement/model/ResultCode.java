package org.example.ordermanagement.model;

import org.springframework.http.HttpStatus;

public interface ResultCode {

    String getCode();
    String getMessage();
    HttpStatus getHttpStatus();

}
