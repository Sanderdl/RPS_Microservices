package com.sanderdl.controller;

import com.sanderdl.domain.User;
import com.sanderdl.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(
            @PathVariable(value = "id")Long id
            ) {
        return userService.getUserById(id);
    }


}
