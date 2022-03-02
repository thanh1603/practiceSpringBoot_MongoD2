package com.drjoy.priticeSpringBoot_MongoDb.service.Impl;

import com.drjoy.priticeSpringBoot_MongoDb.common.Constant;
import com.drjoy.priticeSpringBoot_MongoDb.common.ResponseObject;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.UserDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Friend;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.User;
import com.drjoy.priticeSpringBoot_MongoDb.request.RequestFriend;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.FriendReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.UserReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.security.UserDetailsImpl;
import com.drjoy.priticeSpringBoot_MongoDb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    FriendReponsitory friendReponsitory;

    @Autowired
    UserReponsitory userReponsitory;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public ResponseEntity<ResponseObject> createUser(UserDto dto) {
        if (dto != null) {
            Optional<User> findUsers = userReponsitory.findByEmail(dto.getEmail());
            if (findUsers.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("failed", "Email already taken", "")
                );
            } else {
                User user = new User();
                Friend friend = new Friend();
                friendReponsitory.save(friend);
//                user.setId(dto.getId());
                user.setName(dto.getName());
                user.setEmail(dto.getEmail());
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
//                user.setGroupId(dto.getGroupId());
//                user.setFriendId(friend.getId());
//                user.getListFriendId().add(dto.getId());
//                user.setListPostId(dto.getListPostId());
                //user.setRoleGroup(dto.getRoleGroup());

               // user.setRole(Constant.ROLE.user);

                userReponsitory.save(user);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Insert success", user.getEmail())
                );

            }

        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "data null", "")
        );
    }

    @Override
    public ResponseEntity<ResponseObject> addFriend(RequestFriend requestFriend) {
        UserDetailsImpl currentLoginUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (requestFriend.getId() != null) {

            Optional<User> targetUserOp = userReponsitory.findById(requestFriend.getId());
            if (targetUserOp.isPresent()) {
                Optional<User> currentUserOp = userReponsitory.findById(currentLoginUser.getId());
                User targetUser = targetUserOp.get();
                List<User> listSave = new ArrayList();
                if (currentUserOp.isPresent()){
                    User currentUser = currentUserOp.get();
                    // Check
                    List<String> currentFriendIds = Optional.ofNullable(currentUser.getFriendId())
                            .orElse(new ArrayList<>());
                    List<String> targetFriendIds = Optional.ofNullable(targetUser.getFriendId())
                            .orElse(new ArrayList<>());
                    if (currentFriendIds.size() >= 100 || targetFriendIds.size() >= 100) {
                        return ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseObject("ok", "you full friend ", "")
                        );
                    }
                    currentFriendIds.add(targetUser.getId());
                    currentUser.setFriendId(currentFriendIds);
                    listSave.add(currentUser);

                    targetFriendIds.add(currentUser.getId());
                    targetUser.setFriendId(targetFriendIds);
                    listSave.add(targetUser);

//                    if (CollectionUtils.isEmpty(currentUser.getFriendId())) {
//                        List<String> listFriendUserId = new ArrayList<>();
//                        listFriendUserId.add(targetUser.getId());
//                        currentUser.setFriendId(listFriendUserId);
//                    }else {
//                        if (currentUser.getGroupId().size()< 100) {
//                            currentUser.getFriendId().add(targetUser.getId());
//                        }else {
//                            return ResponseEntity.status(HttpStatus.OK).body(
//                                    new ResponseObject("ok", "you full friend ", "")
//                            );
//                        }
//
//
//                    }

//                    if (CollectionUtils.isEmpty(targetUser.getFriendId())) {
//                        List<String> listFriendUserId = new ArrayList<>();
//                        listFriendUserId.add(currentUser.getId());
//                        targetUser.setFriendId(listFriendUserId);
//                    }else {
//                        if (targetUser.getFriendId().size()<100) {
//                            targetUser.getFriendId().add(currentUser.getId());
//                        }else {
//                            return ResponseEntity.status(HttpStatus.OK).body(
//                                    new ResponseObject("ok", "your friend full friend ", "")
//                            );
//                        }
//
//                    }

                }

                userReponsitory.saveAll(listSave);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "add friend success", "")
                );

            }

        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "data null", "")
        );
    }

    @Override
    public ResponseEntity<ResponseObject> removeFriend(RequestFriend requestFriend) {
        UserDetailsImpl currentLoginUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (requestFriend.getId() != null) {
            Optional<User> targetUserOp = userReponsitory.findById(requestFriend.getId());
            if (targetUserOp.isPresent()) {
                List<User> listSave = new ArrayList();
                Optional<User> currentUserOp = userReponsitory.findById(currentLoginUser.getId());
                User targetUser = targetUserOp.get();
                if (currentUserOp.isPresent()){
                    User currentUser = currentUserOp.get();
                    List<String> listFriendIdTarget = targetUser.getFriendId();
                    List<String> listFriendCurrent = currentUser.getFriendId();
//                    listFriendIdTarget.removeIf(currentUser.getId())
                    List<String>  listTargetUser=  listFriendIdTarget.stream().filter(idFriend -> !idFriend.equals(currentUser.getId())).collect(Collectors.toList());
                    listSave.add(targetUser);
                    List<String>  list=  listFriendCurrent.stream().filter(idFriend -> !idFriend.equals(targetUser.getId())).collect(Collectors.toList());
                    listSave.add(currentUser);

//                    for (String lft: listFriendIdTarget) {
//                        if (lft.equals(currentUser.getId())){
//                            listFriendIdTarget.remove(currentUser.getId());
//                            listSave.add(targetUser);
//                        }
//
//                    }

//                    for (String lfc : listFriendCurrent) {
//                        if (lfc.equals(targetUser.getId())){
//                            listFriendCurrent.remove(targetUser.getId());
//                            listSave.add(currentUser);
//                        }
//
//                    }

                }
//                if (CollectionUtils.isEmpty(listSave)){
//
//                }

                userReponsitory.saveAll(listSave);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "remove friend success", "")
                );

            }

        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "data null", "")
        );
    }

    @Override
    public List<User> findUserName(String name) {
        if (name != null && name.length()==0) {
            return userReponsitory.findAll();
        } else {
            Query query = new Query();
            assert name != null;
            Criteria criteria = Criteria.where(Constant.USER.NAME).regex(name, "$i");
//            Criteria criteria = Criteria.where(Constant.USER.NAME).is(name);
            query.addCriteria(criteria);
            return mongoTemplate.find(query, User.class);
        }


    }

