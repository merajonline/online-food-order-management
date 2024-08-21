package org.example.ordermanagement.provider;

import lombok.RequiredArgsConstructor;
import org.example.ordermanagement.enums.RestaurantSelectionStrategyEnum;
import org.example.ordermanagement.strategy.RestaurantSelectionStrategy;
import org.example.ordermanagement.strategy.impl.HighRatingStrategy;
import org.example.ordermanagement.strategy.impl.LowCostRestaurantStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RestaurantSelectionProvider {

    @Value("${restaurant.selection.strategy}")
    private int restaurantSelectionStrategy;

    private final ApplicationContext applicationContext;

    public RestaurantSelectionStrategy getSelectedRestaurant() {
        if (restaurantSelectionStrategy == RestaurantSelectionStrategyEnum.LOW_COST_RESTAURANT.getValue()) {
            return applicationContext.getBean(LowCostRestaurantStrategy.class);
        } else {
            return applicationContext.getBean(HighRatingStrategy.class);
        }
    }
}
