package org.example.ordermanagement.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatusEnum {

    CREATED(1),
    ACCEPTED(2),
    DECLINED(3),
    DISPATCHED(4),
    DELIVERED(5),
    ;

    final int value;

    public static OrderStatusEnum fromValue(int value) {
        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            if (orderStatusEnum.getValue() == value) {
                return orderStatusEnum;
            }
        }
        return null;
    }


}