//    private Criteria getCriteriaListSchedulePatternByCondition(String keyword) {
//        Criteria criteria = new Criteria();
//        List<Criteria> criteriaList = new ArrayList<>();
//
//        if (StringUtils.isNotBlank(keyword)) {
//            Criteria searchKeyWord = new Criteria();
//            String regexKeyWord = MongoRegexCreator.INSTANCE
//                    .toRegularExpression(keyword, MongoRegexCreator.MatchMode.CONTAINING);
//            assert regexKeyWord != null;
//            searchKeyWord.orOperator(where(PATTERN_NAME).regex(regexKeyWord, "i"));
//            criteriaList.add(searchKeyWord);
//        }
//
//        if (CollectionUtils.isEmpty(criteriaList)) {
//            return criteria;
//        }
//
//        return criteria.andOperator(criteriaList.toArray(new Criteria[0]));
//    }


    private Criteria getCriteriaListSchedulePatternByCondition(String keyword) {
        Criteria criteria = new Criteria();
        List<Criteria> criteriaList = new ArrayList<>();

        if (StringUtils.isNotBlank(keyword)) {
            Criteria searchKeyWord = new Criteria();
            String regexKeyWord = MongoRegexCreator.INSTANCE
                    .toRegularExpression(keyword, MongoRegexCreator.MatchMode.CONTAINING);
            assert regexKeyWord != null;
            searchKeyWord.orOperator(where(PATTERN_NAME).regex(regexKeyWord, "i"));
            criteriaList.add(searchKeyWord);
        }

        if (CollectionUtils.isEmpty(criteriaList)) {
            return criteria;
        }

        return criteria.andOperator(criteriaList.toArray(new Criteria[0]));
    }



}
