//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.servicelab4.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.repositorylab4.api.OwnerRepository;
import org.example.repositorylab4.api.UserRepository;
import org.example.repositorylab4.dto.UserDto;
import org.example.repositorylab4.model.Owner;
import org.example.repositorylab4.model.User;
import org.example.servicelab4.mapping.UserMapper;
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
    private OwnerRepository ownerRepository;

    public UserService() {
    }

    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        user.setRole("OWNER");
        if (userDto.getOwner_id() != null) {
            Owner owner = (Owner)this.ownerRepository.findById(userDto.getOwner_id()).orElseThrow(() -> {
                return new RuntimeException("Owner not found");
            });
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

    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        return (List)users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByUsername(username);
        return (UserDetails)user.map(MyUserDetails::new).orElseThrow(() -> {
            return new UsernameNotFoundException(username + " ");
        });
    }
}
