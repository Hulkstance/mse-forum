package com.mse.wcp.forum.service.impl;

import com.mse.wcp.forum.persistence.entity.UserEntity;
import com.mse.wcp.forum.persistence.repository.UserRepository;
import com.mse.wcp.forum.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> get(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> create(UserEntity userEntity) {
        if (userRepository.findByUsername(userEntity.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists.");
        }

        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));

        return Optional.of(userRepository.save(userEntity));
    }

    @Override
    public Optional<UserEntity> update(UserEntity userEntity) {
        Optional<UserEntity> userToUpdate = get(userEntity.getId());

        if (!userToUpdate.isPresent()) {
            throw new IllegalArgumentException("User does not exist.");
        }

        userToUpdate.get().setUsername(userEntity.getUsername());
        userToUpdate.get().setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userToUpdate.get().setFirstName(userEntity.getFirstName());
        userToUpdate.get().setLastName(userEntity.getLastName());
        userToUpdate.get().setRole(userEntity.getRole());

        return Optional.of(userRepository.save(userToUpdate.get()));
    }

    @Override
    public void delete(Long id) {
        Optional<UserEntity> userEntity = get(id);
        userEntity.ifPresent(entity -> userRepository.delete(entity));
    }

}
