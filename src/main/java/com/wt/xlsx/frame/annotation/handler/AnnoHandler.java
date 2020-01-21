package com.wt.xlsx.frame.annotation.handler;

import com.wt.xlsx.frame.annotation.CellColumn;
import com.wt.xlsx.frame.annotation.Excel;
import com.wt.xlsx.frame.annotation.prop.CellProp;
import com.wt.xlsx.frame.annotation.prop.ExcelProp;
import com.wt.xlsx.frame.excp.ExcelInitException;
import com.wt.xlsx.frame.valueparse.ValueParser;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 注解处理器
 *
 * @author: wangtao
 * @date:8:30 2018/6/21
 * @email:386427665@qq.com
 */
public class AnnoHandler {

    /**
     * 扫描类上，属性上的注解，生成ExcelProp实体
     *
     * @param clz
     * @return
     */
    public ExcelProp scanClassAnnotation(Class clz) {
        //扫描类上的注解
        Excel excelAnon = (Excel) clz.getAnnotation(Excel.class);
        if (excelAnon == null) {
            throw new ExcelInitException("实体" + clz.getName() + "缺少@Excel注解");
        }
        //创建excelProp对象
        ExcelProp excelProp = new ExcelProp(excelAnon.type(), excelAnon.sheetName(), excelAnon.maxRow());
        //递归方法，获取当前类和父类的所有属性
        List<Field> fields = getFields(clz);
        CellProp cellProp = null;
        //遍历所有属性，扫描每个属性上的注解
        for (Field f : fields) {
            //扫描当前属性上的注解
            cellProp = scanFieldAnontation(f);
            if (cellProp != null) {
                //如果返回的值不为空，说明当前属性被标注了，需要输出到Excel中，添加一个CellProp对象
                excelProp.addCellProp(cellProp);
            }
        }
        //处理方法上的注释
        List<Method> methods = getMethods(clz);
        for (Method m : methods) {
            //扫描当前属性上的注解
            cellProp = scanMethodAnontation(m);
            if (cellProp != null) {
                //如果返回的值不为空，说明当前属性被标注了，需要输出到Excel中，添加一个CellProp对象
                excelProp.addCellProp(cellProp);
            }
        }
        return excelProp;
    }

    /**
     * 扫描方法上的注解
     *
     * @param m
     * @return
     */
    private CellProp scanMethodAnontation(Method m) {
        //获取方法上的注解
        CellColumn cellColumnAnon = m.getAnnotation(CellColumn.class);
        if (cellColumnAnon == null) {
            //如果注解为空，则说明该方法不需要输出到excel
            return null;
        }
        return getCellProp(m, cellColumnAnon);
    }

    /**
     * 扫描某个属性上的注解
     *
     * @param f
     * @return
     */
    public CellProp scanFieldAnontation(Field f) {
        Method m = null;
        //获得属性所属的类字节码
        Class clz = f.getDeclaringClass();
        //获得该属性上的注解
        CellColumn cellColumnAnon = f.getAnnotation(CellColumn.class);
        //获得属性名字
        //获得获取属性值的方法名字
        if (cellColumnAnon == null) {
            //如果当前注解为空，则表示该属性不参与生成Excel，跳过
            return null;
        }
        m = getMethodByField(f, clz, cellColumnAnon);
        return getCellProp(m, cellColumnAnon);
    }

    /**
     * 生成每个列的数据获取配置
     *
     * @param m
     * @param cellColumnAnon
     * @return
     */
    private CellProp getCellProp(Method m, CellColumn cellColumnAnon) {
        CellProp cellProp;
        try {
            //此处之后是获取到注解了的情况，创建cellProp对象
            cellProp = new CellProp(cellColumnAnon.order(), cellColumnAnon.cellType());
            //设置该属性输出到excel的表格的标题头，也就是header
            cellProp.setHeader(cellColumnAnon.header());
            //设置该属性的getter方法
            cellProp.setMethod(m);
            //设置该属性的格式化方式，Date类型时使用
            cellProp.setDateFormat(cellColumnAnon.dateFormat());
            //获取当前字段用户自定义的值解析器
            Class ccc = cellColumnAnon.valueParser();
            ValueParser valueParser = (ValueParser) ccc.newInstance();
            //为当前属性设置值解析器
            cellProp.setValueParser(valueParser);
        } catch (IllegalAccessException e) {
            throw new ExcelInitException(e.getMessage());
        } catch (InstantiationException e) {
            throw new ExcelInitException(e.getMessage());
        }
        return cellProp;
    }

    private Method getMethodByField(Field f, Class clz, CellColumn cellColumnAnon) {
        String fname = f.getName();
        Method m = null;
        String methodName = "";
        //能到这里，cellColumnAnon一定不为null
        methodName = cellColumnAnon.method();
        boolean isCustomMethod = true;
        if ("".equals(methodName)) {
            isCustomMethod = false;
            methodName = "get" + fname.substring(0, 1).toUpperCase() + fname.substring(1);
        }
        try {
            //获得当前属性的方法
            m = clz.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            if (isCustomMethod) {
                throw new ExcelInitException(e.getMessage() + "方法未找到或不是public方法");
            }
            try {
                if (f.getType().getName().contains("boolean")) {
                    m = clz.getMethod("is" + methodName.substring(3));
                    return m;
                }
            } catch (NoSuchMethodException e1) {
                throw new ExcelInitException(e1.getMessage() + " and " + e.getMessage() + "方法未找到,请指定属性获取的方法或为属性生成Getter方法");
            }
            throw new ExcelInitException(e.getMessage() + "方法未找到,请指定属性获取的方法或为属性生成Getter方法");
        }
        return m;
    }

    /**
     * 递归获取当前类以及父类额所有属性
     *
     * @param clz
     * @return
     */
    public List<Field> getFields(Class clz) {
        List<Field> fs = new ArrayList<>();
        Field[] fields = clz.getDeclaredFields();
        for (Field f : fields) {
            fs.add(f);
        }
        if (!clz.isInstance(Object.class)) {
            fs.addAll(getFields(clz.getSuperclass()));
        }
        return fs;
    }

    /**
     * 递归获取当前类以及父类额所有公开方法
     *
     * @param clz
     * @return
     */
    public List<Method> getMethods(Class clz) {
        List<Method> ms = new ArrayList<>();
        Method[] methods = clz.getDeclaredMethods();
        for (Method m : methods) {
            ms.add(m);
        }
        if (!clz.isInstance(Object.class)) {
            ms.addAll(getMethods(clz.getSuperclass()));
        }
        return ms;
    }

}
