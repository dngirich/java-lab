package com.example.service;

import com.example.domain.Restaurant;
import java.util.List;

public interface RestaurantService {

    public List<Restaurant> findAll();

    public Restaurant save(Restaurant restaurant);

    public void delete(Long id);

    public Restaurant findOne(Long id);

    public Restaurant update(Long id, Restaurant restaurant);

    public Restaurant updatePatch(Long id, Restaurant restaurant);
}
