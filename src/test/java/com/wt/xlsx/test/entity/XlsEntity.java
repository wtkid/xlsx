package com.wt.xlsx.test.entity;

import com.wt.xlsx.frame.annotation.CellColumn;
import com.wt.xlsx.frame.annotation.Excel;
import com.wt.xlsx.frame.base.ExcelType;
import com.wt.xlsx.frame.builder.AnnoXlsxCreater;
import com.wt.xlsx.frame.builder.XlsxCreater;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @description
 * @author: wangtao
 * @date:9:16 2018/6/21
 * @email:@email:386427665@qq.com
 */
@Excel(type = ExcelType.XLS, sheetName = "xlsSheet", maxRow = 80)
public class XlsEntity extends TestBaseEntity {

    @CellColumn(header = "xlsHeader")
    private String xls;

    private String haha;

    public String getXls() {
        return xls;
    }

    public void setXls(String xls) {
        this.xls = xls;
    }

    public String getHaha() {
        return haha;
    }

    public void setHaha(String haha) {
        this.haha = haha;
    }

    @CellColumn(header = "UUID")
    public String haha(){
        return UUID.randomUUID().toString();
    }

    public XlsEntity(String name, String age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.date = new Date();
    }

}
