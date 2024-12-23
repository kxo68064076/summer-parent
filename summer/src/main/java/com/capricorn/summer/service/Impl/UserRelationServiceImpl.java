package com.capricorn.summer.service.Impl;

import com.capricorn.summer.mapper.UserRelationMapper;
import com.capricorn.summer.service.IUserRelationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRelationServiceImpl implements IUserRelationService {

    @Autowired
    UserRelationMapper userRelationMapper;

    @Override
    public PageInfo<Map<String, String>> queryUserList(int pageNum,int pageSize,String userName) {
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String, String>> userList = userRelationMapper.queryUserList(userName);
        return new PageInfo<>(userList);
    }

    @Override
    public Map<String, String> addUser(Map<String,String> map) {
        Map<String, String> resultMap = new HashMap<>();
        int seq = userRelationMapper.getUserId();
        String userId = "s"+String.format("%05d", seq);
        map.put("USER_ID",userId);
        map.put("DATA_UPDATE_USER", map.get("DATA_CREATE_USER"));
        try {
            int result = userRelationMapper.addUser(map);
            resultMap.put("flag",result==0?"fail":"success");
        }catch (Exception e){
            resultMap.put("msg",e.getCause().getMessage());
            resultMap.put("flag","fail");
        }
        return resultMap;
    }

    @Override
    public Map<String, String> editUser(Map<String, String> map) {
        Map<String, String> resultMap = new HashMap<>();
        map.put("EMAIL", ObjectUtils.isEmpty(map.get("EMAIL"))?"":map.get("EMAIL"));
        map.put("TELEPHONE", ObjectUtils.isEmpty(map.get("TELEPHONE"))?"":map.get("TELEPHONE"));
        try {
            int result = userRelationMapper.editUser(map);
            resultMap.put("flag",result==0?"fail":"success");
        }catch (Exception e){
            resultMap.put("msg",e.getCause().getMessage());
        }
        return resultMap;
    }

    @Override
    public Map<String, String> delUser(String userId) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            Integer result = userRelationMapper.delUser(userId);
            resultMap.put("flag",result.equals(0)?"fail":"success");
        }catch (Exception e){
            resultMap.put("msg",e.getCause().getMessage());
        }
        return resultMap;
    }
}
