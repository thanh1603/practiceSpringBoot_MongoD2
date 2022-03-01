package com.drjoy.priticeSpringBoot_MongoDb.service;

import com.drjoy.priticeSpringBoot_MongoDb.common.ResponseObject;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.PostDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.UserDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {
    ResponseEntity<ResponseObject> createPost(PostDto dto);
    List<Post> findByPage(String title, int page, int size);
    ResponseEntity<ResponseObject> likePost(String id);
    ResponseEntity<ResponseObject> disLikePost(String id);

}
