package com.capricorn.summer.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface LoginRelationMapper {
    Map<String,String> queryUserByUserId(Map<String,String> map);
}
