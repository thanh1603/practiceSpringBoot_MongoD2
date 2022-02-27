package com.drjoy.priticeSpringBoot_MongoDb.domain.dto;

import com.drjoy.priticeSpringBoot_MongoDb.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {

    private  String id;
    private String name;
    private List<String> listUserId;
}
