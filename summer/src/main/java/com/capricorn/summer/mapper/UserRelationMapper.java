package com.capricorn.summer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserRelationMapper {
    List<Map<String,String>> queryUserList(@Param("userName") String userName);

    int addUser(Map<String, String> map);

    int getUserId();

    int editUser(Map<String, String> map);

    Integer delUser(String userId);
}
