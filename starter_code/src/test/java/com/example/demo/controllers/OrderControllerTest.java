package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static com.example.demo.controllers.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private OrderController orderController;

    private OrderRepository orderRepository = mock(OrderRepository.class);

    private UserRepository userRepository = mock(UserRepository.class);

    @Before
    public void setup() {
        orderController = new OrderController();
        injectObjects(orderController, "userRepository", userRepository);
        injectObjects(orderController, "orderRepository", orderRepository);
    }

    @Test
    public void submitOrderHappyPath(){
        User user = createTestUser();
        user.setCart(createTestCart());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        ResponseEntity<UserOrder> responseEntity = orderController.submit(user.getUsername());
        assertNotNull(responseEntity);
        assertEquals(200,responseEntity.getStatusCodeValue());
        UserOrder order = responseEntity.getBody();
        assertNotNull(order);
    }

    @Test
    public void submitOrderWrongPath(){
        User user = createTestUser();
        user.setCart(createTestCart());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
        ResponseEntity<UserOrder> responseEntity = orderController.submit(user.getUsername());
        assertNotNull(responseEntity);
        assertEquals(404,responseEntity.getStatusCodeValue());
    }

    @Test
    public void getOrdersHappyPath(){
        User user = createTestUser();
        user.setCart(createTestCart());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        when(orderRepository.findByUser(user)).thenReturn(Collections.singletonList(UserOrder.createFromCart(user.getCart())));
        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser(user.getUsername());
        assertNotNull(responseEntity);
        assertEquals(200,responseEntity.getStatusCodeValue());
        List<UserOrder> order = responseEntity.getBody();
        assertNotNull(order);
    }

    @Test
    public void getOrdersWrongPath(){
        User user = createTestUser();
        user.setCart(createTestCart());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser(user.getUsername());
        assertNotNull(responseEntity);
        assertEquals(404,responseEntity.getStatusCodeValue());
    }
}
