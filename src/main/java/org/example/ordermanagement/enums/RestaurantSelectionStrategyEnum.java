package org.example.ordermanagement.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RestaurantSelectionStrategyEnum {

    LOW_COST_RESTAURANT(0),
    HIGH_RATED_RESTAURANT(1),
    ;

    private final int value;
}
