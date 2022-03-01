package com.drjoy.priticeSpringBoot_MongoDb.responsitory;

import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Comment;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReponsitory extends MongoRepository<Comment, String> {
    Page<Comment> findByContent(String content, Pageable pageable);

}
