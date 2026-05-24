package com.hemant.workflow_orchestrator.controller;

import com.hemant.workflow_orchestrator.models.UserModel;
import com.hemant.workflow_orchestrator.services.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserModel createUser(@RequestBody UserModel user) {
        return userService.createUser(user);
    }

    @GetMapping("/get-all-users")
    public List<UserModel> getMethodName() {
        return userService.getAllUsers();
    }

    @GetMapping("/get-user-by-id/{id}")
    public UserModel getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    
    
    
}
