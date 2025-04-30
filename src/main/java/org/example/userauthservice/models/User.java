package org.example.userauthservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Setter
@Getter
public class User extends BaseModel{
    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User() {
        this.createdAt = new Date();
        this.lastUpdatedAt = new Date();
        this.state = State.ACTIVE;
    }
}
