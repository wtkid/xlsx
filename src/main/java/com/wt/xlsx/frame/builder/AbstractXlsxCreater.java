package com.wt.xlsx.frame.builder;

import com.wt.xlsx.frame.annotation.CellColumn;
import com.wt.xlsx.frame.annotation.prop.CellProp;
import com.wt.xlsx.frame.annotation.prop.ExcelProp;
import com.wt.xlsx.frame.base.ExcelType;
import com.wt.xlsx.frame.excp.ExcelInitException;
import com.wt.xlsx.frame.valueparse.AbstractValueParser;
import com.wt.xlsx.frame.valueparse.DefaultValueParser;
import com.wt.xlsx.frame.valueparse.ValueParser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.wt.xlsx.frame.base.ExcelType.XLS;
import static com.wt.xlsx.frame.base.ExcelType.XLSX;

/**
 * Excel生成器抽象类
 *
 * @author: wangtao
 * @date:13:35 2018/6/21
 * @email:386427665@qq.com
 */
public abstract class AbstractXlsxCreater {

    /**
     * 生成Excel所需的全部信息
     */
    protected ExcelProp excelProp;
    /**
     * 生成Excel所需的全部数据
     */
    protected List<?> datas;

    public ExcelProp getExcelProp() {
        return excelProp;
    }

    public void setExcelProp(ExcelProp excelProp) {
        this.excelProp = excelProp;
    }

    /**
     * 根据ExcelType创建对应的Workbook对象
     *
     * @param type
     * @return
     */
    protected Workbook createWorkbook(ExcelType type) {
        if (type == XLS) {
            return new HSSFWorkbook();
        } else if (type == XLSX) {
            return new XSSFWorkbook();
        } else {
            throw new ExcelInitException("不支持的Excel类型");
        }
    }

    /**
     * 生成Excel文件，数据超过maxRow时会自动分页
     *
     * @return
     */
    protected Workbook createDocument() {
        //创建一个workbook对象
        Workbook wb = this.createWorkbook(excelProp.getType());
        //获取最大的每页条数
        int maxRow = excelProp.getMaxRow();
        //定义当前已经取到的记录下标，起始下标
        int currentRow = 0;
        List<?> ds = this.datas;
        //定义当前需要获取到的记录条数下标，目标下标
        int getRows = 0;
        int sheetNum = 1;
        //如果当前已经取到的记录下标小于总条数-1，则说明还有数据未生成，创建新的一页sheet生成
        while (currentRow < ds.size() - 1) {
            //当前需要生成的记录条数，如果剩下的记录条数大于最大条数，则只取maxRow，否则截取到总条数
            getRows = ds.size() - currentRow > maxRow ? maxRow : ds.size();
            //截取List，从crrentRow到getRows
            this.datas = ds.subList(currentRow, getRows);
            //为当前的Excel创建一页sheet
            createOneSheet(wb);
            currentRow += getRows;
            //循环过后，重置sheet页名称
            excelProp.setSheetName(excelProp.getSheetName() + sheetNum);
            sheetNum++;
        }
        return wb;
    }

    /**
     * 创建一页数据条数不超过maxRow的sheet页
     *
     * @param wb
     * @return
     */
    public Workbook createOneSheet(Workbook wb) {
        //获取数据生成所需的配置CellProp
        List<CellProp> cellProps = excelProp.getCellProps();
        //初始化值解析器
        initCellParser(cellProps);
        //创建一页只带了Header的sheet页
        Sheet sheet = createSheetWithHeader(wb, excelProp);

        CellProp cellProp = null;
        ValueParser valueParser = null;
        Row row = null;
        CellStyle cellStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        Object data = null;
        try {
            //遍历所有datas，每一个data都是一行row的数据
            for (int i = 0; i < datas.size(); i++) {
                data = datas.get(i);
                //第0行是header，这里创建行row从1开始
                row = sheet.createRow(i + 1);
                //遍历所有CellProp，每一个CellProp都是对应的每一行的一个对应header列的值获取配置
                for (int j = 0; j < cellProps.size(); j++) {
                    //获取到当前行的当前列值获取配置信息CellProp
                    cellProp = cellProps.get(j);
                    //获取解析器
                    valueParser = cellProp.getValueParser();
                    //在当前行的当前列上创建一个Cell
                    Cell cell = row.createCell(j);
                    //如果dateFomat不为空，添加格式化Date的CellStyle
                    if (!"".equals(cellProp.getDateFormat()) && cellProp.getDateFormat() != null) {
                        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(cellProp.getDateFormat()));
                        cell.setCellStyle(cellStyle);
                    }
                    //值解析器为当前cell赋值
                    valueParser.parseAndSet(cell, cellProp.getMethod().invoke(data));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return wb;
    }

    /**
     * 初始化值解析器
     *
     * @param cellProps
     */
    private void initCellParser(List<CellProp> cellProps) {
        CellProp cellProp = null;
        ValueParser valueParser = null;
        //遍历所有的属性值获取配置信息CellProp
        for (int j = 0; j < cellProps.size(); j++) {
            cellProp = cellProps.get(j);
            //获取到值解析器
            valueParser = cellProp.getValueParser();
            //如果用户没有自定义值解析器，
            //如果是扫描方式，值解析器为默认值CellColumn.None，如果是非注解方式则值解析器为默认为null，
            //如果值解析器为这默认值，则将其设置为null，之后重新指定正确的值解析器
            if (valueParser instanceof CellColumn.None) {
                valueParser = null;
            }
            if (valueParser == null) {
                //如果值解析器为null，根据用户定义的CellType来获取值解析器，CellType也有默认值，
                //当为_NONE的时候，获取到的值解析器也是null，这个在getValueParser()方法中已经处理了
                valueParser = AbstractValueParser.getValueParser(cellProp.getCellType());
            }
            if (valueParser == null) {
                //如果值解析器为null，则通过当前属性的get方法的返回值类型来获取值解析器
                valueParser = AbstractValueParser.getValueParser(cellProp.getMethod().getReturnType().getName());
            }
            if (valueParser == null) {
                //如果值解析器为null，则设置默认的值解析器方法
                valueParser = new DefaultValueParser();
            }
            //设置当前属性的值解析器
            cellProp.setValueParser(valueParser);
        }
        //排序所有的属性，该顺序是输出到excel当中后header的顺序
        Collections.sort(cellProps, new Comparator<CellProp>() {
            @Override
            public int compare(CellProp o1, CellProp o2) {
                int order1 = o1.getOrder();
                int order2 = o2.getOrder();
                return order1 > order2 ? 1 : order1 < order2 ? -1 : 0;
            }
        });
    }

    /**
     * 创建一页只带了header的sheet页
     *
     * @param wb
     * @param excelProp
     * @return
     */
    private Sheet createSheetWithHeader(Workbook wb, ExcelProp excelProp) {
        //根据sheetName创建一页sheet
        Sheet sheet = wb.createSheet(excelProp.getSheetName());
        //创建第0行，放置header
        Row row = sheet.createRow(0);
        List<CellProp> cellProps = excelProp.getCellProps();
        for (int i = 0; i < cellProps.size(); i++) {
            //添加header
            row.createCell(i).setCellValue(cellProps.get(i).getHeader());
        }
        return sheet;
    }

    /**
     * 由子类实现的创建方法
     *
     * @return
     */
    public abstract Workbook create();

    /**
     * 创建Excel并输出到outputStream
     *
     * @param os
     * @throws IOException
     */
    public void createAndWrite(OutputStream os) throws IOException {
        Workbook wb = create();
        wb.write(os);
        os.flush();
        os.close();
    }
}
