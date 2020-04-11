package com.mse.wcp.forum.persistence.repository;

import com.mse.wcp.forum.persistence.entity.ReplyEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

}
