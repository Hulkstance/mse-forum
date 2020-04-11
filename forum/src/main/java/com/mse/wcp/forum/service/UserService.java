package com.mse.wcp.forum.service;

import com.mse.wcp.forum.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserEntity> getAll();

    Optional<UserEntity> get(Long id);

    Optional<UserEntity> create(UserEntity userEntity);

    Optional<UserEntity> update(UserEntity userEntity);

    void delete(Long id);

}
