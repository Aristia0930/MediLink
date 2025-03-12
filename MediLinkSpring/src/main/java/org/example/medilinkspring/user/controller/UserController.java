package org.example.medilinkspring.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.medilinkspring.user.entity.User;
import org.example.medilinkspring.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    //유저 유효한지 확인
    @GetMapping("/check")
    public ResponseEntity<Void> userCheck(){
        log.debug("유저토근 유효검사");
        return ResponseEntity.ok().build();
    }




}
