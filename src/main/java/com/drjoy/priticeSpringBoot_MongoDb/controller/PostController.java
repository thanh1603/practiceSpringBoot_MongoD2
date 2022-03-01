package com.drjoy.priticeSpringBoot_MongoDb.controller;

import com.drjoy.priticeSpringBoot_MongoDb.common.ResponseObject;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.PostDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.UserDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Post;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.PostReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    PostReponsitory postReponsitory;

    @PostMapping("/createPost")
    public ResponseEntity<ResponseObject> createPost(@RequestBody PostDto dto){
        return this.postService.createPost(dto);
    }

    @PostMapping("/likePost")
    public ResponseEntity<ResponseObject> likePost(@RequestParam(defaultValue = "") String id){

        return this.postService.likePost(id);
    }

    @PostMapping("/dislikePost")
    public ResponseEntity<ResponseObject> dislikePost(@RequestParam(defaultValue = "") String id){

        return this.postService.disLikePost(id);
    }


    @GetMapping("/pagePost")
    public ResponseEntity<Map<String, Object>> getAllPostPage(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {

        try {
            List<Post> posts = this.postService.findByPage(title, page, size);
            Map<String, Object> response = new HashMap<>();
            response.put("posts", posts);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    //    @GetMapping("/page")
//    public ResponseEntity<Map<String, Object>> getAllTutorialsPage(
//            @RequestParam(required = false) String title,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "3") int size) {
//        try {
//            List<Post> posts = new ArrayList<Post>();
//            Pageable paging = PageRequest.of(page, size);
//
//            Page<Post> pageTuts;
//            if (title == null)
//                pageTuts = postReponsitory.findAll(paging);
//            else
//                pageTuts = postReponsitory.findByTitleContainingIgnoreCase(title, paging);
//            posts = pageTuts.getContent();
//            Map<String, Object> response = new HashMap<>();
//            response.put("posts", posts);
//            response.put("currentPage", pageTuts.getNumber());
//            response.put("totalItems", pageTuts.getTotalElements());
//            response.put("totalPages", pageTuts.getTotalPages());
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }






}
