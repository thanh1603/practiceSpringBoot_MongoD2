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
    private String title;
    private String content;

}
