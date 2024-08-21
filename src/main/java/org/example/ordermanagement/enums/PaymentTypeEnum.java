package org.example.ordermanagement.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentTypeEnum {

    PAY(0),
    REFUND(1),
    ;

    private final int value;

}
