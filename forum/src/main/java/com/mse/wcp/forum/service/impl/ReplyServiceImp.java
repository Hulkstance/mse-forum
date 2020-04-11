package com.mse.wcp.forum.service.impl;

import com.mse.wcp.forum.persistence.entity.ReplyEntity;
import com.mse.wcp.forum.persistence.repository.ReplyRepository;
import com.mse.wcp.forum.service.ReplyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReplyServiceImp implements ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Override
    public List<ReplyEntity> getAll() {
        return replyRepository.findAll();
    }

    @Override
    public Optional<ReplyEntity> get(Long id) {
        return replyRepository.findById(id);
    }

    @Override
    public Optional<ReplyEntity> create(ReplyEntity replyEntity) {
        return Optional.of(replyRepository.save(replyEntity));
    }

    @Override
    public Optional<ReplyEntity> update(ReplyEntity replyEntity) {
        Optional<ReplyEntity> replyToUpdate = get(replyEntity.getId());

        if (!replyToUpdate.isPresent()) {
            throw new IllegalArgumentException("Reply does not exist.");
        }

        replyToUpdate.get().setContent(replyEntity.getContent());

        return Optional.of(replyRepository.save(replyToUpdate.get()));
    }

    @Override
    public void delete(Long id) {
        Optional<ReplyEntity> replyEntity = get(id);
        replyEntity.ifPresent(entity -> replyRepository.delete(entity));
    }

}
