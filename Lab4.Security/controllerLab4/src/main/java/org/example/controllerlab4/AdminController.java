package org.example.controllerlab4;

import java.util.List;
import org.example.repositorylab4.dto.UserDto;
import org.example.servicelab4.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<UserDto> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @PostMapping({"/newUser"})
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto addUser(@RequestBody UserDto userDto) {
        return this.userService.createUser(userDto);
    }

    @PostMapping({"/newAdmin"})
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto createAdmin(@RequestBody UserDto userDto) {
        return this.userService.createAdmin(userDto);
    }
}
