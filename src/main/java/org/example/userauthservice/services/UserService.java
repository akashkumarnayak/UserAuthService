package org.example.userauthservice.services;

import org.example.userauthservice.models.User;
import org.example.userauthservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo userRepository;

    public User getUser(Long Id)
    {
        Optional<User> user = userRepository.findById(Id);
        return user.orElse(null);
    }
}
