package com.leverx.controller;

import com.leverx.config.*;
import com.leverx.model.Comment;
import com.leverx.model.User;
import com.leverx.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        WebConfig.class
})
@WebAppConfiguration
class CommentControllerTest {

    @Autowired
    private WebApplicationContext webAppConfiguration;


    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppConfiguration).build();
    }

    @Test
    public void shouldGetAllApprovedCommentsByTraderIdThenResponseCommentsCollectionAndStatusOK() throws Exception {
        final CommentService commentServiceMock = mock(CommentService.class);

        Long id = 2L;

        User user = new User();
        user.setId(id);
        user.setFirstName("Andrew");
        user.setLastName("Panas");
        user.setEmail("test@mail.ru");
        user.setPassword("1234");
        user.setCreatedAt(LocalDateTime.now());

        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setMessage("Test message1");
        comment1.setApproved(true);
        comment1.setCreatedAt(LocalDateTime.now());
        comment1.setUser(user);

        Comment comment2 = new Comment();
        comment2.setId(2L);
        comment2.setMessage("Test message2");
        comment2.setApproved(true);
        comment2.setCreatedAt(LocalDateTime.now());
        comment2.setUser(user);

        List<Comment> comments = new ArrayList<>(Arrays.asList(comment1, comment2));

        when(commentServiceMock.findAllApprovedByTraderId(id)).thenReturn(comments);

        mockMvc
                .perform(get("/users/{id}/comments", id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}