package org.example.ordermanagement.handler.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ordermanagement.constants.GeneralConstant;
import org.example.ordermanagement.dto.request.CreateOrderDto;
import org.example.ordermanagement.dto.request.ItemIdsDto;
import org.example.ordermanagement.dto.request.OrderItemDto;
import org.example.ordermanagement.dto.request.OrderUpdateDto;
import org.example.ordermanagement.dto.response.DishListDto;
import org.example.ordermanagement.dto.response.DishResponseDto;
import org.example.ordermanagement.enums.OrderStatusEnum;
import org.example.ordermanagement.enums.RestaurantStatusEnum;
import org.example.ordermanagement.exception.BixException;
import org.example.ordermanagement.handler.OrderHandler;
import org.example.ordermanagement.model.Order;
import org.example.ordermanagement.model.Restaurant;
import org.example.ordermanagement.service.OrderService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.example.ordermanagement.exception.CommonApiResultCode.INVALID_STATUS_UPDATE;
import static org.example.ordermanagement.exception.CommonApiResultCode.ORDER_CREATION_FAILED;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderHandlerImpl implements OrderHandler {

    public final OrderService orderService;
    private final RestTemplate restTemplate;

    @Override
    public Order getOrderById(Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @Override
    public Order createOrder(CreateOrderDto createOrderDto) {
        Map<Long, List<DishResponseDto.DishDto>> restroDishMap = validateAndGetFilteredRestaurant(createOrderDto);
        return orderService.createOrder(restroDishMap);
    }

    @Override
    public Order updateOrder(Order order) {
        return orderService.updateOrder(order);
    }

    @Override
    public Order updateOrderStatus(OrderUpdateDto orderUpdateDto) {
        return orderService.updateOrder(validateAndGetOrderInstance(orderUpdateDto));
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return orderService.getRestaurantById(id);
    }

    @Override
    public Order createFoodOrder(Order order) {
        return orderService.createFoodOrder(order);
    }

    public Map<Long, List<DishResponseDto.DishDto>> validateAndGetFilteredRestaurant(CreateOrderDto createOrderDto) {
        Map<Long, Integer> itemQuantityMap = new HashMap<>();
        for (OrderItemDto orderItemDto : createOrderDto.getItemList()) {
            itemQuantityMap.put(orderItemDto.getItemId(), itemQuantityMap.getOrDefault(orderItemDto.getItemId(), 0) + orderItemDto.getQuantity());
        }
        ItemIdsDto itemIdsDto = ItemIdsDto.builder()
                .itemIds(itemQuantityMap.keySet().stream().toList())
                .build();
        DishResponseDto dishResponseDto = getAllDishForItems(itemIdsDto);

        if (Objects.isNull(dishResponseDto) || Objects.isNull(dishResponseDto.getDishDtos()) || dishResponseDto.getDishDtos().isEmpty()) {
            throw BixException.of(ORDER_CREATION_FAILED);
        }

        Map<Long, List<DishResponseDto.DishDto>> restroDishMap = new HashMap<>();
        for (DishResponseDto.DishDto dishDto : dishResponseDto.getDishDtos()) {
            if (dishDto.getRestaurantStatus() == RestaurantStatusEnum.OPEN.getValue() &&
                    dishDto.getMaxItemProcessingCap() > dishDto.getCurrItemProcessingCap()) {
                dishDto.setOrderedQuantity(itemQuantityMap.get(dishDto.getItemId()));
                if (restroDishMap.containsKey(dishDto.getRestaurantId())) {
                    restroDishMap.get(dishDto.getRestaurantId()).add(dishDto);
                } else {
                    List<DishResponseDto.DishDto> dishDtoList = new ArrayList<>();
                    dishDtoList.add(dishDto);
                    restroDishMap.put(dishDto.getRestaurantId(), dishDtoList);
                }
            }
        }
        if (restroDishMap.isEmpty()) {
            throw BixException.of(ORDER_CREATION_FAILED);
        }

        int totalDistinctItems = itemQuantityMap.size();
        //int totalItems = itemQuantityMap.values().stream().mapToInt(i -> i).sum();
        for(Map.Entry<Long, List<DishResponseDto.DishDto>> entry : restroDishMap.entrySet()) {
            if (entry.getValue().size() < totalDistinctItems ||
                    entry.getValue().get(0).getMaxItemProcessingCap() < entry.getValue().get(0).getCurrItemProcessingCap() + totalDistinctItems ) {
                restroDishMap.remove(entry.getKey());
            }
        }

        if (restroDishMap.isEmpty()) {
            throw BixException.of(ORDER_CREATION_FAILED);
        }
        return restroDishMap;
    }

    private DishResponseDto getAllDishForItems(ItemIdsDto itemIdsDto) {
        log.info("Get list of dishes for ordered itemIds {}", itemIdsDto.getItemIds());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<ItemIdsDto> requestEntity = new HttpEntity<>(itemIdsDto, headers);

        DishListDto dishListDto  = restTemplate.exchange(
                GeneralConstant.getDishByItemsUrl,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<DishListDto>() {}).getBody();
        if (Objects.isNull(dishListDto) || Objects.isNull(dishListDto.getData())) {
            throw BixException.of(ORDER_CREATION_FAILED);
        }
        return dishListDto.getData();
    }

    private Order validateAndGetOrderInstance(OrderUpdateDto orderUpdateDto) {
        if (Objects.isNull(orderUpdateDto) ||  Objects.isNull(orderUpdateDto.getId()) ||
                Objects.isNull(OrderStatusEnum.fromValue(orderUpdateDto.getStatus()))) {
            throw BixException.of(INVALID_STATUS_UPDATE);
        }
        Order order = new Order();
        order.setId(orderUpdateDto.getId());
        order.setStatus(orderUpdateDto.getStatus());
        if (OrderStatusEnum.DELIVERED.getValue() == orderUpdateDto.getStatus()) {
            order.setDeliveryDate(System.currentTimeMillis());
        }
        return order;
    }

}
