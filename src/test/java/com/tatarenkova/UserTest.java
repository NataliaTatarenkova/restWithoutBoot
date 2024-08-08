package com.tatarenkova;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tatarenkova.conf.HibernateConfig;
import com.tatarenkova.conf.WebConfig;
import com.tatarenkova.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WebConfig.class, HibernateConfig.class})
@WebAppConfiguration
@ActiveProfiles(value = "dev")
public class UserTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void getUserById() throws Exception {
        System.out.println("ddd");
        mockMvc.perform(get("/users/{id}", 1))
                .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name", is("Иван")));
    }

    @Test
    public void getUserByIdNotFound() throws Exception {
        System.out.println("ddd");
        mockMvc.perform(get("/users/{id}", -2))
               .andDo(print())
               .andExpect(status().isNotFound());
    }

    @Test
    public void getUserAll() throws Exception {
        mockMvc.perform(get("/users/all"))
               .andDo(print())
               .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Иван")));
    }

    @Test
    public void userSave() throws Exception {
        mockMvc.perform(post("/users/save")
                       .content(objectMapper.writeValueAsString(User.builder().mail("newMail@mail.com").name("NewName").build()))
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .accept(MediaType.APPLICATION_JSON_VALUE))
               .andDo(print())
               .andExpect(status().isOk());
    }

    @Test
    public void userUpdate() throws Exception {
        mockMvc.perform(post("/users/update")
                       .content(objectMapper.writeValueAsString(User.builder().id(1L).mail("newMail@mail.com").name("NewName").build()))
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .accept(MediaType.APPLICATION_JSON_VALUE))
               .andDo(print())
               .andExpect(status().isOk());
    }

    @Test
    public void userDelete() throws Exception {
        mockMvc.perform(delete("/users/{id}", 1))
               .andDo(print())
               .andExpect(status().isOk());
    }


    @Test
    public void userAddTask() throws Exception {
        mockMvc.perform(post("/users/{userId}/addTasks", 1)
                       .content(objectMapper.writeValueAsString(Collections.singletonList(1)))
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .accept(MediaType.APPLICATION_JSON_VALUE))
               .andDo(print())
               .andExpect(status().isOk());
               //.andExpect(content().string("Для пользователя Иван были добавлены следующие задачи [Task1]"));
    }
}