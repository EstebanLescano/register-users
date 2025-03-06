package com.dmh.registerusers.controller;

import com.dmh.registerusers.service.UserService;
import com.dmh.registerusers.userDTO.UserDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDTO createUser(@Valid @RequestBody UserDTO user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public Optional<UserDTO> getUser(@PathVariable String id) {
        return userService.getUser(id);
    }
}
