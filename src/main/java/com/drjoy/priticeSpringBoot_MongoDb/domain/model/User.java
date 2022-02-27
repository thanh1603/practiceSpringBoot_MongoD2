package com.drjoy.priticeSpringBoot_MongoDb.domain.model;

import com.drjoy.priticeSpringBoot_MongoDb.request.RequestFriend;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String groupId;
    private List<String> listFriendId;
    private List<String> listPostId;
    private String  role;
    private String roleGroup;
}
