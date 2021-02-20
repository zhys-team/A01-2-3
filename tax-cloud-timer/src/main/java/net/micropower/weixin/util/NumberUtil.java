/**
 * Copyright (c) 2011 Yunling Techology
 * All rights reserved.
 *
 */
package net.micropower.weixin.util;

import java.util.regex.Pattern;

/**
 * @author lin.jiashu
 * 
 */
public class NumberUtil
{
	// 正则表达式判断是否是数字
	public static boolean isNumeric(String str)
	{
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
}
