package org.example.ordermanagement.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RestaurantStatusEnum {

    CLOSE(0),
    OPEN(1);

    private final int value;

}
