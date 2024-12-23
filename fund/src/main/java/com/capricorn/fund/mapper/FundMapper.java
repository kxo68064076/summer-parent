package com.capricorn.fund.mapper;

import com.capricorn.fund.entity.FundDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FundMapper {
    List<Object> findAllFund();

    List<FundDetail> selectFundPage(@Param("start") Long start, @Param("end") Long end);
}
