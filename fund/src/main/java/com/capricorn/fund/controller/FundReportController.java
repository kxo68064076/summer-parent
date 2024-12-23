package com.capricorn.fund.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.capricorn.fund.entity.FundDetail;
import com.capricorn.fund.service.IFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("report")
public class FundReportController {

    @Autowired
    @Qualifier("fundServiceImpl")
    private IFundService fundService;

    @RequestMapping("export")
    public void export(Map<String,String> map, HttpServletResponse response){
        Long pageSize = StringUtils.isEmpty(map.get("pageSize"))?1000l:Long.parseLong(map.get("pageSize"));
        Long pageNow = StringUtils.isEmpty(map.get("pageNow"))?1l:Long.parseLong(map.get("pageNow"));

        List<FundDetail> list = fundService.selectFundPage(pageNow,pageSize);

        String fileName = "基金估值明细.xlsx";

        try {
            // 设置文本内省
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // 设置字符编码
            response.setCharacterEncoding("utf-8");
            // 设置响应头
            response.setHeader("Content-disposition", "attachment; filename*=utf-8''"+URLEncoder.encode(fileName,"UTF-8"));
            EasyExcel.write(response.getOutputStream(), FundDetail.class)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .sheet("明细")
                    .doWrite(list);

        }catch (Exception e){

        }

    }

}
