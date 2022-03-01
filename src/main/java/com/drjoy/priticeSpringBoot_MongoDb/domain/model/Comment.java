package com.drjoy.priticeSpringBoot_MongoDb.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "comment")
public class Comment {

    @Id
    private String id;
    private String content;
    private String userId;
    private String postId;
    private Integer like = 0;
    private List<String> listUserId;
}
