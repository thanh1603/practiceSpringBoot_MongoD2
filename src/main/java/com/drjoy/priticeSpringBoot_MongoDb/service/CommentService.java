package com.drjoy.priticeSpringBoot_MongoDb.service;

import com.drjoy.priticeSpringBoot_MongoDb.common.ResponseObject;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.CommentDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.UserDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Comment;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    ResponseEntity<ResponseObject> createComment(CommentDto dto, String idPost);
    List<Comment> findByComment(String content, int page, int size);
    ResponseEntity<ResponseObject> likeComment(String id);
    ResponseEntity<ResponseObject> disLikeComment(String id);
}
