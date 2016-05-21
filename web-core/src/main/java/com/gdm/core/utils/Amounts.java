/*
 * 
 *
 * 
 */
package com.gdm.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * 金额工具类.
 * 
 * @author YHY
 * @version 2013年12月24日
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2013年12月24日
 */
public abstract class Amounts {

	// 默认运算精度  
	private static final Integer DEF_SCALE = 2;

	/**
	 * 提供精确的加法运算.
	 * 
	 * @param values 被加数
	 * @Example Assert.assertEquals("13.21", Amounts.add(12.11, 1.10).toString());
	 * @Example Assert.assertEquals("14.41", Amounts.add(12.11, 1.10, 1.2).toString());
	 * @return 参数之和
	 * @author YHY
	 * @version 2014年2月13日
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014年2月13日
	 */
	public static Double add(Number... values) {
		BigDecimal bd = new BigDecimal(0d);
		for (Number value : values) {
			if (value == null) {
				continue;
			}
			BigDecimal bdn = new BigDecimal(Double.toString(value.doubleValue()));
			bd = bd.add(bdn);
		}
		return bd.doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param values 被减数
	 * @Example Assert.assertEquals("0.9", Amounts.sub(2.00, 1.10).toString());
	 * @Example Assert.assertEquals("0.6", Amounts.sub(2.00, 1.10, 0.3).toString());
	 * @return 参数之差
	 * @author YHY
	 * @version 2014年2月13日
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014年2月13日
	 */
	public static Double sub(Number... values) {
		BigDecimal bd = new BigDecimal(0d);
		for (int i = 0; i < values.length; i++) {
			if (values[i] == null) {
				continue;
			}
			BigDecimal bdn = new BigDecimal(Double.toString(values[i].doubleValue()));
			if (i == 0) {
				bd = bdn;
			} else {
				bd = bd.subtract(bdn);
			}
		}
		return bd.doubleValue();
	}

	/**
	 * 提供精确的乘法运算.
	 * 
	 * @param values 被乘数
	 * @Example Assert.assertEquals("0.11", Amounts.mul(0.1, 1.1).toString());
	 * @Example Assert.assertEquals("0.022", Amounts.mul(0.1, 1.1, 0.2).toString());
	 * @return 参数之积
	 * @author YHY
	 * @version 2014年2月13日
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014年2月13日
	 */
	public static Double mul(Number... values) {
		BigDecimal bd = new BigDecimal(0d);
		for (int i = 0; i < values.length; i++) {
			if (values[i] == null) {
				continue;
			}
			BigDecimal bdn = new BigDecimal(Double.toString(values[i].doubleValue()));
			if (i == 0) {
				bd = bdn;
			} else {
				bd = bd.multiply(bdn);
			}
		}
		return bd.doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后2位，以后的数字四舍五入.
	 * 
	 * @param dividend 被除数
	 * @param divisor 除数
	 * @Example Assert.assertEquals("3.0", Amounts.div(0.6, 0.2).toString());
	 * @Example Assert.assertEquals("3.33", Amounts.div(0.6666, 0.2).toString());
	 * @return 两个参数的商
	 * @author YHY
	 * @version 2014年2月13日
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014年2月13日
	 */
	public static Double div(Number dividend, Number divisor) {
		return div(dividend, divisor, DEF_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算， 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入.
	 * 
	 * @param dividend 被除数
	 * @param divisor 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @Example Assert.assertEquals("3.333", Amounts.div(0.6666, 0.2, 3).toString());
	 * @return 两个参数的商
	 * @author YHY
	 * @version 2014年2月13日
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014年2月13日
	 */
	public static Double div(Number dividend, Number divisor, Integer scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(dividend.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(divisor.doubleValue()));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理，小数点保留两位.
	 * 
	 * @param amount 需要四舍五入的数字
	 * @Example Assert.assertTrue(16.59d == Amounts.round(16.5933));
	 * @Example Assert.assertTrue(16.58d == Amounts.round(16.5787));
	 * @Example Assert.assertTrue(16.60d == Amounts.round(16.5997));
	 * @Example Assert.assertTrue(16.50d == Amounts.round(16.5));
	 * @return 四舍五入后的结果
	 * @author YHY
	 * @version 2014年2月13日
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014年2月13日
	 */
	public static Double round(Number value) {
		return round(value, DEF_SCALE);
	}

	/**
	 * 提供精确的小数位四舍五入处理.
	 * 
	 * @param value 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @Example Assert.assertTrue(16.593d == Amounts.round(16.5933, 3));
	 * @Example Assert.assertTrue(16.589d == Amounts.round(16.5887, 3));
	 * @Example Assert.assertTrue(16.600d == Amounts.round(16.5997, 3));
	 * @Example Assert.assertTrue(16.500d == Amounts.round(16.5, 3));
	 * @return 四舍五入后的结果
	 * @author YHY
	 * @version 2014年2月13日
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014年2月13日
	 */
	public static Double round(Number value, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal bd = new BigDecimal(Double.toString(value.doubleValue()));
		return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五舍处理，小数点保留两位.
	 * 
	 * @param value 需要四舍五舍的数字
	 * @Example Assert.assertTrue(16.59d == Amounts.scale(16.5933));
	 * @Example Assert.assertTrue(16.57d == Amounts.scale(16.5787));
	 * @Example Assert.assertTrue(16.50d == Amounts.scale(16.5));
	 * @return 四舍五舍后的结果
	 * @author YHY
	 * @version 2013年12月24日
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2013年12月24日
	 */
	public static Double scale(Number value) {
		return scale(value, DEF_SCALE);
	}

	/**
	 * 提供精确的小数位四舍五舍处理.
	 * 
	 * @param value 需要四舍五舍的数字
	 * @param scale 小数点后保留几位
	 * @Example Assert.assertTrue(16.593d == Amounts.scale(16.5933, 3));
	 * @Example Assert.assertTrue(16.588d == Amounts.scale(16.5887, 3));
	 * @Example Assert.assertTrue(16.500d == Amounts.scale(16.5, 3));
	 * @return 四舍五舍后的结果
	 * @author YHY
	 * @version 2013年12月24日
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2013年12月24日
	 */
	public static Double scale(Number value, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal bd = new BigDecimal(Double.toString(value.doubleValue()));
		return bd.setScale(scale, BigDecimal.ROUND_FLOOR).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五舍处理，返回BigDecimal.
	 * 
	 * @param value 需要四舍五舍的数字
	 * @param scale 小数点后保留几位
	 * @Example Assert.assertTrue(16.593d == Amounts.scale(16.5933, 3));
	 * @Example Assert.assertTrue(16.588d == Amounts.scale(16.5887, 3));
	 * @Example Assert.assertTrue(16.500d == Amounts.scale(16.5, 3));
	 * @return 四舍五舍后的结果
	 * @author YHY
	 * @version 2013年12月24日
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2013年12月24日
	 */
	public static BigDecimal scaleBigDecimal(Number value, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal bd = new BigDecimal(Double.toString(value.doubleValue()));
		return bd.setScale(scale, BigDecimal.ROUND_FLOOR);
	}

	/**
	 * 格式化数字.
	 * 小数点保留两位,四舍五舍,三位一级用逗号分割.
	 * 
	 * @param value 需要格式化的数字
	 * @Example Assert.assertEquals("1,664,546,466,200.59", Amounts.format(1664546466200.5933));
	 * @Example Assert.assertEquals("1,664,546,466,200.57", Amounts.format(1664546466200.5787));
	 * @Example Assert.assertEquals("1,664,546,466,200.5", Amounts.format(1664546466200.5));
	 * @return
	 * @author YHY
	 * @version 2013年12月24日
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2013年12月24日
	 */
	public static String format(double value) {
		return format(value, DEF_SCALE);
	}

	/**
	 * 格式化数字.
	 * 四舍五舍,三位一级用逗号分割.
	 * 
	 * @param amount 需要格式化的数字
	 * @param scale 小数点保留位数
	 * @Example Assert.assertEquals("1,664,546,466,200.593", Amounts.format(1664546466200.5933, 3));
	 * @Example Assert.assertEquals("1,664,546,466,200.578", Amounts.format(1664546466200.5787, 3));
	 * @Example Assert.assertEquals("1,664,546,466,200.5", Amounts.format(1664546466200.5, 3));
	 * @return
	 * @author YHY
	 * @version 2013年12月24日
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2013年12月24日
	 */
	public static String format(double value, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		NumberFormat nsf = NumberFormat.getInstance();
		nsf.setMaximumFractionDigits(scale);
		nsf.setRoundingMode(RoundingMode.FLOOR);
		return nsf.format(value);
	}

	public static String format(BigDecimal value, int scale) {
		return Amounts.format(value.doubleValue(), scale);
	}

	/**
	 * Number类型转化为BigDecimal.
	 * 
	 * @param value 需要类型转化的数字
	 * @Example Assert.assertEquals("1.0", Amounts.toBigDecimal(1).toString());
	 * @Example Assert.assertEquals("0.011", Amounts.toBigDecimal(0.011d).toString());
	 * @return 类型转化后的BigDecimal
	 * @author YHY
	 * @version 2014年2月13日
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014年2月27日
	 */
	public static BigDecimal toBigDecimal(Number value) {
		return new BigDecimal(Double.toString(value.doubleValue()));
	}

	/**
	 * 比较两个BigDecimal大小
	 * -1:b1小于b2 0:b1 等于b2 1:b1大于b2.
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 * @author sdh
	 * @version 2014-7-25
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by sdh
	 * @updated at 2014-7-25
	 */
	public static int compareTo(BigDecimal b1, BigDecimal b2) {
		return b1.compareTo(b2);
	}

	/**
	 * 比较是否大于0.
	 * -1:b小于0 0:b等于0 1:b大于0.
	 * 
	 * @param b
	 * @return
	 * @author sdh
	 * @version 2015-01-13
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by sdh
	 * @updated at 2015-01-13
	 */
	public static int compareTo(BigDecimal b) {
		return b.compareTo(BigDecimal.ZERO);
	}

	/**
	 * 比较两个Double大小
	 * -1:b1小于b2 0:b1 等于b2 1:b1大于b2.
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 * @author sdh
	 * @version 2014-7-25
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by sdh
	 * @updated at 2014-7-25
	 */
	public static int compareTo(Double d1, Double d2) {
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return compareTo(b1, b2);
	}

	/**
	 * 比较是否大于0.
	 * -1:d小于0 0:d等于0 1:d大于0.
	 * 
	 * @param b
	 * @return
	 * @author sdh
	 * @version 2015-01-13
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by sdh
	 * @updated at 2015-01-13
	 */
	public static int compareTo(Double d) {
		BigDecimal b = new BigDecimal(d);
		return b.compareTo(BigDecimal.ZERO);
	}

	/**
	 * 获取绝对值.
	 * 
	 * @param d
	 * @return
	 * @author sdh
	 * @version 2015-01-14
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by sdh
	 * @updated at 2015-01-14
	 */
	public static Double abs(Double d) {
		return Math.abs(d);
	}

}
