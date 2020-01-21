package com.wt.xlsx.parse.valuereader;

import org.apache.poi.ss.usermodel.Cell;

/**
 * 值解析器接口
 *
 * @author: wangtao
 * @date:14:31 2018/6/15
 * @email:386427665@qq.com
 */
public interface ValueReader<T> {

    T read(Cell cell);

}
