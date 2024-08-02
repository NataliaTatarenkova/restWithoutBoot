package com.tatarenkova.controller;

import java.util.List;

import com.tatarenkova.entity.Task;
import com.tatarenkova.entity.User;
import com.tatarenkova.service.TaskService;
import com.tatarenkova.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    private final TaskService taskService;

    @PostMapping("/{userId}/addTasks")
    public ResponseEntity<String> addTasks(@RequestBody List<Long> taskIds, @PathVariable Long userId) {
        List<Task> tasks = taskIds.stream().map(taskService::findById).toList();
        for (Task task : tasks) {
            task.setUserId(userId);
        }
        tasks.forEach(taskService::save);
        User user = userService.getUserById(userId);
        return ResponseEntity.ok("Для пользователя " + user.getName() + " были добавлены следующие задачи "
                + tasks.stream().map(Task::getName).toList());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return "Saved";
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return "Updated";
    }

    @DeleteMapping(value = "/{id}")
    public String updateUser(@PathVariable("id")  Long id) {
        userService.deleteUser(id);
        return "Deleted";
    }
}
