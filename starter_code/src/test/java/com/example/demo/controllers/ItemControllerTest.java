package com.example.demo.controllers;


import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.demo.controllers.TestUtils.createTestItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUpTest() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController,"itemRepository", itemRepository);
    }



    @Test
    public void getItemsHappyPath(){
        ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
    }

    @Test
    public void getItemByNameWrongPath(){
        Item item = createTestItem();
        when(itemRepository.findByName(item.getName())).thenReturn(Collections.singletonList(item));
        ResponseEntity<List<Item>> response = itemController.getItemsByName("test");
        assertNotNull(response);
        assertEquals(404,response.getStatusCodeValue());
    }

    @Test
    public void getItemByNameHappyPath(){
        Item item = createTestItem();
        when(itemRepository.findByName(item.getName())).thenReturn(Collections.singletonList(item));
        ResponseEntity<List<Item>> response = itemController.getItemsByName(item.getName());
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());

        List<Item> itemResponse = response.getBody();
        Assert.assertNotNull(itemResponse);
        Assert.assertEquals(item.getName(), itemResponse.get(0).getName());
        Assert.assertEquals(item.getDescription(), itemResponse.get(0).getDescription());
    }

    @Test
    public void getItemByIdWrongPath(){
        Item item = createTestItem();
        when(itemRepository.findById(0L)).thenReturn(java.util.Optional.of(item));
        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        assertEquals(404,response.getStatusCodeValue());
    }

    @Test
    public void getItemByIdHappyPath(){
        Item item = createTestItem();
        when(itemRepository.findById(0L)).thenReturn(java.util.Optional.of(item));
        ResponseEntity<Item> response = itemController.getItemById(0L);
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());

        Item itemResponse = response.getBody();
        Assert.assertNotNull(itemResponse);
        Assert.assertEquals(item.getName(), itemResponse.getName());
        Assert.assertEquals(item.getDescription(), itemResponse.getDescription());
    }


}
