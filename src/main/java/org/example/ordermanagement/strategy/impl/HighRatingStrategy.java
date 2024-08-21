package org.example.ordermanagement.strategy.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ordermanagement.dto.response.DishResponseDto;
import org.example.ordermanagement.strategy.RestaurantSelectionStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HighRatingStrategy implements RestaurantSelectionStrategy {

    @Override
    public DishResponseDto selectOrder(Map<Long, List<DishResponseDto.DishDto>> restroDishMap) {
        if (restroDishMap.size() == 1){
            return DishResponseDto.builder().dishDtos(restroDishMap.values().stream().toList().get(0)).build();
        }
        List<DishResponseDto.DishDto> result = restroDishMap.values().stream().toList().get(0);
        Double maxRating = result.get(0).getRestaurantRating();
        for (Map.Entry<Long, List<DishResponseDto.DishDto>> entry : restroDishMap.entrySet()) {
            if (entry.getValue().get(0).getRestaurantRating() > maxRating){
                result = entry.getValue();
                maxRating = entry.getValue().get(0).getRestaurantRating();
            }
        }
        return DishResponseDto.builder().dishDtos(result).build();
    }

}
