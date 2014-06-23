/*
 * 文件名：StringUtil.java
 * 版权：Copyright 2006-2011 AsiaInfo Tech. Co. Ltd. All Rights Reserved. 
 * 描述： StringUtil.java
 * 修改人：齐鹏飞
 * 修改时间：2011-8-12
 * 修改内容：新增
 */
package com.gaokaoyizhantong.dpstools;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 * <p>
 * <p>
 * 
 * <pre>
 * </pre>
 * 
 * @author 齐鹏飞
 * @version CTMS V100R001 2011-8-12
 * @since CTMS V100R001C01
 */
public final class StringUtil {

	/**
	 * yyyy-MM-dd日期字符串格式匹配器
	 */
	public static final Pattern YYYY_MM_DD = Pattern
			.compile("^((((1[6-9]|[2-9]\\d)\\d{2})-(0[13578]|1[02])-(0[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0[13456789]|1[012])-(0[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-02-(0[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-02-29))$");

	/**
	 * dd-mm-yyyy日期字符串格式匹配器
	 */
	public static final Pattern DD_MM_YYYY = Pattern
			.compile("^(((0[1-9]|[12]\\d|3[01])-(0[13578]|1[02])-((1[6-9]|[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)-(0[13456789]|1[012])-((1[6-9]|[2-9]\\d)\\d{2}))|((0[1-9]|1\\d|2[0-8])-02-((1[6-9]|[2-9]\\d)\\d{2}))|(29-02-((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$");

	/**
	 * 构造函数。
	 */
	private StringUtil() {
	}

	/**
	 * 
	 * String字符串null “”判断
	 * 
	 * @param str
	 *            要判断的字符串
	 * @return 如果为null或“”，则返回true
	 */
	public static boolean isNull(String str) {
		boolean b = str == null || str.trim().equals("");

		return b;
	}

	/**
	 * 将null转为入参指定的arg2
	 * 
	 * @param arg
	 * @param arg2
	 * @return
	 */
	public static String nullConvert(String arg, String arg2) {
		return isNull(arg) ? arg2 : arg;
	}

	/**
	 * 
	 * 校验给定字符串是否为整数序列。 校验字符串是否为指定长度内(小于等于)的整数序列
	 * 
	 * @param str
	 *            要校验的字符串
	 * @param length
	 *            指定的长度
	 * @return 如果为符合指定长度的整数序列返回true，否则为false。 <br/>
	 *         注意：如果要校验的字符串为空，将会出现空指针异常。
	 */
	public static boolean isNumber(String str, int length) {
		Pattern p = Pattern.compile("\\d{1," + length + "}");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 
	 * 判断时间字符串的格式是否符合指定的格式匹配。
	 * 
	 * @param time
	 *            时间字符串
	 * @param format
	 *            格式匹配
	 * @return boolean true/false.
	 */
	public static boolean isTimeFormat(String time, Pattern format) {
		Matcher matcher = format.matcher(time);
		return matcher.matches();
	}

	/**
	 * 
	 * 判断时间字符串的格式是否符合yyyy-MM-dd格式。
	 * 
	 * @param time
	 *            时间字符串
	 * @return boolean true/false.
	 */
	public static boolean isTimeFormat(String time) {
		return isTimeFormat(time, YYYY_MM_DD);
	}

	/**
	 * 
	 * 正则表达式验证
	 * 
	 * @param str
	 *            验证字符串
	 * @param regex
	 *            正则表达式
	 * @return boolean
	 */
	public static boolean matcher(String str, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 如果Sring为null，则返回空字符串
	 * 
	 * @param content
	 *            内容
	 * @return 返回转换后内容
	 */
	public static String transformNull(String content) {
		return (content == null) ? "" : content;
	}

	public static String getSpliteStringByList(List list) {
		String returnString = "";

		for (int i = 0; i < list.size(); i++) {
			returnString += "'" + list.get(i) + "',";
		}

		if (returnString.length() == 0) {
			return returnString;
		}

		return returnString.substring(0, returnString.length() - 1);
	}

	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	public static byte[] parseHex2Byte(String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		} else {
			byte[] result = new byte[hexStr.length() / 2];
			for (int i = 0; i < hexStr.length() / 2; i++) {
				int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1),
						16);
				int low = Integer.parseInt(
						hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
				result[i] = (byte) (high * 16 + low);
			}
			return result;
		}
	}

	public static String subString(String src, String start, String end) {
		if (src == null || src.equals(""))
			return src;
		int startIndex = 0;
		int endIndex = src.length() - 1;
		if (start != null && !start.equals("")) {
			startIndex = src.indexOf(start) == -1 ? 0 : src.indexOf(start)
					+ start.length();
		}
		if (end != null && !end.equals("")) {
			endIndex = src.indexOf(end, startIndex) == -1 ? src.length() - 1
					: src.indexOf(end, startIndex);
		}
		return src.substring(startIndex, endIndex);
	}

	public static void main(String[] args) {
		String str = "<table border=\"0\" width=\"100%\" id=\"table2\" cellpadding=\"0\" >	<tr>		<td colspan=\"3\"><strong>2013年内蒙古考生报考热度</strong>		</td>	</tr>	<tr>		<td colspan=\"3\"><img  width =\"54\" height=\"8\" src=\"/images/bk_hot_red.gif\"><img  width =\"36\" height=\"8\" src=\"/images/bk_hot_grey.gif\"/>136%</td>			</tr>	<tr>		<td colspan=\"3\"><strong>计划招收人数：14人<br>实际报考人数：19人<strong>	</td></tr>		<tr>		<td><img  width =\"50\" height=\"8\" src=\"/images/bk_hot_blue.gif\"><br>没有招满</td>		<td><img  width =\"50\" height=\"8\" src=\"/images/bk_hot_red.gif\"><br>已经招满</td>		<td><img  width =\"50\" height=\"8\" src=\"/images/bk_hot_hot.gif\"><br>超热门</td>	</tr></table>";
		System.out.println(subString(str, "<td colspan=\"3\"><strong>计划招收人数",
				"<strong>"));
	}
}
