package com.drjoy.priticeSpringBoot_MongoDb.service.Impl;


import com.drjoy.priticeSpringBoot_MongoDb.common.ResponseObject;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.CommentDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.FriendDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Friend;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.FriendReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    FriendReponsitory friendReponsitory;

    @Override
    public ResponseEntity<ResponseObject> createFriend(FriendDto dto) {
        if (dto != null) {
            Friend friend = new Friend();
            if (CollectionUtils.isEmpty(dto.getListFriend())) {
                friend.setListFriendId(dto.getListFriend());
            }

            friendReponsitory.save(friend);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "create post success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "data null", "")
        );
    }
}
