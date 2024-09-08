package com.example.taskmanagement.security.controllers;

import com.example.taskmanagement.security.JWTGenerator;
import com.example.taskmanagement.security.dtos.AuthResponseDto;
import com.example.taskmanagement.security.dtos.LoginDto;
import com.example.taskmanagement.security.dtos.RegisterDto;
import com.example.taskmanagement.users.entities.Role;
import com.example.taskmanagement.users.entities.User;
import com.example.taskmanagement.users.repositories.RoleRepository;
import com.example.taskmanagement.users.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTGenerator jwtGenerator;

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.username(),
                loginDto.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.username())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(registerDto.username());
        user.setPassword(passwordEncoder.encode(registerDto.password()));

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("USER").get());
        //Just for easier testing
        if(registerDto.username().equals("admin")) {
            roles.add(roleRepository.findByName("ADMIN").get());
        }
        user.setRoles(roles);
        userRepository.save(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.OK);
    }


}
