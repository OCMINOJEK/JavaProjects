package org.example.externalinterfaceservice.controllers;

import java.util.List;

import org.example.common.dto.UserDto;
import org.example.externalinterfaceservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping({"/admin"})
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/users"})
    @PreAuthorize("hasRole('ADMIN')")
    public List getAllUsers() {
        return this.userService.getAllUsers();
    }

    @PostMapping({"/newUser"})
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto addUser(@RequestBody UserDto userDto) throws InterruptedException {
        return this.userService.createUser(userDto);
    }

    @PostMapping({"/newAdmin"})
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto createAdmin(@RequestBody UserDto userDto) {
        return this.userService.createAdmin(userDto);
    }
}
