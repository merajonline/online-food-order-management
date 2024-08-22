package org.example.ordermanagement.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ordermanagement.constants.GeneralConstant;
import org.example.ordermanagement.dto.response.DishResponseDto;
import org.example.ordermanagement.enums.OrderStatusEnum;
import org.example.ordermanagement.exception.BixException;
import org.example.ordermanagement.model.*;
import org.example.ordermanagement.provider.RestaurantSelectionProvider;
import org.example.ordermanagement.repository.OrderRepository;
import org.example.ordermanagement.service.OrderService;
import org.example.ordermanagement.service.RestaurantService;
import org.example.ordermanagement.strategy.RestaurantSelectionStrategy;
import org.example.ordermanagement.util.CustomBeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.example.ordermanagement.constants.GeneralConstant.decimalPrecision;
import static org.example.ordermanagement.exception.CommonApiResultCode.INVALID_ORDER_ID;
import static org.example.ordermanagement.exception.CommonApiResultCode.INVALID_RESTAURANT_ID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    public final OrderRepository orderRepository;;
    public final RestaurantService restaurantService;
    public final RestaurantSelectionProvider restaurantSelectionProvider;


    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantService.getRestaurant(id);
    }

    private Restaurant updateRestaurant(Restaurant restaurant) {
        return restaurantService.updateRestaurant(restaurant);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }


    @Transactional
    @Override
    public Order createOrder(Map<Long, List<DishResponseDto.DishDto>> restroDishMap) {
        RestaurantSelectionStrategy strategy = restaurantSelectionProvider.getSelectedRestaurant();
        DishResponseDto dishResponseDto = strategy.selectOrder(restroDishMap);
        Order order = createOrderInstance(dishResponseDto);
        updateRestaurant(dishResponseDto);
        return orderRepository.save(order);
    }

    private void updateRestaurant(DishResponseDto dishResponseDto) {
        Long restaurantId = dishResponseDto.getDishDtos().get(0).getRestaurantId();
        int totalDistinctItems = dishResponseDto.getDishDtos().size();
        Restaurant restaurant = getRestaurantById(restaurantId);
        if (Objects.nonNull(restaurant)) {
            restaurant.setCurrItemProcessingCap(restaurant.getCurrItemProcessingCap() - totalDistinctItems);
            restaurant.setUpdatedDate(System.currentTimeMillis());
            log.info("Updating restaurant CurrItemProcessingCap : {}", restaurant.getCurrItemProcessingCap());
            updateRestaurant(restaurant);
        } else {
            throw new BixException(INVALID_RESTAURANT_ID);
        }
    }

    @Override
    public Order createFoodOrder(Order order) {
        Long currentTime = System.currentTimeMillis();
        order.setCreatedDate(currentTime);
        order.setUpdatedDate(currentTime);
        if (Objects.nonNull(order.getOrderedItems()) && !order.getOrderedItems().isEmpty()) {
            for(OrderedItem orderedItem : order.getOrderedItems()) {
                orderedItem.setCreatedDate(currentTime);
                orderedItem.setUpdatedDate(currentTime);
            }
        }
        if (Objects.isNull(order.getOrderBill())) {
            order.setOrderBill(new OrderBill());
            order.getOrderBill().setOrder(order);
        }
        order.getOrderBill().setCreatedDate(currentTime);
        order.getOrderBill().setUpdatedDate(currentTime);
        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public Order updateOrder(Order order) {
        Order orderFromDb =  getOrderById(order.getId());
        if (Objects.nonNull(orderFromDb)) {
            checkAndUpdateRestroObject(order, orderFromDb);
            CustomBeanUtils.copyNonNullProperties(order, orderFromDb);
            orderFromDb.setUpdatedDate(System.currentTimeMillis());
        } else {
            throw BixException.of(INVALID_ORDER_ID);
        }
        return orderRepository.saveAndFlush(orderFromDb);
    }

    private void checkAndUpdateRestroObject(Order order, Order orderFromDb) {
        if (orderFromDb.getStatus() == OrderStatusEnum.CREATED.getValue() &&
                (order.getStatus() == OrderStatusEnum.DECLINED.getValue() ||
                        order.getStatus() == OrderStatusEnum.DISPATCHED.getValue())) {
            Restaurant restaurant = getRestaurantById(orderFromDb.getRestaurant().getId());
            if (Objects.nonNull(restaurant)) {
                int totalDistinctItems = orderFromDb.getOrderedItems().size();
                restaurant.setCurrItemProcessingCap(restaurant.getCurrItemProcessingCap() + totalDistinctItems);
                restaurant.setUpdatedDate(System.currentTimeMillis());
                log.info("Updating restaurant CurrItemProcessingCap when order got cancelled : {}", restaurant.getCurrItemProcessingCap());
                updateRestaurant(restaurant);
            }
        }
    }



    private Order createOrderInstance(DishResponseDto dishResponseDto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(dishResponseDto.getDishDtos().get(0).getRestaurantId());

        Order order = new Order();
        Long currentTime = System.currentTimeMillis();
        order.setCreatedDate(currentTime);
        order.setUpdatedDate(currentTime);
        order.setStatus(OrderStatusEnum.CREATED.getValue());
        order.setCustomerId(12345L);
        order.setCustomerAddressId(45678L);
        order.setRestaurant(restaurant);
        order.setOrderedItems(createOrderedItems(dishResponseDto.getDishDtos(), order, currentTime));
        order.setOrderBill(createOrderBill(dishResponseDto.getDishDtos(), currentTime));
        order.getOrderBill().setOrder(order);

        return order;
    }

    private List<OrderedItem> createOrderedItems(List<DishResponseDto.DishDto> dishDtoList, Order order, Long currentTime) {
        List<OrderedItem> orderedItems = new ArrayList<>();
        for(DishResponseDto.DishDto dishDto : dishDtoList) {
            Dish dish = new Dish();
            dish.setId(dishDto.getId());

            OrderedItem orderedItem = new OrderedItem();
            orderedItem.setDish(dish);
            orderedItem.setRate(dishDto.getPrice());
            orderedItem.setQuantity(dishDto.getOrderedQuantity());
            orderedItem.setCreatedDate(currentTime);
            orderedItem.setUpdatedDate(currentTime);
            orderedItems.add(orderedItem);
            orderedItem.setOrder(order);
        }
        return orderedItems;
    }

    private OrderBill createOrderBill(List<DishResponseDto.DishDto> dishDtoList, Long currentTime) {
        OrderBill orderBill = new OrderBill();
        orderBill.setCreatedDate(currentTime);
        orderBill.setUpdatedDate(currentTime);
        orderBill.setItemTotal(calculateItemTotal(dishDtoList));
        orderBill.setPackagingCharge(calculatePackagingCharge(dishDtoList));
        orderBill.setPlatformFee(GeneralConstant.platformFee);
        orderBill.setTax(calculateTax(orderBill));

        return orderBill;
    }

    private Double calculateItemTotal(List<DishResponseDto.DishDto> dishDtoList) {
        return dishDtoList.stream().mapToDouble(dishDto -> dishDto.getPrice() * dishDto.getOrderedQuantity()).sum();
    }

    private Double calculatePackagingCharge(List<DishResponseDto.DishDto> dishDtoList) {
        return dishDtoList.stream().mapToDouble(dishDto -> GeneralConstant.chargesPerDishPerQuantity * dishDto.getOrderedQuantity()).sum();
    }

    private Double calculateTax(OrderBill orderBill) {
        double total = 0.0;
        total += orderBill.getItemTotal() + orderBill.getPackagingCharge() + orderBill.getDiscount() + orderBill.getPlatformFee();

        return round(total * GeneralConstant.rateOfTax, decimalPrecision);
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }




}
