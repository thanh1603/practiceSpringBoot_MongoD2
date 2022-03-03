package com.drjoy.priticeSpringBoot_MongoDb.service.Impl;

import com.drjoy.priticeSpringBoot_MongoDb.common.Constant;
import com.drjoy.priticeSpringBoot_MongoDb.common.ResponseObject;
import com.drjoy.priticeSpringBoot_MongoDb.domain.dto.GroupDto;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.Group;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.User;
import com.drjoy.priticeSpringBoot_MongoDb.domain.model.UserGroup;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.GroupReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.UserGroupResponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.responsitory.UserReponsitory;
import com.drjoy.priticeSpringBoot_MongoDb.security.UserDetailsImpl;
import com.drjoy.priticeSpringBoot_MongoDb.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupReponsitory groupReponsitory;

    @Autowired
    private UserReponsitory userReponsitory;

    @Autowired
    private UserGroupResponsitory userGroupResponsitory;



    @Override
    public ResponseEntity<ResponseObject> createGroup(GroupDto dto) {
        UserDetailsImpl currentLoginUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (dto!= null) {
            Optional<User> currentUserOp = userReponsitory.findById(currentLoginUser.getId());
            if (currentUserOp.isPresent()) {
                User currentUser = currentUserOp.get();
                List<String> listGroupId = Optional.ofNullable(currentUser.getGroupId())
                        .orElse(new ArrayList<>());
                Group newGroup = new Group();
                newGroup.setName(dto.getName());
                groupReponsitory.save(newGroup);

                listGroupId.add(newGroup.getId());
                currentUser.setRoleGroup(Constant.ROLE_GROUP.admin);
                currentUser.setGroupId(listGroupId);
                userReponsitory.save(currentUser);

                UserGroup userGroup = new UserGroup();
                userGroup.setCreator(currentUser.getId());
                userGroup.setGroupId(newGroup.getId());
                userGroup.setIdMember(new ArrayList<>());
                userGroupResponsitory.save(userGroup);


                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "create new group success", "")
                );

//                if (CollectionUtils.isEmpty(currentUser.getGroupId())) {
//                    Group newGroup = new Group();
//                    newGroup.setName(dto.getName());
//                    groupReponsitory.save(newGroup);
//                    List<String> list = new ArrayList<>();
//                    list.add(newGroup.getId());
//                    currentUser.setGroupId(list);
//                    currentUser.setRoleGroup(Constant.ROLE_GROUP.admin);
//                    userReponsitory.save(currentUser);
//                    return ResponseEntity.status(HttpStatus.OK).body(
//                            new ResponseObject("ok", "create new group success", "")
//                    );
//
//                }else {
//                    Group newGroup = new Group();
//                    newGroup.setName(dto.getName());
//                    groupReponsitory.save(newGroup);
//                    currentUser.getGroupId().add(newGroup.getId());
//                    currentUser.setRoleGroup(Constant.ROLE_GROUP.admin);
//                    userReponsitory.save(currentUser);
//                    return ResponseEntity.status(HttpStatus.OK).body(
//                            new ResponseObject("ok", "create new group success", "")
//                    );
//                }
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
            Optional<UserGroup> creatorGroupOp = userGroupResponsitory.findById(currentLoginUser.getId());

            if (userAddOp.isPresent() && groupAddOP.isPresent() && currentUserOp.isPresent() && creatorGroupOp.isPresent()) {
                User currentUser = currentUserOp.get();
                User userAdd = userAddOp.get();
                Group groupAdd = groupAddOP.get();
                UserGroup creatorGroup =creatorGroupOp.get();

                if (CollectionUtils.isEmpty(currentUser.getGroupId())){
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "you don't group", "")
                    );
                }else {
                    List<String> listIdGroupCurrent = currentUser.getGroupId();

                    for (String idOfGroup: listIdGroupCurrent) {
                        if (idOfGroup.equals(groupAdd.getId())) {
                            List<String> listMemberId = Optional.ofNullable(creatorGroup.getIdMember())
                                    .orElse(new ArrayList<>());
                                for (String idMember: listMemberId) {
                                    if (idMember.equals(userAdd.getId()) || currentUser.getId().equals(creatorGroup.getCreator())) {
                                        List<String> addUserForGroup = Optional.ofNullable(groupAdd.getListUserId())
                                                .orElse(new ArrayList<>());
                                        addUserForGroup.add(userAdd.getId());
                                        groupAdd.setListUserId(addUserForGroup);

                                        groupReponsitory.save(groupAdd);
                                        return ResponseEntity.status(HttpStatus.OK).body(
                                                new ResponseObject("ok", "add user for group success", groupAdd.getName())
                                        );
                                    }
                                }

                        }


                    }
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "you do not have access ", "")
                    );

                }


            }

        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "do not data", "")
        );
    }

    @Override
    public ResponseEntity<ResponseObject> accessPermissions(String idGroup, String idUser) {
        UserDetailsImpl currentLoginUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (idGroup != null&&idGroup.length()>0 && idUser !=null &&idUser.length()>0) {
            Optional<User> currentUserOp = userReponsitory.findById(currentLoginUser.getId());
            Optional<User> userAddOp = userReponsitory.findById(idUser);
            Optional<Group> groupAddOP = groupReponsitory.findById(idGroup);
            Optional<UserGroup> creatorGroupOp = userGroupResponsitory.findById(currentLoginUser.getId());

            if (userAddOp.isPresent() && groupAddOP.isPresent() && currentUserOp.isPresent() && creatorGroupOp.isPresent()) {
                User currentUser = currentUserOp.get();
                User userAdd = userAddOp.get();
                Group groupAdd = groupAddOP.get();
                UserGroup creatorGroup = creatorGroupOp.get();

                if (CollectionUtils.isEmpty(currentUser.getGroupId())) {
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "you don't group", "")
                    );
                }else {
                    List<String> listIdGroupCurrent = currentUser.getGroupId();
                    for (String idOfGroup: listIdGroupCurrent) {
                        if (idOfGroup.equals(groupAdd.getId())&& currentUser.getId().equals(creatorGroup.getCreator())
                                && currentUser.getRoleGroup().equals(Constant.ROLE_GROUP.admin)){


                            List<String> addMemBer = Optional.ofNullable(creatorGroup.getIdMember())
                                    .orElse(new ArrayList<>());
                            addMemBer.add(userAdd.getId());
                            creatorGroup.setIdMember(addMemBer);

                            userGroupResponsitory.save(creatorGroup);
                            return ResponseEntity.status(HttpStatus.OK).body(
                                    new ResponseObject("ok", "add permissions for user success", groupAdd.getName())
                            );

                        }
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "you do not have access ", "")
                    );
                }

            }

        }


        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "do not data", "")
        );
    }


}
