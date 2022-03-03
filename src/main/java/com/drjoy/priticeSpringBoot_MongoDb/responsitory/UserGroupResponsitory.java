package com.drjoy.priticeSpringBoot_MongoDb.responsitory;

import com.drjoy.priticeSpringBoot_MongoDb.domain.model.UserGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserGroupResponsitory extends MongoRepository<UserGroup, String> {
}
