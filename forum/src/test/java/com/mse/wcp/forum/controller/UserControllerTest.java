package com.mse.wcp.forum.controller;

import com.google.gson.Gson;
import com.mse.wcp.forum.persistence.entity.Role;
import com.mse.wcp.forum.persistence.entity.UserEntity;
import com.mse.wcp.forum.service.UserService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    @Order(1)
    public void testGetAllUsers() throws Exception {
        UserEntity userEntity = UserEntity
                .builder()
                .username("admin")
                .password("123")
                .firstName("admin")
                .lastName("admin")
                .role(Role.MODERATOR)
                .build();

        userService.create(userEntity);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()*").value(1))
                .andExpect(jsonPath("[0].username").value("admin"));
    }

    @Test
    public void testGetUser() {

    }

    @Test
    @Order(2)
    public void testCreateUser() throws Exception {
        UserEntity userEntity = UserEntity
                .builder()
                .username("admin")
                .password("123")
                .firstName("admin")
                .lastName("admin")
                .role(Role.MODERATOR)
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(userEntity);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        Optional<UserEntity> userEntity2 = userService.get(1L);
        assertTrue(userEntity2.isPresent());
    }

    @Test
    public void testUpdateUser() {

    }

    @Test
    public void testDeleteUser() {

    }
}
