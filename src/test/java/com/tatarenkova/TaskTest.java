package com.tatarenkova;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tatarenkova.conf.HibernateConfig;
import com.tatarenkova.conf.WebConfig;
import com.tatarenkova.entity.Task;
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
public class TaskTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void getTaskById() throws Exception {
        mockMvc.perform(get("/tasks/{id}", 1L))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name", is("Task1")));
    }

    @Test
    public void deleteTaskById() throws Exception {
        mockMvc.perform(delete("/tasks/{id}", 1L))
               .andDo(print())
               .andExpect(status().isOk());
    }

    @Test
    public void createTask() throws Exception {
        mockMvc.perform(post("/tasks", 1L)
                       .content(objectMapper.writeValueAsString(Task.builder().creationDate(new Date()).deadLine(new Date()).name("Task2").description("descr").build()))
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .accept(MediaType.APPLICATION_JSON_VALUE))
               .andDo(print())
               .andExpect(status().isOk());
    }


}
