package com.capricorn.fund.service.impl;

import com.capricorn.fund.mapper.BecomingRichMapper;
import com.capricorn.fund.service.IBecomingRichService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BecomingRichServiceImpl implements IBecomingRichService {

    @Autowired
    private BecomingRichMapper mapper;

    @Override
    public List<Map<String, Object>> findAllFundDetail(String viewer) {
        return mapper.findAllFundDetail(viewer);
    }

    @Override
    public void fundQueryValue(Map<String,Object> param) {
        mapper.fundQueryValue(param);
    }
}
