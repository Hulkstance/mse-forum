package com.mse.wcp.forum.service.impl;

import com.mse.wcp.forum.persistence.entity.TopicEntity;
import com.mse.wcp.forum.persistence.repository.TopicRepository;
import com.mse.wcp.forum.service.TopicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public List<TopicEntity> getAll() {
        return topicRepository.findAll();
    }

    @Override
    public Optional<TopicEntity> get(Long id) {
        return topicRepository.findById(id);
    }

    @Override
    public Optional<TopicEntity> create(TopicEntity topicEntity) {
        return Optional.of(topicRepository.save(topicEntity));
    }

    @Override
    public Optional<TopicEntity> update(TopicEntity topicEntity) {
        Optional<TopicEntity> topicToUpdate = get(topicEntity.getId());

        if (!topicToUpdate.isPresent()) {
            throw new IllegalArgumentException("Topic does not exist.");
        }

        topicToUpdate.get().setTitle(topicEntity.getTitle());

        return Optional.of(topicRepository.save(topicToUpdate.get()));
    }

    @Override
    public void delete(Long id) {
        Optional<TopicEntity> topicEntity = get(id);
        topicEntity.ifPresent(entity -> topicRepository.delete(entity));
    }

}
