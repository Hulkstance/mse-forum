package com.mse.wcp.forum.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TopicDTO {

    private Long id;
    private String title;
    private Date createdAt;
    private Date modifiedAt;
    private String username;

}
