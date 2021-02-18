package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class TestUtils {

    public static void injectObjects(Object target, String fieldName, Object toInject) {
        boolean wasPrivate = false;

        try {
            Field f = target.getClass().getDeclaredField(fieldName);

            if(!f.isAccessible()){
                f.setAccessible(true);
                wasPrivate = true;
            }
            f.set(target, toInject);
            if(wasPrivate){
                f.setAccessible(false);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }


    }
    static User createTestUser() {
        User testUser = new User();
        testUser.setId(0);
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        return  testUser;
    }

    static Item createTestItem() {
        Item testItem = new Item();
        testItem.setId(1L);
        testItem.setName("testItem");
        testItem.setPrice(BigDecimal.ONE);
        testItem.setDescription("test Description");
        return testItem;
    }

    static Cart createTestCart() {
        Cart testCart = new Cart();
        testCart.addItem(createTestItem());
        testCart.addItem(createTestItem());
        return testCart;
    }
}
