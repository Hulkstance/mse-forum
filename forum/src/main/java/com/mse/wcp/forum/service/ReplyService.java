package com.mse.wcp.forum.service;

import com.mse.wcp.forum.persistence.entity.ReplyEntity;

import java.util.List;
import java.util.Optional;

public interface ReplyService {

    List<ReplyEntity> getAll();

    Optional<ReplyEntity> get(Long id);

    Optional<ReplyEntity> create(ReplyEntity replyEntity);

    Optional<ReplyEntity> update(ReplyEntity replyEntity);

    void delete(Long id);

}
