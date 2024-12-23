package com.capricorn.fund.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import lombok.Data;

@Data
@ColumnWidth(20)
@ContentStyle(verticalAlignment = VerticalAlignmentEnum.CENTER,horizontalAlignment = HorizontalAlignmentEnum.CENTER)
public class FundDetail {

    @ExcelProperty(value = {"基本信息","基金代码"},index = 0)
    private String fundCode;
    @ExcelProperty(value = {"基本信息","基金名称"},index = 1)
    private String fundName;
    @ExcelProperty(value = {"重要信息","净值"},index = 2)
    private String fundValue;
    @ExcelProperty(value = {"重要信息","估算涨幅"},index = 3)
    private String fundValuationIncrease;
    @ExcelProperty(value = {"重要信息","估值"},index = 4)
    private String fundValuation;
    @ExcelProperty(value = {"重要信息","更新时间"},index = 5)
    private String updateTime;


}
