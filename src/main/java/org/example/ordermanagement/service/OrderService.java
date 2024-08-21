package org.example.ordermanagement.service;

import org.example.ordermanagement.dto.response.DishResponseDto;
import org.example.ordermanagement.model.Order;
import org.example.ordermanagement.model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OrderService {

    Order getOrderById(Long orderId);
    Order createOrder(Map<Long, List<DishResponseDto.DishDto>> restroDishMap);
    Order updateOrder(Order order);
    Order createFoodOrder(Order order);

    Restaurant getRestaurantById(Long id);

}
