package com.drjoy.priticeSpringBoot_MongoDb.controller;

import com.drjoy.priticeSpringBoot_MongoDb.common.ResponseObject;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.UserDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.User;
import com.drjoy.priticeSpringBoot_MongoDb.request.RequestFriend;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.UserReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/removeFriend")
    public ResponseEntity<ResponseObject> removeFriend(@RequestBody RequestFriend requestFriend){
        return this.userService.removeFriend(requestFriend);
    }


    @GetMapping("/findByName")
    public List<User> findUserName(@RequestParam(defaultValue = "") String name){
        return this.userService.findUserName(name);
    }






}
