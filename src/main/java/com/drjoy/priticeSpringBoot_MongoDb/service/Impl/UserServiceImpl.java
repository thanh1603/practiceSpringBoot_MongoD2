package com.drjoy.priticeSpringBoot_MongoDb.service.Impl;

import com.drjoy.priticeSpringBoot_MongoDb.common.Constant;
import com.drjoy.priticeSpringBoot_MongoDb.common.ResponseObject;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.UserDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.User;
import com.drjoy.priticeSpringBoot_MongoDb.request.RequestFriend;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.UserReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.security.UserDetailsImpl;
import com.drjoy.priticeSpringBoot_MongoDb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserReponsitory userReponsitory;

    @Autowired
    PasswordEncoder passwordEncoder;

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
//                user.setId(dto.getId());
                user.setName(dto.getName());
                user.setEmail(dto.getEmail());
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
                user.setGroupId(dto.getGroupId());
//                user.getListFriendId().add(dto.getId());
//                user.setListPostId(dto.getListPostId());
                user.setRoleGroup(dto.getRoleGroup());

                user.setRole(Constant.ROLE.user);

                userReponsitory.save(user);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Insert success", user.getEmail())
                );

            }

        }
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> addFriend(RequestFriend requestFriend) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (requestFriend.getId() != null) {

            Optional<User> findFriendById = userReponsitory.findById(requestFriend.getId());
            if (findFriendById.isPresent()) {
                Optional<User> userActive = userReponsitory.findById(userDetails.getId());
                if (userActive.isPresent()) {
                    User user = userActive.get();


                    if (user.getListFriendId().isEmpty()) {
                        List<String> list = new ArrayList<>();
                        list.add(requestFriend.getId());
                        user.setListFriendId(list);
//                        user.getListPostId().addAll(list);
                    }else {
                        List<String> listFriend;
                        listFriend = user.getListFriendId();
//                        listFriend.stream().filter(lf->lf.equalsIgnoreCase(requestFriend.getId())).re
                        for (String lf: listFriend) {
                            if (lf.equals(requestFriend.getId())){
                                listFriend.remove(requestFriend.getId());
                                userReponsitory.save(user);
                                return ResponseEntity.status(HttpStatus.OK).body(
                                        new ResponseObject("ok", "remove friend success", "")
                                );
                            }

                        }

                        user.getListFriendId().add(requestFriend.getId());
                        userReponsitory.save(user);
                        return ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseObject("ok", "add friend success", "")
                        );

                    }

                }
            }


        }

        return null;
    }


}
