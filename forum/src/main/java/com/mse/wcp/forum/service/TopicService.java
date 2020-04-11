package com.mse.wcp.forum.service;

import com.mse.wcp.forum.persistence.entity.TopicEntity;

import java.util.List;
import java.util.Optional;

public interface TopicService {

    List<TopicEntity> getAll();

    Optional<TopicEntity> get(Long id);

    Optional<TopicEntity> create(TopicEntity topicEntity);

    Optional<TopicEntity> update(TopicEntity topicEntity);

    void delete(Long id);

}
