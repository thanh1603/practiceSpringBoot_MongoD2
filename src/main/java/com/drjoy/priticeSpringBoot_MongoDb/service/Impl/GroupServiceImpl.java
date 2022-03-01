package com.drjoy.priticeSpringBoot_MongoDb.service.Impl;

import com.drjoy.priticeSpringBoot_MongoDb.common.Constant;
import com.drjoy.priticeSpringBoot_MongoDb.common.ResponseObject;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.GroupDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Group;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.User;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.GroupReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.UserReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.security.UserDetailsImpl;
import com.drjoy.priticeSpringBoot_MongoDb.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupReponsitory groupReponsitory;

    @Autowired
    UserReponsitory userReponsitory;


    @Override
    public ResponseEntity<ResponseObject> createGroup(GroupDto dto) {
        UserDetailsImpl currentLoginUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (dto!= null) {
            Optional<User> currentUserOp = userReponsitory.findById(currentLoginUser.getId());
            if (currentUserOp.isPresent()) {
                User currentUser = currentUserOp.get();
                if (CollectionUtils.isEmpty(currentUser.getGroupId())) {
                    Group newGroup = new Group();
                    newGroup.setName(dto.getName());
                    groupReponsitory.save(newGroup);
                    List<String> list = new ArrayList<>();
                    list.add(newGroup.getId());
                    currentUser.setGroupId(list);
                    currentUser.setRoleGroup(Constant.ROLE_GROUP.admin);
                    userReponsitory.save(currentUser);
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "create new group success", "")
                    );

                }else {
                    Group newGroup = new Group();
                    newGroup.setName(dto.getName());
                    groupReponsitory.save(newGroup);
                    currentUser.getGroupId().add(newGroup.getId());
                    currentUser.setRoleGroup(Constant.ROLE_GROUP.admin);
                    userReponsitory.save(currentUser);
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "create new group success", "")
                    );
                }
            }

        }


        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "data null", "")
        );
    }

    @Override
    public ResponseEntity<ResponseObject> addUserGroup(String idGroup, String idUser) {
        UserDetailsImpl currentLoginUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (idGroup != null&&idGroup.length()>0 && idUser !=null &&idUser.length()>0) {
            Optional<User> currentUserOp = userReponsitory.findById(currentLoginUser.getId());
            Optional<User> userAddOp = userReponsitory.findById(idUser);
            Optional<Group> groupAddOP = groupReponsitory.findById(idGroup);
            if (userAddOp.isPresent() && groupAddOP.isPresent() && currentUserOp.isPresent()) {
                User currentUser = currentUserOp.get();
                User userAdd = userAddOp.get();
                Group groupAdd = groupAddOP.get();
                if (CollectionUtils.isEmpty(currentUser.getGroupId())){
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "you don't group", "")
                    );
                }else {
                    List<String> listIdGroupCurrent = currentUser.getGroupId();
                    List<User> listSaveUser = new ArrayList<>();
                    List<Group> listSaveGroup = new ArrayList<>();
                    for (String listId: listIdGroupCurrent) {
                        if (listId.equals(groupAdd.getId()) && currentUser.getRoleGroup().equals(Constant.ROLE_GROUP.admin)){

                            currentUser.getGroupId().add(userAdd.getId());
                            listSaveUser.add(currentUser);
                            if (CollectionUtils.isEmpty(userAdd.getGroupId())) {
                                List<String> lisGroupUserAdd = new ArrayList<>();
                                lisGroupUserAdd.add(currentUser.getId());
                                userAdd.setGroupId(lisGroupUserAdd);

                            }else {
                                userAdd.getGroupId().add(currentUser.getId());

                            }
                            listSaveUser.add(userAdd);


                            if (CollectionUtils.isEmpty(groupAdd.getListUserId())){
                                List<String> listIdUserGroup = new ArrayList<>();
                                listIdUserGroup.add(userAdd.getId());
                                groupAdd.setListUserId(listIdUserGroup);

                            }else {

                                groupAdd.getListUserId().add(userAdd.getId());

                            }
                            listSaveGroup.add(groupAdd);

                            userReponsitory.saveAll(listSaveUser);
                            groupReponsitory.saveAll(listSaveGroup);
                            return ResponseEntity.status(HttpStatus.OK).body(
                                    new ResponseObject("ok", "add user for group success", "")
                            );

                        }
                    }

                }


            }

        }



        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "do not data", "")
        );
    }


}
