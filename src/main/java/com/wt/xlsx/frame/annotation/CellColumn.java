package com.wt.xlsx.frame.annotation;

import com.wt.xlsx.frame.valueparse.ValueParser;
import org.apache.poi.ss.usermodel.CellType;

import java.lang.annotation.*;

/**
 * 输出Excel的列注解
 *
 * @author: wangtao
 * @date:13:43 2018/6/15
 * @email:386427665@qq.com
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CellColumn {

    /**
     * Cell的CellType类型
     *
     * @return
     */
    CellType cellType() default CellType._NONE;

    /**
     * 排序，值越越小的输出后在Excel的列越靠前
     *
     * @return
     */
    int order() default 0;

    /**
     * 当前属性的标题头header
     *
     * @return
     */
    String header();

    /**
     * 当前属性的格式化方式，列如yyyy-MM-dd HH:mm:ss，仅针对date类型
     *
     * @return
     */
    String dateFormat() default "";

    /**
     * 如果不是java默认的getter/setter方法，指定获取值的方法名称，当该注解作用于方法上时该属性无效
     *
     * @return
     */
    String method() default "";

    /**
     * 自定义当前属性的值解析器
     *
     * @return
     */
    Class<? extends ValueParser> valueParser() default None.class;

    class None implements ValueParser {

    	@Override
        public void parseAndSet(org.apache.poi.ss.usermodel.Cell cell, Object value) {

        }
    }
}
