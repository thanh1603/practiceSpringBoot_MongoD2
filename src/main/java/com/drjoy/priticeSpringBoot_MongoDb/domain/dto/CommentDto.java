package com.drjoy.priticeSpringBoot_MongoDb.domain.dto;

import com.drjoy.priticeSpringBoot_MongoDb.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private String id;
    private String content;
    private String userId;
    private String postId;
    private Integer like = 0;
    private List<String> listUserId;
}
