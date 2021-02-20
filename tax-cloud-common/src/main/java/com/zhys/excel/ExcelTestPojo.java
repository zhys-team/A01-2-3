package com.zhys.excel;

public class ExcelTestPojo {

	@ExcelCell(name="字符串1",index=0)
	private String str;
	
	@ExcelCell(name="字符串2",index=1)
	private String str2;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}
}
