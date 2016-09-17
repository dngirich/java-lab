package com.example.service.impl;

import com.example.domain.Restaurant;
import com.example.service.EntityNotFoundException;
import com.example.service.RestaurantService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
class RestaurantServiceImpl implements RestaurantService {

    private static final Map<Long, Restaurant> map = new HashMap<>();

    @Override
    public List<Restaurant> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        map.put(restaurant.getId(), restaurant);
        return restaurant;
    }

    @Override
    public void delete(Long id) {
        map.remove(id);
    }

    @Override
    public Restaurant findOne(Long id) {
        return map.get(id);
    }

    @Override
    public Restaurant update(Long id, Restaurant restaurant) {
        Restaurant existingRestaurant = map.get(id);
        if (existingRestaurant != null) {
            return map.put(id, restaurant);
        }
        throw new EntityNotFoundException("Can't update non-existent restaurant.");
    }

    @Override
    public Restaurant updatePatch(Long id, Restaurant restaurant) {
        Restaurant existingRestaurant = map.get(id);
        
        if (existingRestaurant != null) {
            if (restaurant.getName() != null) {
                existingRestaurant.setName(restaurant.getName());
            }
            
            return existingRestaurant;
        }
        return null;
    }        

}
