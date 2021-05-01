package com.leverx.service.impl;

import com.leverx.config.WebConfig;
import com.leverx.model.User;
import com.leverx.repository.UserRepository;
import com.leverx.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        WebConfig.class
})
@WebAppConfiguration
class UserServiceImplTest {

    @Mock
    UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Andrew1");
        user1.setLastName("Panas1");
        user1.setEmail("test1@mail.ru");
        user1.setPassword("1234");
        user1.setCreatedAt(LocalDateTime.now());

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Andrew2");
        user2.setLastName("Panas2");
        user2.setEmail("test2@mail.ru");
        user2.setPassword("12345");
        user2.setCreatedAt(LocalDateTime.now());

        List<User> users = new ArrayList<>(Arrays.asList(user1, user2));
        when(userService.findAll()).thenReturn(users);
        List<User> actualUserList = userService.findAll();

        assertEquals(users, actualUserList);
        assertNotNull(actualUserList);
    }
}