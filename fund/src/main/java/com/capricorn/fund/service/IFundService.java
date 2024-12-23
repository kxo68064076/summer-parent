package com.capricorn.fund.service;

import com.capricorn.fund.entity.FundDetail;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IFundService {
    PageInfo<Object> findAllFund(int pageSize, int pageNum);

    List<FundDetail> selectFundPage(Long pageNow, Long pageSize);
}
