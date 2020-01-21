package com.wt.xlsx.frame.builder;

import com.wt.xlsx.frame.annotation.prop.CellProp;
import com.wt.xlsx.frame.annotation.prop.ExcelProp;
import com.wt.xlsx.frame.base.ExcelType;
import com.wt.xlsx.frame.excp.ExcelInitException;
import com.wt.xlsx.frame.valueparse.ValueParser;
import org.apache.poi.ss.usermodel.CellType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 非注解生成Excel时的Excel生成器构建器
 *
 * @author: wangtao
 * @date:9:14 2018/6/22
 * @email:386427665@qq.com
 */
public class XlsxCreaterBuilder {

    /**
     * Excel类型
     */
    private ExcelType type;
    /**
     * sheet页每页最大条数
     */
    private int maxRow;
    /**
     * sheet页的名字，超过maxRow时新创建的sheet页名字默认为sheetName+页数
     */
    private String sheetName;
    /**
     * sheet页的标题头header
     */
    private List<String> headers;
    /**
     * 对应header列的值格式化方式，仅对date类型有效，key为header名称
     */
    private Map<String, String> dateFormats;
    /**
     * 每一列的数据获取的方法名称
     */
    private List<String> methods;
    /**
     * Cell的CellType
     */
    private List<CellType> cellTypes;
    /**
     * 每一列的值解析器
     */
    private List<ValueParser> valueParsers;
    /**
     * 列排序
     */
    private List<Integer> orders;
    /**
     * 生成Excel的实体数据
     */
    private List<?> datas;

    public XlsxCreaterBuilder(String sheetName, List<String> headers, List<String> methodsOrFields, List<?> datas) {
        this.sheetName = sheetName;
        this.headers = headers;
        this.datas = datas;
        this.methods = new ArrayList<String>();
        for (String mf : methodsOrFields) {
            if (!mf.startsWith("get")) {
                mf = "get" + mf.substring(0, 1).toUpperCase() + mf.substring(1);
            }
            this.methods.add(mf);
        }
    }

    public XlsxCreaterBuilder maxRow(int maxRow) {
        this.maxRow = maxRow;
        return this;
    }

    public XlsxCreaterBuilder type(ExcelType type) {
        this.type = type;
        return this;
    }

    public XlsxCreaterBuilder cellTypes(List<CellType> cellTypes) {
        this.cellTypes = cellTypes;
        return this;
    }

    public XlsxCreaterBuilder CellType(List<ValueParser> valueParsers) {
        this.valueParsers = valueParsers;
        return this;
    }

    public XlsxCreaterBuilder orders(List<Integer> orders) {
        this.orders = orders;
        return this;
    }

    public XlsxCreaterBuilder addDateFormat(String headerName, String dateFormat) {
        if (this.dateFormats == null) {
            this.dateFormats = new HashMap<>(8);
        }
        this.dateFormats.put(headerName, dateFormat);
        return this;
    }

    /**
     * 构建一个非注解生成Excel的Excel生成器
     *
     * @return
     */
    public AbstractXlsxCreater build() {
        ExcelProp excelProp = buildExceProp();
        XlsxCreater xlsxCreater = new XlsxCreater(this.datas);
        xlsxCreater.setExcelProp(excelProp);
        return xlsxCreater;
    }

    /**
     * 构建生成Excel所必须的配置ExcelProp
     *
     * @return
     */
    private ExcelProp buildExceProp() {
        ExcelProp excelProp = new ExcelProp(this.type == null ? ExcelType.XLSX : this.type, this.sheetName);
        excelProp.setMaxRow(this.maxRow > 0 ? maxRow : 5000);
        CellProp cellProp = null;
        //获得生成Excel数据的实体字节码
        Class clz = datas.get(0).getClass();
        int i = 0;
        try {
            //遍历所有header，有多少个header就有多少列，为每一列生成CellProp配置
            for (; i < this.headers.size(); i++) {
                cellProp = new CellProp();
                //设置当前列的header
                cellProp.setHeader(headers.get(i));
                //设置每一列的数据获取方法，多余的方法省略
                if (methods != null && methods.size() > i) {
                    cellProp.setMethod(clz.getMethod(methods.get(i)));
                } else {
                    //每一个header都必须有一个获取值得方法，如果method不够或没有，抛出异常
                    throw new ExcelInitException("请在methods中指定<" + headers.get(i) + ">列的数据获取方法");
                }
                //设置每一列的排序，默认从第一个取起走，不够的补0，多余的省略
                if (orders != null && orders.size() > i) {
                    cellProp.setOrder(orders.get(i));
                } else {
                    cellProp.setOrder(0);
                }
                //设置每一个Cell的CellType，有就设置，没有就省略，默认从第一个取起走
                if (cellTypes != null && cellTypes.size() > i) {
                    cellProp.setCellType(cellTypes.get(i));
                }
                //设置每一个值解析器，有就设置，没有就省略，默认从第一个取起走
                if (valueParsers != null && valueParsers.size() > i) {
                    cellProp.setValueParser(valueParsers.get(i));
                }
                //为对应的header设置数据格式化方式，仅针对date类型
                if (dateFormats != null) {
                    cellProp.setDateFormat(dateFormats.get(headers.get(i)));
                }
                excelProp.addCellProp(cellProp);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new ExcelInitException("方法" + clz.getName() + "." + methods.get(i) + "()未找到");
        }
        return excelProp;
    }

}
