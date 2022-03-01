package com.drjoy.priticeSpringBoot_MongoDb.responsitory;

import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Friend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendReponsitory extends MongoRepository<Friend, String> {
}
