package com.example.controller;

import com.example.domain.Restaurant;
import com.example.service.EntityNotFoundException;
import com.example.service.RestaurantService;
import java.util.Arrays;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerUpdateTest {

    @Autowired
    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    @Ignore
    public void update_NonExistentRestaurant_ShouldReturnErrorResponseStatusCode() throws Exception {
        Restaurant restaurant = new Restaurant(1, "name");
        String restaurantJSON = TestUtil.toJson(restaurant);

        when(restaurantService.update(eq(restaurant.getId()), any(Restaurant.class)))
                .thenThrow(EntityNotFoundException.class);

        this.mockMvc.perform(put("/restaurants/" + restaurant.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(restaurantJSON))
                .andExpect(status().isNotFound());

        verify(restaurantService, times(1)).update(eq(restaurant.getId()), any(Restaurant.class));
        verifyNoMoreInteractions(restaurantService);
    }

    @Test
    public void update_Restaurant_ShouldUpdateRestaurantAndReturnUpdateRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant(1, "name");
        restaurant.setWorkTime(new Restaurant.WorkTime("12AM", "18PM"));
        restaurant.setHalls(Arrays.asList("Banket Hall", "NoSmoking Hall"));

        String restaurantJSON = TestUtil.toJson(restaurant);

        when(restaurantService.update(eq(restaurant.getId()), any(Restaurant.class))).thenReturn(restaurant);
        this.mockMvc.perform(put("/restaurants/" + restaurant.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(restaurantJSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", is(restaurant.getName())))
                .andExpect(jsonPath("$.halls", hasSize(restaurant.getHalls().size())))
                .andExpect(jsonPath("$.halls", hasSize(restaurant.getHalls()
                        .size())));

        verify(restaurantService, times(1)).update(eq(restaurant.getId()), any(Restaurant.class));
        verifyNoMoreInteractions(restaurantService);
    }

}
