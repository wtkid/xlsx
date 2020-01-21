package com.wt.xlsx.frame.annotation.prop;

import com.wt.xlsx.frame.valueparse.ValueParser;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;

import java.lang.reflect.Method;

/**
 * 元素属性输出到excel的配置
 *
 * @author: wangtao
 * @date:8:57 2018/6/21
 * @email:386427665@qq.com
 */
public class CellProp {

    /**
     * 排序,默认为0
     */
    private int order;
    /**
     * 单元格Cell的cellType类型
     */
    private CellType cellType;
    /**
     * 当前cell的标题头名字
     */
    private String header;
    /**
     * 填充当前cell的方法名字
     */
    private Method method;
    /**
     * 当前Cell的值解析器
     */
    private ValueParser valueParser;
    /**
     * 仅针对Date，格式化字符串，列如yyyy-MM-dd HH:mm:ss
     */
    private String dateFormat;

    public CellProp() {
    }

    public CellProp(int order, CellType cellType) {
        this.order = order;
        this.cellType = cellType;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public ValueParser getValueParser() {
        return valueParser;
    }

    public void setValueParser(ValueParser valueParser) {
        this.valueParser = valueParser;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
