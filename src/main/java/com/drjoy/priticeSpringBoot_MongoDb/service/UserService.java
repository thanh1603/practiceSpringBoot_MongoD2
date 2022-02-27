package com.drjoy.priticeSpringBoot_MongoDb.service;

import com.drjoy.priticeSpringBoot_MongoDb.common.ResponseObject;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.UserDto;
import com.drjoy.priticeSpringBoot_MongoDb.request.RequestFriend;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ResponseObject> createUser(UserDto dto);
    ResponseEntity<ResponseObject> addFriend(RequestFriend requestFriend);

}
