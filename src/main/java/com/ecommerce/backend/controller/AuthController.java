package com.ecommerce.backend.controller;


import com.ecommerce.backend.dto.LoginDto;
import com.ecommerce.backend.dto.RegisterResponseRecord;
import com.ecommerce.backend.dto.RegisterUserRecord;
import com.ecommerce.backend.dto.UserResDto;
import com.ecommerce.backend.entity.user.ApplicationUser;
import com.ecommerce.backend.service.AuthenticationServiceImpl;
import com.ecommerce.backend.util.LoginValidation;
import com.ecommerce.backend.util.RegisterValidation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationServiceImpl authenticationServiceImpl;
    @PostMapping("/register")
    public RegisterResponseRecord register(@RequestBody RegisterUserRecord registerUser) {
        RegisterValidation.validate(registerUser);
        ApplicationUser user = authenticationServiceImpl
                .register(registerUser.fullName(), registerUser.email(),
                        registerUser.password(), registerUser.role());
        return new RegisterResponseRecord(user.getFullName(), user.getEmail(), user.getRoles());
    }

    @PostMapping("/login")
    public UserResDto login(@RequestBody LoginDto loginDto) {
        LoginValidation.validate(loginDto);
        ApplicationUser user = authenticationServiceImpl.login(loginDto.email(), loginDto.password());
        return new UserResDto(user.getId(), user.getFullName(), user.getEmail(), user.getRoles());
    }
}
