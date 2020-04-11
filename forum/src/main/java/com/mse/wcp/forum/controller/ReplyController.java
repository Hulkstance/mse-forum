package com.mse.wcp.forum.controller;

import com.mse.wcp.forum.dto.ReplyDTO;
import com.mse.wcp.forum.mapper.ReplyMapper;
import com.mse.wcp.forum.persistence.entity.ReplyEntity;
import com.mse.wcp.forum.persistence.entity.TopicEntity;
import com.mse.wcp.forum.persistence.entity.UserEntity;
import com.mse.wcp.forum.service.ReplyService;
import com.mse.wcp.forum.service.TopicService;
import com.mse.wcp.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/replies")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReplyMapper replyMapper;

    /**
     * Gets all replies.
     *
     * @return the replies.
     */
    @GetMapping
    public ResponseEntity<List<ReplyDTO>> getAll() {
        return ResponseEntity.ok(replyMapper.toReplyDTOs(replyService.getAll()));
    }

    /**
     * Gets reply by id.
     *
     * @param id the id.
     * @return the reply.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReplyDTO> get(@PathVariable("id") Long id) {
        Optional<ReplyEntity> topicEntity = replyService.get(id);
        return topicEntity.map(entity -> ResponseEntity.ok(replyMapper.toReplyDTO(entity))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new reply.
     *
     * @param replyDTO the reply DTO.
     * @return the new reply DTO.
     */
    @PostMapping
    public ResponseEntity<ReplyDTO> create(@RequestBody ReplyDTO replyDTO) {
        TopicEntity topicEntity = topicService.get(replyDTO.getTopicId()).orElseThrow(() -> new IllegalArgumentException("Topic does not exist."));
        UserEntity userEntity = userService.get(replyDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("User does not exist."));

        ReplyEntity replyEntity = replyMapper.toReplyEntity(replyDTO);
        replyEntity.setId(null);
        replyEntity.setTopic(topicEntity);
        replyEntity.setUser(userEntity);

        Optional<ReplyEntity> createdReplyEntity = replyService.create(replyEntity);

        return createdReplyEntity.map(entity -> ResponseEntity.ok(replyMapper.toReplyDTO(entity))).orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    /**
     * Updates an existing reply.
     *
     * @param id       the reply id.
     * @param replyDTO the reply DTO.
     * @return the updated reply DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReplyDTO> update(@PathVariable("id") Long id, @RequestBody ReplyDTO replyDTO) {
        TopicEntity topicEntity = topicService.get(replyDTO.getTopicId()).orElseThrow(() -> new IllegalArgumentException("Topic does not exist."));
        UserEntity userEntity = userService.get(replyDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("User does not exist."));

        ReplyEntity replyEntity = replyMapper.toReplyEntity(replyDTO);
        replyEntity.setId(id);
        replyEntity.setTopic(topicEntity);
        replyEntity.setUser(userEntity);

        Optional<ReplyEntity> updatedReplyEntity = replyService.update(replyEntity);

        return updatedReplyEntity.map(entity -> ResponseEntity.ok(replyMapper.toReplyDTO(entity))).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Deletes an existing reply.
     *
     * @param id the reply id.
     * @return the status code.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (replyService.get(id).isPresent()) {
            replyService.delete(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

}
