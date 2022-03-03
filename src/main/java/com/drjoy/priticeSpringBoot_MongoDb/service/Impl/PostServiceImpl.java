package com.drjoy.priticeSpringBoot_MongoDb.service.Impl;


import com.drjoy.priticeSpringBoot_MongoDb.common.ResponseObject;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.PostDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Comment;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Post;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.User;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.PostReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.UserReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.security.UserDetailsImpl;
import com.drjoy.priticeSpringBoot_MongoDb.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostReponsitory postReponsitory;

    @Autowired
    UserReponsitory userReponsitory;


    @Override
    public ResponseEntity<ResponseObject> createPost(PostDto dto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (dto != null) {
            Optional<User> userCurrent = userReponsitory.findById(userDetails.getId());
            if (userCurrent.isPresent()) {
                User user = userCurrent.get();
                Post newPost = new Post();
                newPost.setTitle(dto.getTitle());
                newPost.setUserId(userDetails.getId());
                newPost.setContent(dto.getContent());
                newPost.setCreateTime(dto.getCreateTime());
                postReponsitory.save(newPost);
                if (CollectionUtils.isEmpty(user.getListPostId())) {
                    List<String> listPost = new ArrayList<>();
                    listPost.add(newPost.getId());
                    user.setListPostId(listPost);
                }else {
                    user.getListPostId().add(newPost.getId());
                }
                userReponsitory.save(user);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "create post success", "")
                );
            }


        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "data null", "")
        );
    }

    @Override
    public List<Post> findByPage(String title,int page , int size) {
        Pageable paging = PageRequest.of(page, size);

        Page<Post> pageTuts;
        if (title == null)
            pageTuts = postReponsitory.findAll(paging);
        else
            pageTuts = postReponsitory.findByTitle(title, paging);
        return pageTuts.getContent();
    }

    @Override
    public ResponseEntity<ResponseObject> likePost(String id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (id != null && id.length() > 0) {
            Optional<Post> post = postReponsitory.findById(id);
            if (post.isPresent()) {
                Post postCurrent = post.get();

                Optional<User> userCurrent = userReponsitory.findById(userDetails.getId());
                Optional<User> checkFriend = userReponsitory.findById(postCurrent.getUserId());

                if (userCurrent.isPresent() && checkFriend.isPresent()) {
                    User userActive = userCurrent.get();
                    User check = checkFriend.get();
                    if (CollectionUtils.isEmpty(userActive.getFriendId())) {
                        return ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseObject("ok", "you do not friend", "")
                        );
                    }else if (userActive.getId().equals(check.getId())){
                        postCurrent.setLike(postCurrent.getLike()+1);
                        postReponsitory.save(postCurrent);
                        return ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseObject("ok", "like my post success", "")
                        );
                    }else {
                        List<String> listFriendActive = userActive.getFriendId();
                        for (String lfa : listFriendActive) {
                            if (lfa.equals(check.getId())) {
                                postCurrent.setLike(postCurrent.getLike()+1);
                                List<String> listIdUserPost = Optional.ofNullable(postCurrent.getListUserId())
                                        .orElse(new ArrayList<>());
                                postCurrent.setListUserId(listIdUserPost);
                                listIdUserPost.add(userActive.getId());

//                                if (CollectionUtils.isEmpty(postCurrent.getListUserId())){
//                                    List<String> listId = new ArrayList<>();
//                                    listId.add(userActive.getId());
//                                    postCurrent.setListUserId(listId);
//                                } else {
//                                    postCurrent.getListUserId().add(userActive.getId());
//                                }
                                postReponsitory.save(postCurrent);
                                return ResponseEntity.status(HttpStatus.OK).body(
                                        new ResponseObject("ok", "like  post success", "")
                                );
                            }

                        }
                        return ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseObject("ok", "guys are not friends", "")
                        );

                    }

                }

            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "not data", "")
        );
    }

    @Override
    public ResponseEntity<ResponseObject> disLikePost(String id) {
        UserDetailsImpl userCurrent = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (id != null && id.length() >0) {
            Optional<Post> post = postReponsitory.findById(id);
            if (post.isPresent()) {
                Post postCurrent = post.get();
                if (CollectionUtils.isEmpty(postCurrent.getListUserId())) {
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "post not like", "")
                    );
                }else {
                    List<String> listIdLikePost = postCurrent.getListUserId();
                    for (String idlike : listIdLikePost) {
                        if (idlike.equals(userCurrent.getId())) {
                            postCurrent.setLike(postCurrent.getLike()-1);
                            listIdLikePost.remove(userCurrent.getId());
                            postReponsitory.save(postCurrent);
                            return ResponseEntity.status(HttpStatus.OK).body(
                                    new ResponseObject("ok", "dislike post success", "")
                            );

                        }
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "dislike post fail", "")
                    );
                }

            }
        }


        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "not data", "")
        );
    }


}
