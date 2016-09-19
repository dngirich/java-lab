package com.example.controller;

import java.util.List;
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
public class RestaurantControllerGetAllTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void get_AllRestaurants_ShouldReturnAllRestaurants() throws Exception {
        Restaurant restaurant = new Restaurant(1, "Pizza");
        restaurant.setWorkTime(new Restaurant.WorkTime("10AM", "3PM"));
        restaurant.setHalls(Arrays.asList("Banket Hall", "Smoking Hall"));

        List<Restaurant> restaurants = Arrays.asList(restaurant);

        when(restaurantService.findAll()).thenReturn(restaurants);
        this.mockMvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(restaurant.getName())))
                .andExpect(jsonPath("$[0].halls", hasSize(restaurant.getHalls().size())))
                .andExpect(jsonPath("$[0].workTime.start", is(restaurant.getWorkTime().getStart())))
                .andExpect(jsonPath("$[0].workTime.end", is(restaurant.getWorkTime().getEnd())));
        verify(restaurantService, times(1)).findAll();
        verifyNoMoreInteractions(restaurantService);
    }

    @Test
    public void get_ALLNonExistentRestaurants_ShouldReturnErrorResponseStatusCode() throws Exception {
        Restaurant restaurant = null;
        String restaurantJSON = TestUtil.toJson(restaurant);

        when(restaurantService.findAll()).thenReturn(null);
        this.mockMvc.perform(get("/restaurants/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(restaurantJSON))
                .andExpect(status().isOk());

        verify(restaurantService, times(1)).findAll();
        verifyNoMoreInteractions(restaurantService);
    }

}
