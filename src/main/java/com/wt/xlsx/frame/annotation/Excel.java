package com.wt.xlsx.frame.annotation;

import com.wt.xlsx.frame.base.ExcelType;

import java.lang.annotation.*;

/**
 * 输出Excel文件注解
 *
 * @author: wangtao
 * @date:8:32 2018/6/21
 * @email:386427665@qq.com
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Excel {

    /**
     * 输出Excel的名称，XLS或者XLSX
     *
     * @return
     */
    ExcelType type() default ExcelType.XLSX;

    /**
     * 输出Excel的sheet页名称，当记录超过maxRow之后，创建的新的sheet页名称默认为sheetName()+页数
     *
     * @return
     */
    String sheetName() default "sheet";

    /**
     * 每个sheet页最大的记录条数，超过将另外创建sheet页
     *
     * @return
     */
    int maxRow() default 5000;

}
