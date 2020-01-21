package com.wt.xlsx.test.read;


import com.wt.xlsx.parse.ExcelParseTool;

import java.util.List;

/**
 * @description
 * @author: wangtao
 * @date:12:45 2020/1/8
 * @email:taow02@jumei.com
 */
public class DataInit {

	/**
	 * 起始第一个客服id，数据库最大id+1
	 */
	private static Integer kfStartId = null;
	/**
	 * 起始第一个分组id，数据库最大id+1
	 */
	private static Integer groupStartId = null;

	/**
	 * 输出sql文件基础路径
	 */
	private static String outBasePath = "";

	public static void main(String[] args) {
		List<ExcelKefu> excelKefus = ExcelParseTool.parseExcel("C:\\Users\\X\\Desktop\\商家客服账号-王涛\\开通账号\\in.xlsx", ExcelKefu.class);
		excelKefus.forEach(System.out::println);
	}
}
