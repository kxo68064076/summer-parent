package com.capricorn.summer.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface IVideoParsingRelationService {

    PageInfo<Map<String, String>> queryParameterList(int pageNum,int pageSize);

    Map<String, String> delParsingUrl(String url);

    Map<String, String> addParsingUrl(String url);
}
