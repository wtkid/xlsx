package com.wt.xlsx.parse.valuereader;

import org.apache.poi.ss.formula.functions.T;

import java.util.function.Function;

/**
 * @description
 * @author: wangtao
 * @date:13:17 2020/1/20
 * @email:taow02@jumei.com
 */
public class BaseValueReader<T> extends AbstractValueReader {

	public static ValueReader STRING_READER = new BaseValueReader((Function<String, String>)(v->v));
	public static ValueReader LONG_READER = new BaseValueReader((Function<String, Long>)(Long::valueOf));
	public static ValueReader INT_READER = new BaseValueReader((Function<String, Integer>)(Integer::valueOf));
	public static ValueReader DOUBLE_READER = new BaseValueReader((Function<String, Double>)(Double::valueOf));
	public static ValueReader FLOAT_READER = new BaseValueReader((Function<String, Float>)(Float::valueOf));
	public static ValueReader BOOLEAN_READER = new BaseValueReader((Function<String, Boolean>)(Boolean::valueOf));

	private Function<String, T> function;

	public BaseValueReader(Function function) {
		this.function = function;
	}

	@Override
	public Object doRead(String cellValue) {
		return function.apply(cellValue);
	}
}
