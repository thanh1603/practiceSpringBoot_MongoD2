package com.drjoy.priticeSpringBoot_MongoDb.domain.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//import java.util.List;
import java.util.List;
import java.util.Set;

@Data
@Document(collection = "user")
public class UserGroup {
    @Id
    private String id;
    private String creator;
    private String groupId;
    private List<String> idMember;


}
