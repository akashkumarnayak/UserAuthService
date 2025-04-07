package org.example.userauthservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthservice.exceptions.*;
import org.example.userauthservice.models.User;
import org.example.userauthservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService implements IAuthService{

    @Autowired
    UserRepo userRepo;

    @Autowired
    SecretKey secretKey;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public User signUp(User user)
    {
        Optional<User> storedUser = userRepo.findUserByEmail(user.getEmail());

        if(storedUser.isPresent())
        {
            throw new UserAlreadyExistException("User already exist please try logging in");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return user;
    }

    public Pair<User, String> login(User user)
    {
        Optional<User> storedUser = userRepo.findUserByEmail(user.getEmail());

        if(storedUser.isEmpty())
        {
            throw new UserNotExistException("User does not exist please signup first");
        }

        String storedPassword = storedUser.get().getPassword();

        if(!bCryptPasswordEncoder.matches(user.getPassword(), storedPassword))
        {
            throw new UserCredentialsIncorrectException("Incorrect User credentials");
        }

        Map<String,Object> payload = new HashMap<>();
        payload.put("user_id",user.getId());
        Long currentTime = System.currentTimeMillis();
        payload.put("iat",currentTime);
        payload.put("exp",currentTime+24*60*60*1000);
        payload.put("issuer","Scaler");

        String token = Jwts.builder().claims(payload).signWith(secretKey).compact();

        return new Pair(storedUser.get(),token);
    }

    public boolean logout(User user)
    {
        return false;
    }

    public boolean validateToken(String token, Long userId)
    {
        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        String newToken = Jwts.builder().claims(claims).signWith(secretKey).compact();

        if(!token.equals(newToken))
        {
            throw new IncorrectTokenException("Invalid token");
        }

        Long expiry = (long)claims.get("exp");
        Long currentTime = System.currentTimeMillis();

        if(currentTime > expiry)
        {
            throw new TokenExpiredException("Token expired");
        }

        return true;

    }

}
