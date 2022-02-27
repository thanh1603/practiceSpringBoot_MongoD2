package com.drjoy.priticeSpringBoot_MongoDb.domain.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "post")
public class Post {
    @Id
    private String id;
    private String title;
    private Integer like = 0;
    private String createTime;
    private String userId;
    private String content;
    private List<String> listCommentId;

}
