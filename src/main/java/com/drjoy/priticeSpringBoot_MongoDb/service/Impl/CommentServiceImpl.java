package com.drjoy.priticeSpringBoot_MongoDb.service.Impl;

import com.drjoy.priticeSpringBoot_MongoDb.common.ResponseObject;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.CommentDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Comment;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Post;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.User;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.CommentReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.PostReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.UserReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.security.UserDetailsImpl;
import com.drjoy.priticeSpringBoot_MongoDb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentReponsitory commentReponsitory;

    @Autowired
    PostReponsitory postReponsitory;

    @Autowired
    UserReponsitory userReponsitory;


    @Override
    public ResponseEntity<ResponseObject> createComment(CommentDto dto, String idPost) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (dto != null && idPost != null) {
            Optional<Post> post = postReponsitory.findById(idPost);
            if (post.isPresent()) {
                Post postComment = post.get();
                Optional<User> active = userReponsitory.findById(userDetails.getId());
                Optional<User> checkFriend = userReponsitory.findById(postComment.getUserId());
                if (active.isPresent() && checkFriend.isPresent()) {
                    User userActive = active.get();
                    User check = checkFriend.get();
                    if (CollectionUtils.isEmpty(userActive.getFriendId())) {
                        return ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseObject("ok", "you do not friend", "")
                        );
                    }else if (userActive.getId().equals(check.getId())){
                        Comment comment = new Comment();
                        comment.setContent(dto.getContent());
                        comment.setUserId(userDetails.getId());
                        comment.setPostId(postComment.getId());
                        commentReponsitory.save(comment);
                        if (CollectionUtils.isEmpty(postComment.getListCommentId()) ) {
                            List<String> list = new ArrayList<>();
                            list.add(comment.getId());
                            postComment.setListCommentId(list);
                        } else {
                            postComment.getListCommentId().add(userDetails.getId());
                        }
                        postReponsitory.save(postComment);
                        return ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseObject("ok", "comment my post success", "")
                        );
                    }else {
                        List<String> listFriendActive = userActive.getFriendId();
                        for (String lfa : listFriendActive) {
                            if (lfa.equals(check.getId())) {
                                Comment comment = new Comment();
                                comment.setContent(dto.getContent());
                                comment.setUserId(userDetails.getId());
                                comment.setPostId(postComment.getId());
                                commentReponsitory.save(comment);
                                if (CollectionUtils.isEmpty(postComment.getListCommentId())) {
                                    List<String> list = new ArrayList<>();
                                    list.add(comment.getId());
                                    postComment.setListCommentId(list);
                                } else {
                                    postComment.getListCommentId().add(comment.getId());
                                }
                                postReponsitory.save(postComment);
                                return ResponseEntity.status(HttpStatus.OK).body(
                                        new ResponseObject("ok", "comment success", "")
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
                new ResponseObject("ok", "data null", "")
        );
    }

    @Override
    public List<Comment> findByComment(String content, int page, int size) {
        List<Comment> Comments = new ArrayList<Comment>();
        Pageable paging = PageRequest.of(page, size);

        Page<Comment> pageTuts;
        if (content == null)
            pageTuts = commentReponsitory.findAll(paging);
        else
            pageTuts = commentReponsitory.findByContent(content, paging);
        Comments = pageTuts.getContent();
        return Comments;
    }

    @Override
    public ResponseEntity<ResponseObject> likeComment(String id) {
        UserDetailsImpl userCurrent = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (id != null && id.length()>0) {
            Optional<Comment> comment = commentReponsitory.findById(id);
            if (comment.isPresent()) {
                Comment commentCurrent = comment.get();
                commentCurrent.setLike(commentCurrent.getLike()+1);
                if (CollectionUtils.isEmpty(commentCurrent.getListUserId())){
                    List<String> listId = new ArrayList<>();
                    listId.add(userCurrent.getId());
                    commentCurrent.setListUserId(listId);
                }else {
                    commentCurrent.getListUserId().add(userCurrent.getId());
                }

                commentReponsitory.save(commentCurrent);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "like post success", "")
                );
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "not data", "")
        );
    }

    @Override
    public ResponseEntity<ResponseObject> disLikeComment(String id) {
        UserDetailsImpl userCurrent = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (id != null) {
            Optional<Comment> comment = commentReponsitory.findById(id);
            if (comment.isPresent()) {
                Comment commentCurrent = comment.get();
                if (CollectionUtils.isEmpty(commentCurrent.getListUserId())) {
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "post not like", "")
                    );
                }else {
                    List<String> listIdDisLikePost = commentCurrent.getListUserId();
                    for (String idDis: listIdDisLikePost) {
                        if (idDis.equals(userCurrent.getId())) {
                            commentCurrent.setLike(commentCurrent.getLike()-1);
                            listIdDisLikePost.remove(userCurrent.getId());
                            commentReponsitory.save(commentCurrent);
                            return ResponseEntity.status(HttpStatus.OK).body(
                                    new ResponseObject("ok", "dislike post success", "")
                            );

                        }
                    }
                }

            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "data null", "")
        );
    }
}
