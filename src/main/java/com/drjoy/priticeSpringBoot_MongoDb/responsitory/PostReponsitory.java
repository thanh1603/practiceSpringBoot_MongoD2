package com.drjoy.priticeSpringBoot_MongoDb.responsitory;

import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostReponsitory extends MongoRepository<Post, String > {

}
