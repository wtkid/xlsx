package com.wt.xlsx.frame.valueparse;

import org.apache.poi.ss.usermodel.Cell;

import java.util.Date;

/**
 * Date类型值解析器
 * @author: wangtao
 * @date:14:28 2018/6/15
 * @email:386427665@qq.com
 */
public class DateValueParser extends AbstractValueParser {

    public void parseAndSet(Cell cell, Object value) {
        Date date = (Date) value;
        cell.setCellValue(date);
    }
}
