package com.drjoy.priticeSpringBoot_MongoDb.domain.dto;

import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Post;
import com.drjoy.priticeSpringBoot_MongoDb.request.RequestFriend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String name;
    private String email;
    private String password;



}
