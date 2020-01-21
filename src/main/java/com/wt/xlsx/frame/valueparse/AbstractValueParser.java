package com.wt.xlsx.frame.valueparse;

import org.apache.poi.ss.usermodel.CellType;

import java.util.HashMap;
import java.util.Map;

/**
 * 值解析器抽象类
 *
 * @author: wangtao
 * @date:14:31 2018/6/15
 * @email:386427665@qq.com
 */
public abstract class AbstractValueParser implements ValueParser {

    /**
     * 创建一个值解析器缓存
     */
    protected static Map<String, ValueParser> valueParserMap = new HashMap<String, ValueParser>(4);

    static {
        valueParserMap.put("default", new DefaultValueParser());
    }

    /**
     * 根据CellType获得值解析器，优先级比根据java类型获取值解析器高
     * @param cellType
     * @return
     */
    public static ValueParser getValueParser(CellType cellType) {
        if (cellType == null) {
            return null;
        }
        ValueParser valueParser = valueParserMap.get(cellType.name());
        if (valueParser != null) {
            return valueParser;
        }
        switch (cellType) {
            case _NONE:
                //如果cellType是改类型，返回null，后续继续处理其Valueparser
                return null;
            case STRING:
            case BOOLEAN:
            case BLANK:
            case FORMULA:
                valueParserMap.put(cellType.name(), new StringValueParser());
                break;
            case ERROR:
                valueParserMap.put(cellType.name(), new StringValueParser());
                break;
            case NUMERIC:
                valueParserMap.put(cellType.name(), new NumberValueParser());
                break;
        }
        return valueParserMap.get(cellType.name());
    }

    /**
     * 根据java类型获得值解析器，优先级低于根据CellType来获取
     * @param javaType
     * @return
     */
    public static ValueParser getValueParser(String javaType) {
        switch (javaType) {
            case "java.lang.String":
                return new StringValueParser();
            case "int":
            case "double":
            case "long":
            case "float":
            case "java.lang.Integer":
            case "java.lang.Double":
            case "java.lang.Long":
            case "java.lang.Float":
                return new NumberValueParser();
            case "boolean":
                return new BooleanValueParser();
            case "java.util.Date":
                return new DateValueParser();
            default:
                return new DefaultValueParser();
        }
    }


}
