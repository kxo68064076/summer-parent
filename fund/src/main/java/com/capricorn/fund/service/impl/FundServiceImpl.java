package com.capricorn.fund.service.impl;


import com.capricorn.fund.entity.FundDetail;
import com.capricorn.fund.mapper.FundMapper;
import com.capricorn.fund.service.IFundService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FundServiceImpl implements IFundService {
    @Autowired
    private FundMapper fundMapper;

    @Override
    public PageInfo<Object> findAllFund(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<Object> list = fundMapper.findAllFund();

        List<Object> arrayList = new ArrayList<>();
        list.forEach(i ->{
            arrayList.add(i);
        });
        list.clear();
        arrayList.forEach(i ->{
            list.add(i);
        });
        //mapper接口返回的集合对象不能丢弃，否则会失去总数等信息
//        PageInfo<Object> pageInfo = new PageInfo<>(list);
        PageInfo<Object> pageInfo = new PageInfo<>(arrayList);
        return pageInfo;
    }

    @Override
    public List<FundDetail> selectFundPage(Long pageNow, Long pageSize) {
        return fundMapper.selectFundPage((pageNow-1)*pageSize,pageSize*pageNow);
    }
}
