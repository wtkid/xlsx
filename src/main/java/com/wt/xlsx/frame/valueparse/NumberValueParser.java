package com.wt.xlsx.frame.valueparse;

import org.apache.poi.ss.usermodel.Cell;

/**
 * 数值类型值解析器
 * @author: wangtao
 * @date:14:28 2018/6/15
 * @email:386427665@qq.com
 */
public class NumberValueParser extends AbstractValueParser {

    public void parseAndSet(Cell cell, Object value) {
        String s = value + "";
        if (s.contains(".")) {
            double d = new Double(s);
            cell.setCellValue(d);
        } else {
            int i = new Integer(s);
            cell.setCellValue(i);
        }
    }
}
