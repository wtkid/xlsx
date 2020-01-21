package com.wt.xlsx.test.read;

import com.wt.xlsx.parse.annotation.ColumnIndexMapping;

/**
 * @description
 * @author: wangtao
 * @date:10:54 2020/1/8
 * @email:taow02@jumei.com
 */
public class ExcelKefu {

	@ColumnIndexMapping(cellIndex = 1)
	private String corpName;
	@ColumnIndexMapping(cellIndex = 2)
	private String businessName;
	@ColumnIndexMapping(cellIndex = 0)
	private String corpId;
	@ColumnIndexMapping(cellIndex = 3)
	private String username;
	@ColumnIndexMapping(cellIndex = 4)
	private String nickname;
	@ColumnIndexMapping(cellIndex = 5)
	private String maxDialog;
	@ColumnIndexMapping(cellIndex = 6)
	private String name;
	@ColumnIndexMapping(cellIndex = 6, valueReader = ExcelKefuReader.class)
	private ExcelKefu excelKefu;

	public ExcelKefu() {
	}

	public String getCorpName() {
		return corpName;
	}

	public ExcelKefu setCorpName(String corpName) {
		this.corpName = corpName;
		return this;
	}

	public String getBusinessName() {
		return businessName;
	}

	public ExcelKefu setBusinessName(String businessName) {
		this.businessName = businessName;
		return this;
	}

	public String getMaxDialog() {
		return maxDialog;
	}

	public ExcelKefu setMaxDialog(String maxDialog) {
		this.maxDialog = maxDialog;
		return this;
	}

	public String getCorpId() {
		return corpId;
	}

	public ExcelKefu setCorpId(String corpId) {
		this.corpId = corpId;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public ExcelKefu setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getNickname() {
		return nickname;
	}

	public ExcelKefu setNickname(String nickname) {
		this.nickname = nickname;
		return this;
	}

	public String getName() {
		return name;
	}

	public ExcelKefu setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String toString() {
		return "ExcelKefu{" + "corpName='" + corpName + '\'' + ", businessName='" + businessName + '\'' + ", corpId='"
				+ corpId + '\'' + ", username='" + username + '\'' + ", nickname='" + nickname + '\'' + ", maxDialog='"
				+ maxDialog + '\'' + ", name='" + name + '\'' + ", excelKefu=" + excelKefu.corpId + '}';
	}
}
