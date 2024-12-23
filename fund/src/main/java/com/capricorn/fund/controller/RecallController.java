package com.capricorn.fund.controller;

import com.capricorn.fund.service.impl.BecomingRichServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("openFund")
public class RecallController {

    @Autowired
    BecomingRichServiceImpl service;

    @RequestMapping("openFund")
    public String openFund(Model model, @RequestParam("viewer") String viewer){
        List<Map<String, Object>> allFundDetail = service.findAllFundDetail(viewer);
        Map<String, Object> param = new HashMap<>();
        param.put("viewer",viewer);
        param.put("totalValue",null);
        param.put("valuationValue",null);
        param.put("outFlag",null);
        service.fundQueryValue(param);
        if (param.get("outFlag").toString().equals("0")){
            String totalValue = String.format("%.2f", (new BigDecimal(param.get("totalValue").toString())));
            String valuationValue = String.format("%.2f", (new BigDecimal(param.get("valuationValue").toString())));
            param.put("totalValue",totalValue);
            param.put("valuationValue",valuationValue);
        }

//        allFundDetail.forEach(detai->{
//            if (detai.get("FUND_VALUATION").toString().contains("-"))
//                detai.put("down","y");
//        });
        model.addAttribute("allFundDetail",allFundDetail);
        model.addAttribute("fundQueryValue",param);
        return "fund";
    }
}
