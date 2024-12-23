package com.capricorn.summer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FundRelationMapper {
    List<Map<String,Object>> queryHoldingFundList(@Param("fund") String fund,@Param("userName") String userName);

    int editHoldingFund(Map<String, String> map);

    int addCollectFund(Map<String, String> map);

    List<Map<String, Object>> queryCollectList(String fund, String userName);
}
