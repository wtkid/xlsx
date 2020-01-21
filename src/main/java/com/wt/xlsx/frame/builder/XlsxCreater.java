package com.wt.xlsx.frame.builder;

import com.wt.xlsx.frame.excp.ExcelInitException;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * 非注解生成Excel的生成器
 *
 * @author: wangtao
 * @date:13:47 2018/6/15
 * @email:386427665@qq.com
 */
public class XlsxCreater extends AbstractXlsxCreater {

    public XlsxCreater(List<?> datas) {
        this.datas = datas;
    }

    /**
     * 返回一个非注解试创建Excel的builder
     *
     * @param sheetName
     * @param headers
     * @param methodsOrFields
     * @param datas
     * @return
     */
    public static XlsxCreaterBuilder builder(String sheetName, List<String> headers, List<String> methodsOrFields, List<?> datas) {
        return new XlsxCreaterBuilder(sheetName, headers, methodsOrFields, datas);
    }

    @Override
    public Workbook create() {
        if (datas == null || datas.size() == 0) {
            throw new ExcelInitException("导出数据不能为空");
        }
        return createDocument();
    }

}
