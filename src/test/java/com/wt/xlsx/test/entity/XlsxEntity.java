package com.wt.xlsx.test.entity;

import com.wt.xlsx.frame.annotation.CellColumn;
import com.wt.xlsx.frame.annotation.Excel;
import com.wt.xlsx.frame.base.ExcelType;

import java.util.Date;

/**
 * @description
 * @author: wangtao
 * @date:9:16 2018/6/21
 * @email:386427665@qq.com
 */
@Excel(type = ExcelType.XLSX, sheetName = "xlsxSheet", maxRow = 80)
public class XlsxEntity extends TestBaseEntity {

    @CellColumn(header = "xlsxHeader")
    private String xlsx;

    private String haha;

    public String getXlsx() {
        return xlsx;
    }

    public void setXlsx(String xlsx) {
        this.xlsx = xlsx;
    }

    public String getHaha() {
        return haha;
    }

    public void setHaha(String haha) {
        this.haha = haha;
    }

    public XlsxEntity(String name, String age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.date = new Date();
    }

}
