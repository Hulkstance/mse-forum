package com.mse.wcp.forum.mapper;

import com.mse.wcp.forum.dto.TopicDTO;
import com.mse.wcp.forum.persistence.entity.TopicEntity;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TopicMapper {

    @Mapping(source = "user.id", target = "userId")
    TopicDTO toTopicDTO(TopicEntity topicEntity);

    List<TopicDTO> toTopicDTOs(List<TopicEntity> topicEntities);

    @InheritInverseConfiguration
    TopicEntity toTopicEntity(TopicDTO topicDTO);

}
