package com.example.controller;

import com.example.domain.Restaurant;
import com.example.domain.Restaurant.WorkTime;
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
import org.junit.Ignore;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerCreateTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void create_NewRestaurant_ShouldCreateRestaurantAndReturnCreatedRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant(1, "name");
        restaurant.setWorkTime(new WorkTime("10AM", "8PM"));
        restaurant.setHalls(Arrays.asList("Banket Hall", "Smoking Hall"));

        String restaurantJSON = TestUtil.toJson(restaurant);

        when(restaurantService.save(any(Restaurant.class))).thenReturn(restaurant);

        this.mockMvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(restaurantJSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", is(restaurant.getName())))
                .andExpect(jsonPath("$.halls", hasSize(restaurant.getHalls().size())));

        verify(restaurantService, times(1)).save(any(Restaurant.class));
        verifyNoMoreInteractions(restaurantService);
    }

    @Test
    public void create_EmptyRestaurant_ShouldReturnErrorResponseStatusCode() throws Exception {
        String restaurantJSON = "";

        this.mockMvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(restaurantJSON))
                .andExpect(status().isBadRequest());

        verifyNoMoreInteractions(restaurantService);
    }

    @Test
    public void create_InvalidRestaurant_ShouldReturnErrorResponseStatusCode() throws Exception {
        String restaurantJSON = "invalid";

        this.mockMvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(restaurantJSON))
                .andExpect(status().isBadRequest());

        verifyNoMoreInteractions(restaurantService);
    }

    @Test    
    public void create_RestaurantWithoutName_ShouldReturnErrorResponseStatusCode() throws Exception {
        Restaurant restaurant = new Restaurant(1, "");
        String restaurantJSON = TestUtil.toJson(restaurant);

        this.mockMvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(restaurantJSON))
                .andExpect(status().isBadRequest());

        verifyNoMoreInteractions(restaurantService);
    }

}
