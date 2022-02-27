package com.drjoy.priticeSpringBoot_MongoDb.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String passWord;
}
