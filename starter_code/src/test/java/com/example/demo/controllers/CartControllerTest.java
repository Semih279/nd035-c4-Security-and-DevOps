package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    private CartController cartController;

    private final CartRepository cartRepository = mock(CartRepository.class);

    private final ItemRepository itemRepository = mock(ItemRepository.class);

    private final UserRepository userRepository = mock(UserRepository.class);

    @Before
    public void setUp(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController,"cartRepository",cartRepository);
        TestUtils.injectObjects(cartController,"userRepository",userRepository);
        TestUtils.injectObjects(cartController,"itemRepository",itemRepository);

    }
    @Test
    public void addToCartHappyPath(){
        User user = TestUtils.createTestUser();
        Item item = TestUtils.createTestItem();
        Cart cart = TestUtils.createTestCart();
        user.setCart(cart);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        when(itemRepository.findById(0L)).thenReturn(Optional.of(item));
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(0L);
        modifyCartRequest.setQuantity(2);
        modifyCartRequest.setUsername(user.getUsername());
        ResponseEntity<Cart> responseEntity = cartController.addTocart(modifyCartRequest);

        assertNotNull(responseEntity);
        assertEquals(200,responseEntity.getStatusCodeValue());

        Cart testCart = responseEntity.getBody();
        assertNotNull(testCart);
    }
    @Test
    public void addToCartWrongPathUser(){
        User user = TestUtils.createTestUser();
        Item item = TestUtils.createTestItem();
        Cart cart = TestUtils.createTestCart();
        user.setCart(cart);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
        when(itemRepository.findById(0L)).thenReturn(Optional.of(item));
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(0L);
        modifyCartRequest.setQuantity(2);
        modifyCartRequest.setUsername(user.getUsername());
        ResponseEntity<Cart> responseEntity = cartController.addTocart(modifyCartRequest);

        assertNotNull(responseEntity);
        assertEquals(404,responseEntity.getStatusCodeValue());

    }

    @Test
    public void removeFromcartHappyPath(){
        User user = TestUtils.createTestUser();
        Item item = TestUtils.createTestItem();
        Cart cart = TestUtils.createTestCart();
        user.setCart(cart);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        when(itemRepository.findById(0L)).thenReturn(Optional.of(item));
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(0L);
        modifyCartRequest.setQuantity(2);
        modifyCartRequest.setUsername(user.getUsername());
        ResponseEntity<Cart> responseEntity = cartController.removeFromcart(modifyCartRequest);

        assertNotNull(responseEntity);
        assertEquals(200,responseEntity.getStatusCodeValue());

        Cart testCart = responseEntity.getBody();
        assertNotNull(testCart);
    }

    @Test
    public void removeFromcartWrongPath(){
        User user = TestUtils.createTestUser();
        Item item = TestUtils.createTestItem();
        Cart cart = TestUtils.createTestCart();
        user.setCart(cart);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
        when(itemRepository.findById(0L)).thenReturn(Optional.of(item));
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(0L);
        modifyCartRequest.setQuantity(2);
        modifyCartRequest.setUsername(user.getUsername());
        ResponseEntity<Cart> responseEntity = cartController.removeFromcart(modifyCartRequest);

        assertNotNull(responseEntity);
        assertEquals(404,responseEntity.getStatusCodeValue());
    }

}
