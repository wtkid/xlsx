package com.wt.xlsx.parse.annotation.prop;

import com.wt.xlsx.parse.valuereader.ValueReader;

import java.lang.reflect.Field;

/**
 * @description
 * @author: wangtao
 * @date:11:08 2020/1/8
 * @email:taow02@jumei.com
 */
public class FiledMapping {

	private Field field;
	private Integer cellIndex;
	private ValueReader valueReader;

	public Field getField() {
		return field;
	}

	public FiledMapping setField(Field field) {
		this.field = field;
		return this;
	}

	public Integer getCellIndex() {
		return cellIndex;
	}

	public FiledMapping setCellIndex(Integer cellIndex) {
		this.cellIndex = cellIndex;
		return this;
	}

	public ValueReader getValueReader() {
		return valueReader;
	}

	public FiledMapping setValueReader(ValueReader valueReader) {
		this.valueReader = valueReader;
		return this;
	}
}
