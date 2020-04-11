package com.mse.wcp.forum.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReplyDTO {

    private Long id;
    private String content;
    private Date createdAt;
    private Date modifiedAt;
    private Long topicId;
    private Long userId;

}
