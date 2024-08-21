package org.example.ordermanagement.handler;

import org.example.ordermanagement.dto.request.CreateOrderDto;
import org.example.ordermanagement.dto.request.OrderUpdateDto;
import org.example.ordermanagement.model.Order;
import org.example.ordermanagement.model.Restaurant;

public interface OrderHandler {

    Order getOrderById(Long orderId);
    Order createOrder(CreateOrderDto createOrderDto);
    Order updateOrder(Order order);
    Restaurant getRestaurantById(Long id);
    Order createFoodOrder(Order order);
    Order updateOrderStatus(OrderUpdateDto orderUpdateDto);

}
