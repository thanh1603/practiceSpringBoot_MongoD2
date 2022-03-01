package com.drjoy.priticeSpringBoot_MongoDb.responsitory;

import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupReponsitory extends MongoRepository<Group, String> {
}
