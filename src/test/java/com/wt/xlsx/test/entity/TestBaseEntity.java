package com.wt.xlsx.test.entity;

import com.wt.xlsx.frame.annotation.CellColumn;
import com.wt.xlsx.frame.valueparse.DefaultValueParser;
import com.wt.xlsx.frame.valueparse.StringValueParser;
import org.apache.poi.ss.usermodel.CellType;

import java.util.Date;

/**
 * @description
 * @author: wangtao
 * @date:9:41 2018/6/22
 * @email:386427665@qq.com
 */
public class TestBaseEntity {
    @CellColumn(cellType = CellType.STRING, header = "名字", order = 1, valueParser = StringValueParser.class)
    protected String name;
    @CellColumn(cellType = CellType.STRING, header = "年龄", order = 3, valueParser = DefaultValueParser.class)
    protected String age;
    @CellColumn(cellType = CellType.STRING, header = "性别", order = 2)
    protected String sex;
    @CellColumn(header = "创建时间", order = 0, dateFormat = "yyyy-MM-dd HH:mm:ss")
    protected Date date;
    @CellColumn(header = "boolean", order = 0, method = "isBt")
    protected boolean b;


    public boolean isBt() {
        return b;
    }

    public boolean getB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
