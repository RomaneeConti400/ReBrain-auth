package com.example.rebrainauth.services;

import com.example.rebrainauth.entity.UserEntity;
import com.example.rebrainauth.repository.UserRepo;
import com.example.rebrainauth.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    public UserEntity create(UserEntity userEntity) {
        userEntity.setId(UUID.randomUUID().toString());
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);
        return userRepo.save(userEntity);
    }

    public List<UserEntity> getAll() {
        return userRepo.findAll();
    }

    public UserEntity getOne(String id) {
        return userRepo.findById(id)
                .orElseThrow(() -> {
                    return new ObjectNotFoundException("User with ID " + id + " not found");
                });
    }

    public UserEntity update(String id, UserEntity updateEntity) {
        UserEntity userEntity = getEntityById(id);
        if (updateEntity.getEmail() != null) {
            userEntity.setEmail(updateEntity.getEmail());
        }
        if (updateEntity.getPassword() != null) {
            userEntity.setPassword(updateEntity.getPassword());
        }
        UserEntity updatedEntity = userRepo.save(userEntity);
        return updatedEntity;
    }

    public void delete(String id) {
        UserEntity userEntity = getEntityById(id);
        userRepo.delete(userEntity);
    }

    private UserEntity getEntityById(String id) {
        return userRepo.findById(id)
                .orElseThrow(() -> {
                    return new ObjectNotFoundException("User with ID " + id + " not found");
                });
    }
}
