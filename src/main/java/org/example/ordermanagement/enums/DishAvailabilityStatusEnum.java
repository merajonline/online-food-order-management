package org.example.ordermanagement.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DishAvailabilityStatusEnum {

    NOT_AVAILABLE(0),
    AVAILABLE(1);

    private final int value;

}
