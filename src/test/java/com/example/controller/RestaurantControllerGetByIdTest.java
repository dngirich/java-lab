package com.example.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.example.domain.Restaurant;
import com.example.service.EntityNotFoundException;
import com.example.service.RestaurantService;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerGetByIdTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void get_ByIdRestaurant_ShouldReturnByIdRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant(1, "Pizza");
        restaurant.setWorkTime(new Restaurant.WorkTime("10AM", "3PM"));
        restaurant.setHalls(Arrays.asList("Banket Hall", "Smoking Hall"));

        String restaurantJSON = TestUtil.toJson(restaurant);

        when(restaurantService.findOne(restaurant.getId())).thenReturn(restaurant);
        this.mockMvc.perform(get("/restaurants/" + restaurant.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(restaurantJSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", is(restaurant.getName())))
                .andExpect(jsonPath("$.halls", hasSize(restaurant.getHalls().size())))
                .andExpect(jsonPath("$.workTime.start", is(restaurant.getWorkTime().getStart())))
                .andExpect(jsonPath("$.workTime.end", is(restaurant.getWorkTime().getEnd())));
        verify(restaurantService, times(1)).findOne(restaurant.getId());
        verifyNoMoreInteractions(restaurantService);
    }

    @Test
    public void get_ByNonExistentIdRestaurant_ShouldReturnErrorResponseStatusCode() throws Exception {
        Restaurant restaurant = new Restaurant(1, "Pizza");
        String restaurantJSON = TestUtil.toJson(restaurant);

        when(restaurantService.findOne(restaurant.getId())).thenThrow(EntityNotFoundException.class);
        this.mockMvc.perform(get("/restaurants/" + restaurant.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(restaurantJSON))
                .andExpect(status().isNotFound());

        verify(restaurantService, times(1)).findOne(restaurant.getId());
        verifyNoMoreInteractions(restaurantService);
    }

}
