package com.tatarenkova.controller;

import com.tatarenkova.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/get")
public class UserController {

    @GetMapping//(value = "/get")
    public User getUserById() {
        return new User();
    }
}
