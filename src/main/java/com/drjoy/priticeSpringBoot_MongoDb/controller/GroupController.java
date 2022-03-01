package com.drjoy.priticeSpringBoot_MongoDb.controller;

import com.drjoy.priticeSpringBoot_MongoDb.common.ResponseObject;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.CommentDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.GroupDto;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.GroupReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GroupController {

    @Autowired
    GroupService groupService;

    @Autowired
    GroupReponsitory groupReponsitory;

    @PostMapping("/createGroup")
    public ResponseEntity<ResponseObject> createGroup(@RequestBody GroupDto dto){

        return this.groupService.createGroup(dto);
    }


    @PostMapping("/addUserGroup")
    public ResponseEntity<ResponseObject> addUserGroup(@RequestParam(defaultValue = "") String idGroup,
                                                       @RequestParam(defaultValue = "") String idUser){

        return this.groupService.addUserGroup(idGroup, idUser);
    }




}
