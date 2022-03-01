package com.drjoy.priticeSpringBoot_MongoDb.controller;


import com.drjoy.priticeSpringBoot_MongoDb.common.ResponseObject;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.CommentDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.UserDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Comment;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Post;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.CommentReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommentController {


    @Autowired
    CommentService commentService;

    @Autowired
    CommentReponsitory commentReponsitory;


    @PostMapping("/createComment/{id}")
    public ResponseEntity<ResponseObject> createUser(@RequestBody CommentDto dto, @PathVariable String id){

        return this.commentService.createComment(dto, id);
    }

    @PostMapping("/likeComment")
    public ResponseEntity<ResponseObject> likePost(@RequestParam(defaultValue = "") String id){

        return this.commentService.likeComment(id);
    }

    @PostMapping("/dislikeComment")
    public ResponseEntity<ResponseObject> dislikePost(@RequestParam(defaultValue = "") String id){

        return this.commentService.disLikeComment(id);
    }

    @GetMapping("/pageComment")
    public ResponseEntity<Map<String, Object>> getAllPageComment(
            @RequestParam(required = false) String content,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {

        try {
            List<Comment> comments = this.commentService.findByComment(content, page, size);
            Map<String, Object> response = new HashMap<>();
            response.put("comments", comments);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






}
