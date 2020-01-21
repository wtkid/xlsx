package com.wt.xlsx.test;

import com.wt.xlsx.frame.base.ExcelType;
import com.wt.xlsx.frame.builder.AnnoXlsxCreater;
import com.wt.xlsx.frame.builder.XlsxCreater;
import com.wt.xlsx.test.entity.XlsxEntity;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description
 * @author: wangtao
 * @date:9:16 2018/6/21
 * @email:386427665@qq.com
 */
public class XlsxTest {

    @Test
    public void anonTest() throws Exception {
        List<XlsxEntity> list = new ArrayList<XlsxEntity>();
        for (int i = 0; i < 100; i++) {
            list.add(new XlsxEntity("wt" + i, "age" + i, "sex" + i));
        }
        new AnnoXlsxCreater().datas(list).createAndWrite(new FileOutputStream("D:/test/anonTest.xlsx"));
    }

    @Test
    public void notAnonTest() throws Exception {
        List<XlsxEntity> list = new ArrayList<XlsxEntity>();
        for (int i = 0; i < 100; i++) {
            list.add(new XlsxEntity("wt" + i, "age" + i, "sex" + i));
        }
        XlsxCreater.builder("sheet1",
                Arrays.asList("header1", "header2", "header3", "header4","header5"),
                Arrays.asList("age", "getName", "sex", "date","b"), list)
                .type(ExcelType.XLSX)
                .maxRow(60)
                .addDateFormat("header3", "yyyy-MM-dd")
                .addDateFormat("header4", "yyyy-MM-dd")
                .build()
                .createAndWrite(new FileOutputStream("D:/test/notAnonTest.xlsx"));
    }
}
