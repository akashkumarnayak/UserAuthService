package org.example.userauthservice.models;

import jakarta.persistence.Entity;
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

    public User() {
        this.createdAt = new Date();
        this.lastUpdatedAt = new Date();
        this.state = State.ACTIVE;
    }
}
