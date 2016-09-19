package com.example.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerDeleteTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void delete_ByIdRestaurant_ShouldDeleteByIdRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant(1, "Pizza");
        String restaurantJSON = TestUtil.toJson(restaurant);
        doNothing().when(restaurantService).delete(restaurant.getId());
        this.mockMvc.perform(delete("/restaurants/" + restaurant.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(restaurantJSON))
                .andExpect(status().isOk());
        verify(restaurantService, times(1)).delete(restaurant.getId());
        verifyNoMoreInteractions(restaurantService);
    }

    @Test
    public void delete_ByNonExistentIdRestaurant_ShouldReturnErrorResponseStatusCode() throws Exception {
        Restaurant restaurant = new Restaurant(1, "Pizza");
        String restaurantJSON = TestUtil.toJson(restaurant);

        doThrow(EntityNotFoundException.class).when(restaurantService).delete(restaurant.getId());
        this.mockMvc.perform(delete("/restaurants/" + restaurant.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(restaurantJSON))
                .andExpect(status().isNotFound());
        verify(restaurantService, times(1)).delete(restaurant.getId());
        verifyNoMoreInteractions(restaurantService);
    }

}
