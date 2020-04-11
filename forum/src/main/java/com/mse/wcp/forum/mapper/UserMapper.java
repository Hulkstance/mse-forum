package com.mse.wcp.forum.mapper;

import com.mse.wcp.forum.dto.UserDTO;
import com.mse.wcp.forum.persistence.entity.UserEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(UserEntity userEntity);

    List<UserDTO> toUserDTOs(List<UserEntity> userEntities);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "modifiedAt", ignore = true),
            @Mapping(target = "topics", ignore = true)
    })
    UserEntity toUserEntity(UserDTO userDTO);

}
