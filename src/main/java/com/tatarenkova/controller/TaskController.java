package com.tatarenkova.controller;

import java.util.Optional;

import com.tatarenkova.entity.Task;
import com.tatarenkova.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping()
    public String createTask(@RequestBody Task task) {
        taskService.save(task);
        return "Задача успешно создана";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return "Задача успешно удалена";
    }

    @GetMapping("{id}")
    public Object getTask(@PathVariable Long id) {
        return taskService.findById(id);
    }
}
