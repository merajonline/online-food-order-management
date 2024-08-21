package org.example.ordermanagement.service;


import org.example.ordermanagement.model.Restaurant;

public interface RestaurantService {

    Restaurant getRestaurant(Long id);

    Restaurant updateRestaurant(Restaurant restaurant);

}
