package org.example.ordermanagement.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ordermanagement.constants.GeneralConstant;
import org.example.ordermanagement.dto.request.CreateOrderDto;
import org.example.ordermanagement.dto.request.ItemIdsDto;
import org.example.ordermanagement.dto.request.OrderUpdateDto;
import org.example.ordermanagement.dto.response.DishListDto;
import org.example.ordermanagement.handler.OrderHandler;
import org.example.ordermanagement.model.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/api/order")
public class OrderController {

    private final OrderHandler orderHandler;
    private final RestTemplate restTemplate;

    @PostMapping(value = "/create")
    public Result<Order> createFoodOrder(@Validated @RequestBody CreateOrderDto createOrderDto) {
        log.info("into createItemOrder");
        return ApiConverter.toResult(orderHandler.createOrder(createOrderDto));
    }

    @PostMapping(value = "/food/create")
    public Result<Order> createOrder(@Validated @RequestBody Order order) {
        log.info("into createOrder");
        return ApiConverter.toResult(orderHandler.createFoodOrder(order));
    }

    @GetMapping(value = "/get/{id}")
    public Result<Order> getOrder(@Validated @PathVariable(value = "id") Long id) {
        log.info("into getOrder");
        return ApiConverter.toResult(orderHandler.getOrderById(id));
    }

    @PatchMapping(value = "/update/{id}")
    public Result<Order> updateOrder(@Validated @RequestBody Order order) {
        log.info("into updateOrder");
        return ApiConverter.toResult(orderHandler.updateOrder(order));
    }

    @PatchMapping(value = "/update/status")
    public Result<Order> updateOrderStatus(@Validated @RequestBody OrderUpdateDto orderUpdateDto) {
        log.info("into updateOrderStatus");
        return ApiConverter.toResult(orderHandler.updateOrderStatus(orderUpdateDto));
    }

    @GetMapping(value = "/restro/get/{id}")
    public Result<Restaurant> getRestaurant(@Validated @PathVariable(value = "id") Long id) {
        log.info("into getRestaurant");
        return ApiConverter.toResult(orderHandler.getRestaurantById(id));
    }

    @GetMapping("/test2")
    public Result<DishListDto> test2() {
        ItemIdsDto itemIdsDto = new ItemIdsDto();
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        itemIdsDto.setItemIds(ids);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<ItemIdsDto> requestEntity = new HttpEntity<>(itemIdsDto, headers);

        DishListDto dishListDto  = restTemplate.exchange(
                GeneralConstant.getDishByItemsUrl,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<DishListDto>() {}).getBody();
        return ApiConverter.toResult(dishListDto);
    }

}
