package com.drjoy.priticeSpringBoot_MongoDb.domain.dto;

import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private String id;
    private String title;
    private Integer like = 0;
    private String createTime;
    private String userId;
    private String content;
    private List<String> listCommentId;
}
