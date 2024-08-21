package org.example.ordermanagement.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GeneralConstant {

    public static final String getDishByItemsUrl = "http://restro/restro/v1/api/restaurant/dish/getDishByItemId";
    public static final int chargesPerDishPerQuantity = 2;
    public static final double rateOfTax = 0.1; // 10%
    public static final double platformFee = 5.0;


}
