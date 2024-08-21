package org.example.ordermanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ordermanagement.model.Restaurant;
import org.example.ordermanagement.repository.RestaurantRepository;
import org.example.ordermanagement.service.RestaurantService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Restaurant getRestaurant(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

}
