package org.example.medilinkspring.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.medilinkspring.user.entity.User;
import org.example.medilinkspring.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }


}
