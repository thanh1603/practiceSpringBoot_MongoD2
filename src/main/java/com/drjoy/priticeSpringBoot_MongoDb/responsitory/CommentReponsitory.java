package com.drjoy.priticeSpringBoot_MongoDb.responsitory;

import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentReponsitory extends MongoRepository<Comment, String> {
}
