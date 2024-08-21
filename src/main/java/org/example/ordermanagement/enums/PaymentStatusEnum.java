package org.example.ordermanagement.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatusEnum {

    FAILURE(0),
    PENDING(1),
    SUCCESS(2),
    ;

    private final int value;

}
