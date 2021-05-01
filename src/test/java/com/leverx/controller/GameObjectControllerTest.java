package com.leverx.controller;

import com.leverx.config.*;
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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        WebConfig.class, H2JpaConfig.class, SecurityConfig.class, RedisConfig.class, MailConfig.class
})
@WebAppConfiguration
class GameObjectControllerTest {

    @Autowired
    private WebApplicationContext webAppConfiguration;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppConfiguration).build();
    }

    @Test
    public void givenObjectsURI_whenMockMVC_thenResponseOK() throws Exception {
        mockMvc
                .perform(get("/objects")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].title").value("Some title2"));
    }

    @Test
    public void shouldGetAllGameObjectsByTraderId_whenMockMVC_thenResponseOK() throws Exception {
        mockMvc
                .perform(get("/objects/users/{id}", 2)).andDo(print())
                .andExpect(jsonPath("$[1].text").value("Some text3"));
    }

    @Test
    public void shouldReturnResponseNotFound() throws Exception {
        mockMvc
                .perform(get("/objects/users/{id}", 10)).andDo(print())
                .andExpect(status().isNotFound());
    }
}