package org.example.userauthservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.userauthservice.models.UserRole;

@Getter
@Setter
public class UserSignUpRequestDto {
    private String name;
    private String email;
    private String password;
    private UserRole role;
}
