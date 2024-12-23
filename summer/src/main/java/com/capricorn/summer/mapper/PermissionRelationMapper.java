package com.capricorn.summer.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PermissionRelationMapper {
    List<Map<String,String>> getAllRoutes();
}
