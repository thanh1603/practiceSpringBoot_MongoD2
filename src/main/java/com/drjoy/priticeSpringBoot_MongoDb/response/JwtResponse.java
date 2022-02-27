package com.drjoy.priticeSpringBoot_MongoDb.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtResponse {
    private String id;
    private String token;
    private String userName;

    public JwtResponse(String id, String token, String userName) {
        this.id = id;
        this.token = token;
        this.userName = userName;
    }
}
