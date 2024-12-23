package com.capricorn.summer.service.Impl;

import com.capricorn.summer.mapper.VideoParsingRelationMapper;
import com.capricorn.summer.service.IVideoParsingRelationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoParsingRelationServiceImpl implements IVideoParsingRelationService {

    @Autowired
    VideoParsingRelationMapper mapper;

    @Override
    public PageInfo<Map<String, String>> queryParameterList(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String, String>> result = mapper.queryParameterList();
        return new PageInfo<>(result);
    }

    @Override
    public Map<String, String> delParsingUrl(String url) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            int result = mapper.delParsingUrl(url);
            resultMap.put("flag",result==0?"fail":"success");
        }catch (Exception e){
            resultMap.put("msg",e.getCause().getMessage());
            resultMap.put("flag","fail");
        }
        return resultMap;
    }

    @Override
    public Map<String, String> addParsingUrl(String url) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            int result = mapper.addParsingUrl(url);
            resultMap.put("flag",result==0?"fail":"success");
        }catch (Exception e){
            resultMap.put("msg",e.getCause().getMessage());
            resultMap.put("flag","fail");
        }
        return resultMap;
    }
}
