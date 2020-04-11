package com.mse.wcp.forum.controller;

import com.mse.wcp.forum.dto.UserDTO;
import com.mse.wcp.forum.mapper.UserMapper;
import com.mse.wcp.forum.persistence.entity.UserEntity;
import com.mse.wcp.forum.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

/**
 * Responsible for user management.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    /**
     * Gets all users.
     *
     * @return the users.
     */
    @RolesAllowed("ADMIN")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userMapper.toUserDTOs(userService.getAll()));
    }

    /**
     * Gets user by id.
     *
     * @param id the id.
     * @return the user.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable("id") Long id) {
        Optional<UserEntity> userEntity = userService.get(id);
        return userEntity.map(entity -> ResponseEntity.ok(userMapper.toUserDTO(entity))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new user.
     *
     * @param userDTO the user DTO.
     * @return the new user DTO.
     */
    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        UserEntity userEntity = userMapper.toUserEntity(userDTO);
        userEntity.setId(null);

        Optional<UserEntity> createdUserEntity = userService.create(userEntity);

        return createdUserEntity.map(entity -> ResponseEntity.ok(userMapper.toUserDTO(entity))).orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    /**
     * Updates an existing user.
     *
     * @param id      the user id.
     * @param userDTO the user DTO.
     * @return the updated user DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
        UserEntity userEntity = userMapper.toUserEntity(userDTO);
        userEntity.setId(id);

        Optional<UserEntity> updatedUserEntity = userService.update(userEntity);

        return updatedUserEntity.map(entity -> ResponseEntity.ok(userMapper.toUserDTO(entity))).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Deletes an existing user.
     *
     * @param id the user id.
     * @return the status code.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (userService.get(id).isPresent()) {
            userService.delete(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

}
