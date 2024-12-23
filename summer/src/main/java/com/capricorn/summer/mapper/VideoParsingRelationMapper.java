package com.capricorn.summer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface VideoParsingRelationMapper {

    int delParsingUrl(String url);

    List<Map<String, String>> queryParameterList();

    int addParsingUrl(String url);
}
