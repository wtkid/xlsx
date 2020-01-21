package com.wt.xlsx.test.read;

import com.wt.xlsx.parse.valuereader.AbstractValueReader;

/**
 * @description
 * @author: wangtao
 * @date:14:12 2020/1/20
 * @email:taow02@jumei.com
 */
public class ExcelKefuReader extends AbstractValueReader {
	@Override
	public ExcelKefu doRead(String cellValue) {
		return new ExcelKefu().setCorpId("11111");
	}
}
