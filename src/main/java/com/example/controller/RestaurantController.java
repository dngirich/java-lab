package com.example.controller;

import com.example.domain.Restaurant;
import com.example.service.RestaurantService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Restaurant> getAll() {
        return restaurantService.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Restaurant getById(@PathVariable Long id) {
        return restaurantService.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant create(@RequestBody @Valid Restaurant restaurant) {
        return restaurantService.save(restaurant);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public void delete(@PathVariable Long id) {
        restaurantService.delete(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{id}")
    public Restaurant update(@PathVariable Long id, @RequestBody
            @Valid Restaurant restaurant) {
        return restaurantService.update(id, restaurant);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "{id}")
    public Restaurant updatePatch(@PathVariable Long id,
            @RequestBody Restaurant restaurant) {

        return restaurantService.updatePatch(id, restaurant);
    }

}
