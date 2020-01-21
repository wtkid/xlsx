package com.wt.xlsx.parse.annotation;

import com.wt.xlsx.parse.valuereader.ValueReader;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.annotation.*;

/**
 * @description
 * @author: wangtao
 * @date:11:04 2020/1/8
 * @email:taow02@jumei.com
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ColumnIndexMapping {

	int cellIndex() default 0;

	Class<? extends ValueReader> valueReader() default ColumnIndexMapping.None.class;

	class None implements ValueReader {

		@Override
		public Object read(Cell cell) {
			return null;
		}
	}
}
