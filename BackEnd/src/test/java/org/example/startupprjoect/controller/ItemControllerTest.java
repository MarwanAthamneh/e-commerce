package org.example.startupprjoect.controller;


import org.example.startupprjoect.Controller.ItemController;
import org.example.startupprjoect.Service.ServiceImpl.ItemServiceImpl;
import org.example.startupprjoect.model.Item;
import org.example.startupprjoect.security.JWTAuthenticationFilter;
import org.example.startupprjoect.security.JWTGenrator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(ItemController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemServiceImpl itemService;

    @MockBean
    private JWTGenrator jwtGenerator;  // Add this

    @MockBean
    private JWTAuthenticationFilter jwtAuthFilter;
    @Test
    void ShouldFindAllItems()throws Exception {
        List<Item> expectedItems = Arrays.asList(
                new Item("Item 1",50,"pants",50),
                new Item("Item 2",60,"shirt",40),
                new Item("Item 3",70,"dress",30)
        );
        when(itemService.getAllItems()).thenReturn(expectedItems);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/item/items"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));



    }
}
