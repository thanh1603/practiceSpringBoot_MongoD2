package com.drjoy.priticeSpringBoot_MongoDb.controller;


import com.drjoy.priticeSpringBoot_MongoDb.common.ResponseObject;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.FriendDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.UserDto;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.FriendReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendController {


    @Autowired
    FriendService friendService;

    @Autowired
    FriendReponsitory friendReponsitory;

    @PostMapping("/createFriend")
    public ResponseEntity<ResponseObject> createFriend(@RequestBody FriendDto dto){
        return this.friendService.createFriend(dto);
    }



}
