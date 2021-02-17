package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);

    private final CartRepository cartRepository =  Mockito.mock(CartRepository.class);

    private final BCryptPasswordEncoder bCryptPasswordEncoder= Mockito.mock(BCryptPasswordEncoder.class);

    @Before
    public void setUpTest() {
        userController = new UserController();
        TestUtils.injectObjects(userController,"userRepository", userRepository);
        TestUtils.injectObjects(userController,"cartRepository", cartRepository);
        TestUtils.injectObjects(userController,"encoder", bCryptPasswordEncoder);
    }

    User createTestUser() {
        User testUser = new User();
        testUser.setId(0);
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        return  testUser;
    }

    @Test
    public void createUserHappyPath() throws Exception {
        when(bCryptPasswordEncoder.encode("password")).thenReturn("hashed");
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("semih");
        request.setPassword("password");
        request.setConfirmPassword("password");

        ResponseEntity<User> responseEntity = userController.createUser(request);
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        User user = responseEntity.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("semih", user.getUsername());
        assertEquals("hashed", user.getPassword());
    }

    @Test
    public void createUserWrongPathPasswordShort() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("semih");
        request.setPassword("pass");
        request.setConfirmPassword("pass");

        ResponseEntity<User> responseEntity = userController.createUser(request);
        assertNotNull(responseEntity);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void createUserWrongPathWrongConfirmPassword() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("semih");
        request.setPassword("passdsfdsfdsfdsf");
        request.setConfirmPassword("dsfdsfdsfdsf");

        ResponseEntity<User> responseEntity = userController.createUser(request);
        assertNotNull(responseEntity);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void findUserByIdHappyPath() throws Exception {

        User user = createTestUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.findById(user.getId());
        assertEquals(200, response.getStatusCodeValue());

    }
    @Test
    public void findUserByIdWrongPath() throws Exception {

        User user = createTestUser();

        ResponseEntity<User> response = userController.findById(user.getId());
        assertEquals(404, response.getStatusCodeValue());

    }

    @Test
    public void findUserByNameHappyPath() throws Exception {

        User user = createTestUser();

        ResponseEntity<User> response = userController.findByUserName(user.getUsername());
        assertEquals(404, response.getStatusCodeValue());

    }


}
