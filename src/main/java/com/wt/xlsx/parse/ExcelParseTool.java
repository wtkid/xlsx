package com.wt.xlsx.parse;

import com.wt.xlsx.frame.annotation.handler.AnnoHandler;
import com.wt.xlsx.frame.base.ExcelType;
import com.wt.xlsx.parse.annotation.ColumnIndexMapping;
import com.wt.xlsx.parse.annotation.prop.FiledMapping;
import com.wt.xlsx.parse.excp.ExcelReadBaseException;
import com.wt.xlsx.parse.valuereader.AbstractValueReader;
import com.wt.xlsx.parse.valuereader.ValueReader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @author: wangtao
 * @date:10:54 2020/1/8
 * @email:taow02@jumei.com
 */
public class ExcelParseTool {

	public static <T> List<T> parseExcel(String fileName, Class<T> clz) {
		Workbook wb = readExcel(fileName);
		List<FiledMapping> mappingConfig = getMappingConfig(clz);
		return doParse(wb, mappingConfig, clz);
	}

	private static <T> List<T> doParse(Workbook wb, List<FiledMapping> mappingConfig, Class<T> clz) {
		Sheet sheet = wb.getSheetAt(0);
		Row row = null;
		List<T> entities = new ArrayList<>();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			T t = createAndFillEntity(row, mappingConfig, clz);
			if (t != null) {
				entities.add(t);
			}
		}
		return entities;
	}

	private static <T> T createAndFillEntity(Row row, List<FiledMapping> filedMapping, Class<T> clz) {
		T t = null;
		try {
			t = clz.newInstance();
			Field field = null;
			for (FiledMapping mc : filedMapping) {
				field = mc.getField();
				field.setAccessible(true);
				field.set(t, mc.getValueReader().read(row.getCell(mc.getCellIndex())));
			}
		} catch (Exception e) {
			e.printStackTrace();
			t = null;
		}
		return t;
	}

	private static <T> List<FiledMapping> getMappingConfig(Class<T> clz) {
		Field[] fields = new AnnoHandler().getFields(clz).toArray(new Field[]{});
		ColumnIndexMapping anno = null;
		List<FiledMapping> mappingConfigs = new ArrayList<>();
		ValueReader valueReader = null;
		for (Field field : fields) {
			anno = field.getAnnotation(ColumnIndexMapping.class);
			if (anno != null) {
				FiledMapping fm = new FiledMapping().setField(field).setCellIndex(anno.cellIndex());
				Class<? extends ValueReader> readerClz = anno.valueReader();
				valueReader = readerClz == ColumnIndexMapping.None.class ?
						AbstractValueReader.get(field) :
						AbstractValueReader.get(readerClz);
				if (valueReader == null) {
					throw new ExcelReadBaseException("property " + field.getName() + " in " + field.getType()
							+ " does not have a right ValueReader");
				}
				fm.setValueReader(valueReader);
				mappingConfigs.add(fm);
			}
		}
		return mappingConfigs;
	}

	public static Workbook readExcel(String fileName) {
		if (fileName == null) {
			return null;
		}
		String extString = fileName.substring(fileName.lastIndexOf(".") + 1);
		InputStream is = null;
		try {
			is = new FileInputStream(fileName);
			if (ExcelType.XLS.name().equalsIgnoreCase(extString)) {
				return new HSSFWorkbook(is);
			} else if (ExcelType.XLSX.name().equalsIgnoreCase(extString)) {
				return new XSSFWorkbook(is);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
