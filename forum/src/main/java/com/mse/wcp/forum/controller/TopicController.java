package com.mse.wcp.forum.controller;

import com.mse.wcp.forum.dto.TopicDTO;
import com.mse.wcp.forum.mapper.TopicMapper;
import com.mse.wcp.forum.persistence.entity.TopicEntity;
import com.mse.wcp.forum.persistence.entity.UserEntity;
import com.mse.wcp.forum.service.TopicService;
import com.mse.wcp.forum.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private TopicMapper topicMapper;

    /**
     * Gets all topics.
     *
     * @return the topics.
     */
    @GetMapping
    public ResponseEntity<List<TopicDTO>> getAll() {
        return ResponseEntity.ok(topicMapper.toTopicDTOs(topicService.getAll()));
    }

    /**
     * Gets topic by id.
     *
     * @param id the id.
     * @return the topic.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> get(@PathVariable("id") Long id) {
        Optional<TopicEntity> topicEntity = topicService.get(id);
        return topicEntity.map(entity -> ResponseEntity.ok(topicMapper.toTopicDTO(entity))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new topic.
     *
     * @param topicDTO the topic DTO.
     * @return the new topic DTO.
     */
    @PostMapping
    public ResponseEntity<TopicDTO> create(@RequestBody TopicDTO topicDTO) {
        UserEntity userEntity = userService.findByUsername(topicDTO.getUsername()).orElseThrow(() -> new IllegalArgumentException("User does not exist."));

        TopicEntity topicEntity = topicMapper.toTopicEntity(topicDTO);
        topicEntity.setId(null);
        topicEntity.setUser(userEntity);

        Optional<TopicEntity> createdTopicEntity = topicService.create(topicEntity);

        return createdTopicEntity.map(entity -> ResponseEntity.ok(topicMapper.toTopicDTO(entity))).orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    /**
     * Updates an existing topic.
     * @param id the topic id.
     * @param topicDTO the topic DTO.
     * @return the updated topic DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TopicDTO> update(@PathVariable("id") Long id, @RequestBody TopicDTO topicDTO) {
        UserEntity userEntity = userService.findByUsername(topicDTO.getUsername()).orElseThrow(() -> new IllegalArgumentException("User does not exist."));

        TopicEntity topicEntity = topicMapper.toTopicEntity(topicDTO);
        topicEntity.setId(id);
        topicEntity.setUser(userEntity);

        Optional<TopicEntity> updatedTopicEntity = topicService.update(topicEntity);

        return updatedTopicEntity.map(entity -> ResponseEntity.ok(topicMapper.toTopicDTO(entity))).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Deletes an existing topic.
     * @param id the topic id.
     * @return the status code.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (topicService.get(id).isPresent()) {
            topicService.delete(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

}
