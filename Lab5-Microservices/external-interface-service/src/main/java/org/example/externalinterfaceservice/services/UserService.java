package org.example.externalinterfaceservice.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.common.dto.UserDto;
import org.example.common.mapping.OwnerMapper;
import org.example.common.mapping.UserMapper;
import org.example.common.model.Owner;
import org.example.common.model.User;
import org.example.externalinterfaceservice.messaging.OwnerServiceMessaging;
import org.example.externalinterfaceservice.security.MyUserDetails;
import org.example.externalinterfaceservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OwnerServiceMessaging ownerService;
    public UserDto createUser(UserDto userDto) throws InterruptedException {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        user.setRole("OWNER");
        if (userDto.getOwner_id() != null) {
            Owner owner = OwnerMapper.toOwner(ownerService.findByIdOwner(userDto.getOwner_id()));
            user.setOwner(owner);
        }

        this.userRepository.save(user);
        return UserMapper.toDto(user);
    }

    public UserDto createAdmin(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        user.setRole("ADMIN");
        this.userRepository.save(user);
        return UserMapper.toDto(user);
    }

    public List getAllUsers() {
        List<User> users = this.userRepository.findAll();
        return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByUsername(username);
        return user.map(MyUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}
