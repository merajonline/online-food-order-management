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
public class LowCostRestaurantStrategy implements RestaurantSelectionStrategy {

    @Override
    public DishResponseDto selectOrder(Map<Long, List<DishResponseDto.DishDto>> restroDishMap) {
        if (restroDishMap.size() == 1){
            return DishResponseDto.builder().dishDtos(restroDishMap.values().stream().toList().get(0)).build();
        }

        List<DishResponseDto.DishDto> result = restroDishMap.values().stream().toList().get(0);
        Double orderPrice = calculateOrderPrice(result);
        for (Map.Entry<Long, List<DishResponseDto.DishDto>> entry : restroDishMap.entrySet()) {
            Double currentOrderPrice = calculateOrderPrice(entry.getValue());
            if (currentOrderPrice < orderPrice){
                result = entry.getValue();
                orderPrice = currentOrderPrice;
            }
        }
        return DishResponseDto.builder().dishDtos(result).build();
    }

    private Double calculateOrderPrice(List<DishResponseDto.DishDto> DishDtoList) {
        Double price = 0.0;
        for (DishResponseDto.DishDto dishDto : DishDtoList) {
            price += dishDto.getPrice();
        }
        //we can also consider delivery charge
        //price += deliveryCharge;
        return price;
    }

}
