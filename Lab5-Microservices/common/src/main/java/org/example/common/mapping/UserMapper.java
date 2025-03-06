
package org.example.common.mapping;

import org.example.common.dto.UserDto;
import org.example.common.model.User;

public class UserMapper {

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
