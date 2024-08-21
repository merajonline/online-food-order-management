package org.example.ordermanagement.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.ordermanagement.model.ResultCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonApiResultCode implements ResultCode {


    CUSTOM_ERROR("-1", "Oops, something wrong happened", HttpStatus.INTERNAL_SERVER_ERROR),
    SUCCESS("1000", "Success", HttpStatus.OK),
    ORDER_CREATION_FAILED("1001", "none of the restaurant can fulfill the order", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_STATUS_UPDATE("1002", "order status request invalid", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_ORDER_ID("1003", "order id not valid", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PAYMENT_ID("1003", "payment id not valid", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_RESTAURANT_ID("1004", "restaurant id not valid", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}
