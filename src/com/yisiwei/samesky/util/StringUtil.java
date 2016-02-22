package com.yisiwei.samesky.util;

public class StringUtil {

	public StringUtil() {

	}

	/**
	 * 判断字符串是否为null或空
	 * 
	 * @param string
	 * @return true:为 空或null;false:不为 空或null
	 */
	public static boolean isNullOrEmpty(String string) {
		boolean flag = false;
		if (null == string || string.trim().length() == 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 手机号中间4位以*代替
	 * 
	 * @param phone
	 * @return
	 */
	public static String replacePhone(String phone) {
		String str1 = phone.substring(0, 3);
		String str2 = phone.substring(7, phone.length());
		return str1 + "****" + str2;
	}

	/**
	 * 身份证号中间8位以*代替
	 * 
	 * @param phone
	 * @return
	 */
	public static String replaceCard(String card) {
		return card.replace(card.substring(6, 14), "********");
	}

}
