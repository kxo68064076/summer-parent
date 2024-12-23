package com.capricorn.summer.service.Impl;

import com.capricorn.summer.mapper.FundRelationMapper;
import com.capricorn.summer.service.IFundRelationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FundRelationServiceImpl implements IFundRelationService {

    @Autowired
    FundRelationMapper fundRelationMapper;

    @Override
    public PageInfo<Map<String, Object>> queryHoldingFundList(int pageNum,int pageSize,String fund,String userName) {
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String, Object>> fundList = fundRelationMapper.queryHoldingFundList(fund,userName);
        return new PageInfo<>(fundList);
    }

    @Override
    public int editHoldingFund(Map<String,String> map) {
        return fundRelationMapper.editHoldingFund(map);
    }

    @Override
    public HashMap<String,String> addCollectFund(Map<String, String> map) {
        int result=0;
        HashMap<String,String> hashMap = new HashMap<>();

        try {
            result=fundRelationMapper.addCollectFund(map);
            hashMap.put("result",result!=0?"success":"fail");
        }catch (Exception e){
            hashMap.put("msg",e.getCause().getMessage());
            hashMap.put("result","fail");
        }
        return hashMap;
    }

    @Override
    public PageInfo<Map<String, Object>> queryCollectList(int pageNum,int pageSize, String fund, String userName) {
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String, Object>> fundList = fundRelationMapper.queryCollectList(fund,userName);
        return new PageInfo<>(fundList);
    }
}
