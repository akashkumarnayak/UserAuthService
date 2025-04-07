package org.example.userauthservice.services;

import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthservice.models.User;

public interface IAuthService {

    public User signUp(User user);
    public Pair<User, String> login(User user);
    public boolean logout(User user);
    public boolean validateToken(String token, Long userId);
}
