package com.capricorn.summer.service;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IFundRelationService {

    PageInfo<Map<String, Object>> queryHoldingFundList(int pageNum,int pageSize,String fund,String userName);

    int editHoldingFund(Map<String,String> map);

    HashMap<String,String> addCollectFund(Map<String, String> map);

    PageInfo<Map<String, Object>> queryCollectList(int parseInt, int parseInt1, String fund, String userName);
}
