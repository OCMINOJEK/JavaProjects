
package org.example.servicelab4.mapping;

import org.example.repositorylab4.dto.UserDto;
import org.example.repositorylab4.model.User;

public class UserMapper {
    public UserMapper() {
    }

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        if (user.getOwner() != null) {
            userDto.setOwner_id(user.getOwner().getId());
        }

        return userDto;
    }
}
