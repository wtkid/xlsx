package com.wt.xlsx.frame.builder;

import com.wt.xlsx.frame.annotation.handler.AnnoHandler;
import com.wt.xlsx.frame.excp.ExcelInitException;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * 注解生成Excel的生成器
 *
 * @author: wangtao
 * @date:13:33 2018/6/21
 * @email:386427665@qq.com
 */
public class AnnoXlsxCreater extends AbstractXlsxCreater {

    public AnnoXlsxCreater() {

    }

    public AnnoXlsxCreater(List<?> datas) {
        this.datas = datas;
    }

    public AnnoXlsxCreater datas(List<?> datas) {
        this.datas = datas;
        return this;
    }

    @Override
    public Workbook create() {
        if (datas == null || datas.size() == 0) {
            throw new ExcelInitException("导出数据不能为空");
        }
        //获得生成Excel数据的实体的字节码
        Class clz = datas.get(0).getClass();
        //扫描该类，生成创建Excel所必须的信息
        this.excelProp = new AnnoHandler().scanClassAnnotation(clz);
        return createDocument();
    }

}

