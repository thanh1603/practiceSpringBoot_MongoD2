package com.drjoy.priticeSpringBoot_MongoDb.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "friend")
public class Friend {
    @Id
    private String id;
    private List<String> listFriend;
}
