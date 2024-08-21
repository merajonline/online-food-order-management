package org.example.ordermanagement.strategy;

import org.example.ordermanagement.dto.response.DishResponseDto;

import java.util.List;
import java.util.Map;

public interface RestaurantSelectionStrategy {

    DishResponseDto selectOrder(Map<Long, List<DishResponseDto.DishDto>> restroDishMap);

}
