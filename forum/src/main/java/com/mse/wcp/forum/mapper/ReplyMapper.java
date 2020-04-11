package com.mse.wcp.forum.mapper;

import com.mse.wcp.forum.dto.ReplyDTO;
import com.mse.wcp.forum.persistence.entity.ReplyEntity;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReplyMapper {

    ReplyDTO toReplyDTO(ReplyEntity replyEntity);

    List<ReplyDTO> toReplyDTOs(List<ReplyEntity> replyEntities);

    ReplyEntity toReplyEntity(ReplyDTO replyDTO);

}
