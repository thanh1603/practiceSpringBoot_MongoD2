package com.drjoy.priticeSpringBoot_MongoDb.service;

import com.drjoy.priticeSpringBoot_MongoDb.common.ResponseObject;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.GroupDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface GroupService {
    ResponseEntity<ResponseObject> createGroup(GroupDto dto);
    ResponseEntity<ResponseObject> addUserGroup(String idGroup, String idUser);
    ResponseEntity<ResponseObject> accessPermissions(String idGroup, String idUser);

}
