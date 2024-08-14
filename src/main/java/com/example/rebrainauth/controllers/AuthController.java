package com.example.rebrainauth.controllers;

import com.example.rebrainauth.dto.JwtRequest;
import com.example.rebrainauth.dto.JwtResponse;
import com.example.rebrainauth.dto.UserDto;
import com.example.rebrainauth.entity.UserEntity;
import com.example.rebrainauth.mappers.UserMapper;
import com.example.rebrainauth.security.CustomUserDetailsService;
import com.example.rebrainauth.security.JwtHelper;
import com.example.rebrainauth.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager manager;
    private final JwtHelper helper;
    private final UserService userService;
    public AuthController(CustomUserDetailsService userDetailsService, AuthenticationManager manager,
                          JwtHelper helper,
                          UserService userService) {
        this.userDetailsService = userDetailsService;
        this.manager = manager;
        this.helper = helper;
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody UserDto userDto) {
        UserEntity userEntity = UserMapper.toEntity(userDto);
        UserEntity createdUser = userService.create(userEntity);
        UserDto createdToDto = UserMapper.toDto(createdUser);
        return ResponseEntity.ok(createdToDto.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        this.doAuthenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);
        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .email(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            // Попытка аутентификации
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            // В случае неудачи выбрасываем исключение с сообщением об ошибке
            throw new BadCredentialsException("Invalid Username or Password!!");
        }
    }
}