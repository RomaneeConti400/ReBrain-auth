package com.example.rebrainauth.services;

import com.example.rebrainauth.entity.UserEntity;
import com.example.rebrainauth.exception.UserAlreadyExistsException;
import com.example.rebrainauth.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    public UserEntity create(UserEntity userEntity) {
        if (userRepo.findByEmail(userEntity.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + userEntity.getEmail() + " already exists");
        }
        userEntity.setId(UUID.randomUUID().toString());
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);
        return userRepo.save(userEntity);
    }
}