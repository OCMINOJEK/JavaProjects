
package org.example.repositorylab4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String role;
    private Long owner_id;

}
