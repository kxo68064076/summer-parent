package com.capricorn.fund.service;

import java.util.List;
import java.util.Map;

public interface IBecomingRichService {
    List<Map<String,Object>> findAllFundDetail(String viewer);

    void fundQueryValue(Map<String,Object> param);
}
