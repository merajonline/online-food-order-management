package org.example.ordermanagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.ordermanagement.exception.BixException;
import org.example.ordermanagement.exception.CommonApiResultCode;
import org.example.ordermanagement.model.ApiConverter;
import org.example.ordermanagement.model.Result;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<?>> handleException(HttpServletRequest httpServletRequest, Exception e) {
        Result<?> fail = new Result<>();
        String errorMsg = e.getMessage();
        if(e instanceof BixException) {
            BixException bixException = (BixException) e;
            fail = ApiConverter.toResult(bixException.getData());
            fail.setResultMessage(bixException.getResultCode().getMessage());
            fail.setHttpStatus(bixException.getResultCode().getHttpStatus());
        } else {
            fail.setResultCode(CommonApiResultCode.CUSTOM_ERROR);
            fail.setResultMessage(CommonApiResultCode.CUSTOM_ERROR.getMessage());
            fail.setHttpStatus(CommonApiResultCode.CUSTOM_ERROR.getHttpStatus());
        }

        fail.setTimeStamp(System.currentTimeMillis());
        return ResponseEntity.status(fail.getHttpStatus()).body(fail);
    }
}
