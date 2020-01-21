package com.wt.xlsx.parse.valuereader;

import com.wt.xlsx.parse.excp.ExcelReadBaseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 值阅读器抽象类
 *
 * @author: wangtao
 * @date:14:31 2018/6/15
 * @email:386427665@qq.com
 */
public abstract class AbstractValueReader<T> implements ValueReader {

	/**
	 * 创建一个值阅读器缓存
	 */
	protected static Map<Class, ValueReader> valueReaderMap = new HashMap<>(4);

	public static ValueReader get(Class<? extends ValueReader> clz) {
		try {
			ValueReader valueReader = valueReaderMap.get(clz);
			if (valueReader == null) {
				valueReader = clz.newInstance();
				valueReaderMap.put(clz, valueReader);
			}
			return valueReader;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExcelReadBaseException("valueReader " + clz.toString() + " 初始化失败");
		}

	}

	public static ValueReader get(Field field) {
		String javaType = field.getType().getName();
		switch (javaType) {
			case "java.lang.String":
				return BaseValueReader.STRING_READER;
			case "java.lang.Integer":
			case "int":
				return BaseValueReader.INT_READER;
			case "java.lang.Double":
			case "double":
				return BaseValueReader.DOUBLE_READER;
			case "java.lang.Long":
			case "long":
				return BaseValueReader.LONG_READER;
			case "java.lang.Float":
			case "float":
				return BaseValueReader.FLOAT_READER;
			case "java.lang.Boolean":
			case "boolean":
				return BaseValueReader.BOOLEAN_READER;
			default:
				return null;
		}
	}

	@Override
	public T read(Cell cell) {
		if (cell == null) {
			return null;
		}
		cell.setCellType(CellType.STRING);
		return doRead(cell.getStringCellValue());
	}

	public abstract T doRead(String cellValue);

}
