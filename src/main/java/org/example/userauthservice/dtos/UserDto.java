package org.example.userauthservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.userauthservice.models.UserRole;

@Getter
@Setter
public class UserDto {

    private String name;
    private String email;
    private UserRole role;
}
