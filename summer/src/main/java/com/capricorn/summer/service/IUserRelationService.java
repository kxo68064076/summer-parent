package com.capricorn.summer.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface IUserRelationService {

    PageInfo<Map<String, String>> queryUserList(int pageNum,int pageSize,String userName);

    Map<String, String> addUser(Map<String,String> map);

    Map<String, String> editUser(Map<String, String> map);

    Map<String, String> delUser(String userId);
}
