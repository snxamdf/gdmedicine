/* 
 * 
 *
 * 
 */
package com.gdm.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类.
 * 
 * @author YHY
 * @version 2013-05-15
 */
public abstract class Strings extends StringUtils {

	/**
	 * 转换为驼峰式字符串
	 */
	public static String camel(String inputString) {
		return camel(inputString, false);
	}

	/**
	 * 文本转换为html
	 */
	public static String text2html(String inputString) {
		inputString = inputString.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
		inputString = inputString.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		inputString = inputString.replaceAll(" ", "&nbsp;");
		return inputString;
	}

	/**
	 * 转换为驼峰式字符串
	 */
	public static String camel(String inputString, boolean firstCharacterUppercase) {
		StringBuilder sb = new StringBuilder();

		boolean nextUpperCase = false;
		for (int i = 0; i < inputString.length(); i++) {
			char c = inputString.charAt(i);

			switch (c) {
			case '_':
			case '-':
			case '@':
			case '$':
			case '#':
			case ' ':
			case '/':
			case '&':
				if (sb.length() > 0) {
					nextUpperCase = true;
				}
				break;

			default:
				if (nextUpperCase) {
					sb.append(Character.toUpperCase(c));
					nextUpperCase = false;
				} else {
					sb.append(Character.toLowerCase(c));
				}
				break;
			}
		}

		if (firstCharacterUppercase) {
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		}

		return sb.toString();
	}

	/**
	 * 驼峰式字符串反向转换
	 */
	public static String unCamel(String inputString) {
		return unCamel(inputString, false);
	}

	/**
	 * 驼峰式字符串反向转换
	 */
	public static String unCamel(String inputString, boolean uppercase) {
		return unCamel(inputString, "_", uppercase);
	}

	/**
	 * 驼峰式字符串反向转换
	 */
	public static String unCamel(String inputString, String separator, boolean uppercase) {
		StringBuilder sb = new StringBuilder();

		if (isBlank(separator)) {
			separator = "_";
		}

		for (int i = 0; i < inputString.length(); i++) {
			char c = inputString.charAt(i);

			if (c >= 65 && c <= 90 && i > 0) {
				sb.append(separator);
			}
			sb.append(c);
		}

		if (uppercase) {
			return sb.toString().toUpperCase();
		}

		return sb.toString().toLowerCase();
	}

	/**
	 * 替换掉HTML标签
	 */
	public static String replaceHtml(String html) {
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * 
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		int currentLength = 0;
		for (char c : str.toCharArray()) {
			currentLength += String.valueOf(c).length();
			if (currentLength <= length - 3) {
				sb.append(c);
			} else {
				sb.append("...");
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 缩略字符串（区分中英文字符,汉字在页面占两个长度位置）
	 * 
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbrZh(String str, int length) {
		if (str == null) {
			return "";
		}
		//汉字正则
		Pattern pattern = Pattern.compile("[\u4E00-\u9FA5]");
		Matcher matc = null;
		StringBuilder sb = new StringBuilder();
		int currentLength = 0;
		for (char c : str.toCharArray()) {
			matc = pattern.matcher(String.valueOf(c));
			if (matc.find()) {
				currentLength += String.valueOf(c).length() + 1;
			} else {
				currentLength += String.valueOf(c).length();
			}
			if (currentLength <= length - 3) {
				sb.append(c);
			} else {
				sb.append("...");
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 手机匿名显示.
	 * Assert.assertEquals("123**6789", Strings.anonMobile("123456789"));
	 * Assert.assertEquals("135****8888", Strings.anonMobile("13566668888"));
	 * Assert.assertEquals("aB字****测试cD", Strings.anonMobile("aB字符串截取测试cD"));
	 * 
	 * @param in 手机号
	 * @return
	 * @author YHY
	 * @version 2013-12-09
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2013-12-09
	 */
	public static String anonMobile(String mobile) {
		return anonymous(mobile, 3, 4, 0);
	}

	/**
	 * 选取头尾各一个字符，中间用3个*号代替
	 */
	public static String anonNameThree(String name) {
		return anonymous(name, 1, 1, 3);
	}

	/**
	 * 手机匿名显示.
	 * Assert.assertEquals("1*******9", Strings.anonName("123456789"));
	 * Assert.assertEquals("1*********8", Strings.anonName("13566668888"));
	 * Assert.assertEquals("a*********D", Strings.anonName("aB字符串截取测试cD"));
	 * 
	 * @param in 手机号
	 * @return
	 * @author YHY
	 * @version 2013-12-09
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2013-12-09
	 */
	public static String anonName(String name) {
		return anonymous(name, 1, 1, 0);
	}

	/**
	 * 字符匿名显示.
	 * Assert.assertEquals("1*******9", Strings.anonymous("123456789", 1, 1));
	 * Assert.assertEquals("135****8888", Strings.anonymous("13566668888", 3, 4));
	 * Assert.assertEquals("aB字****测试cD", Strings.anonymous("aB字符串截取测试cD", 3, 4));
	 * 
	 * @param in 输入字符
	 * @param leftLen 左边显示明文字符长度
	 * @param rightLen 右边显示明文字符长度
	 * @param starLen 指定*的长度
	 * @return
	 * @author YHY
	 * @version 2013-12-09
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2013-12-09
	 */
	public static String anonymous(String in, int leftLen, int rightLen, int starLen) {
		if (StringUtils.isBlank(in)) {
			return "";
		}
		if (leftLen < 1) {
			leftLen = 1;
		}
		if (rightLen < 1) {
			leftLen = 1;
		}
		in = StringUtils.trim(in);
		StringBuilder sb = new StringBuilder();
		int loop = 0;
		for (int i = 0; i < in.length(); i++) {
			if (i < leftLen || i >= in.length() - rightLen) {
				sb.append(in.charAt(i));
			} else {
				if (starLen > 0) {
					if (loop <= starLen) {
						sb.append("*");
					}
				} else {
					sb.append("*");
				}
			}
			loop++;
		}
		return sb.toString();
	}

}
