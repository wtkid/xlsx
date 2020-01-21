package com.wt.xlsx.frame.annotation.prop;

import com.wt.xlsx.frame.base.ExcelType;

import java.util.ArrayList;
import java.util.List;

/**
 * 输出Excel的配置
 *
 * @author: wangtao
 * @date:8:59 2018/6/21
 * @email:386427665@qq.com
 */
public class ExcelProp {

    /**
     * 输出Excel的类型，XLS还是XLSX
     */
    private ExcelType type;
    /**
     * Excel的sheet的名称，超过一页之后的名称默认为当前sheet名称加上当前页数
     */
    private String sheetName;
    /**
     * Excel的sheet页每页的最大记录条数，超过将新增一页sheet页，默认为5000
     */
    private int maxRow;
    /**
     * 每一页sheet页的实体数据获取的属性
     */
    private List<CellProp> cellProps;

    public ExcelProp(ExcelType type, String sheetName) {
        this.type = type;
        this.sheetName = sheetName;
        this.cellProps = new ArrayList<CellProp>();
        this.maxRow = 5000;
    }

    public ExcelProp(ExcelType type, String sheetName, int maxRow) {
        this.type = type;
        this.sheetName = sheetName;
        this.cellProps = new ArrayList<CellProp>();
        this.maxRow = maxRow <= 0 ? 5000 : maxRow;
    }

    public int getMaxRow() {
        return maxRow;
    }

    public void setMaxRow(int maxRow) {
        this.maxRow = maxRow;
    }

    public ExcelType getType() {
        return type;
    }

    public void setType(ExcelType type) {
        this.type = type;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<CellProp> getCellProps() {
        return cellProps;
    }

    public void setCellProps(List<CellProp> cellProps) {
        this.cellProps = cellProps;
    }

    public void addCellProp(CellProp cellProp) {
        this.cellProps.add(cellProp);
    }
}
