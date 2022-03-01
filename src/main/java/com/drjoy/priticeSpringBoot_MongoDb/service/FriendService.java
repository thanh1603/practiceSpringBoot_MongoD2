package com.drjoy.priticeSpringBoot_MongoDb.service;

import com.drjoy.priticeSpringBoot_MongoDb.common.ResponseObject;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.FriendDto;
import org.springframework.http.ResponseEntity;

public interface FriendService {
    ResponseEntity<ResponseObject> createFriend(FriendDto dto);

}
