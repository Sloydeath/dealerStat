package com.leverx.controller;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.leverx.config.*;
import com.leverx.model.GameObject;
import com.leverx.model.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        WebConfig.class, H2JpaConfig.class, SecurityConfig.class, RedisConfig.class, MailConfig.class
})
@WebAppConfiguration
class TraderControllerTest {

    @Autowired
    private WebApplicationContext webAppConfiguration;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectWriter ow;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webAppConfiguration)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void shouldSaveNewGameObject_whenMockMVC_thenResponseCREATED() throws Exception {
        //before
        GameObject gameObject = new GameObject();
        gameObject.setTitle("Test title");
        gameObject.setText("Test text");
        String requestGameObject = ow.writeValueAsString(gameObject);
        //when
        mockMvc
                .perform(post("/traders/objects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestGameObject)
                .with(user("qwerty@mail.ru").password(passwordEncoder.encode("12345")).authorities(new SimpleGrantedAuthority(Role.TRADER.getRole()))))
                .andDo(print())
                //then
                .andExpect(status().isCreated());
    }
}