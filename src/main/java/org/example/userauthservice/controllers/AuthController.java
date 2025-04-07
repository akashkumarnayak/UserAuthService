package org.example.userauthservice.controllers;

import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthservice.dtos.*;
import org.example.userauthservice.exceptions.UserCredentialsIncorrectException;
import org.example.userauthservice.exceptions.UserNotExistException;
import org.example.userauthservice.models.User;
import org.example.userauthservice.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("signup")
    private UserDto signup(@RequestBody UserSignUpRequestDto userSignUpDto)
    {
        User user = authService.signUp(from(userSignUpDto));
        return from(user);
    }

    @PostMapping("login")
    private ResponseEntity<UserDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto)
    {
        try {
            Pair<User, String> userWithToken = authService.login(from(userLoginRequestDto));
            UserDto userDto = from(userWithToken.a);
            MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
            headers.add(HttpHeaders.SET_COOKIE,userWithToken.b);
            return new ResponseEntity<>(userDto,headers,201);
        }
        catch (UserNotExistException exception)
        {
            return new ResponseEntity<>(null,null,404);
        }
        catch (UserCredentialsIncorrectException exception)
        {
            return new ResponseEntity<>(null,null,401);
        }
    }

    @PostMapping("logout")
    private Boolean logout(@RequestBody UserLogoutRequestDto userLogoutRequestDto)
    {
        return authService.logout(from(userLogoutRequestDto));
    }

    @PostMapping("validateToken")
    private ResponseEntity<Boolean> validateToken(@RequestBody ValidateTokenDto validateTokenDto)
    {
        try {
            Boolean result = authService.validateToken(validateTokenDto.getToken(), validateTokenDto.getUserId());
            return new ResponseEntity<>(result,null,200);
        }catch (Exception exception) {
            return new ResponseEntity<>(false,null,400);
        }
    }

    User from(UserLogoutRequestDto userLogoutRequestDto)
    {
        User user = new User();
        user.setEmail(userLogoutRequestDto.getEmail());
        return user;
    }

    User from(UserLoginRequestDto userLoginRequestDto)
    {
        User user = new User();
        user.setEmail(userLoginRequestDto.getEmail());
        user.setPassword(userLoginRequestDto.getPassword());
        return user;
    }

    User from(UserSignUpRequestDto userSignUpRequestDto)
    {
        User user = new User();
        user.setName(userSignUpRequestDto.getName());
        user.setEmail(userSignUpRequestDto.getEmail());
        user.setPassword(userSignUpRequestDto.getPassword());
        return user;
    }

    UserDto from(User user)
    {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
