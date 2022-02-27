package com.drjoy.priticeSpringBoot_MongoDb.controller;

import com.drjoy.priticeSpringBoot_MongoDb.common.ResponseObject;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.UserDto;
import com.drjoy.priticeSpringBoot_MongoDb.request.RequestFriend;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.UserReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserReponsitory userReponsitory;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createUser(@RequestBody UserDto dto){

        return this.userService.createUser(dto);
    }

    @PostMapping("/addFriend")
    public ResponseEntity<ResponseObject> addFriend(@RequestBody RequestFriend requestFriend){
        return this.userService.addFriend(requestFriend);
    }







}
