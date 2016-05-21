/*
 * 
 *
 * 
 */
package com.gdm.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;

/**
 * Page工具类.
 * 
 * @author YHY
 * @version 2014-08-19
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-08-19
 */
public abstract class Pages {

	private final static int first = 1;// 首页索引
	private final static int length = 8;// 显示页面长度
	private final static int slider = 1;// 前后显示页面长度

	/**
	 * 根据page生成分页html代码.
	 * 
	 * @param page
	 * @return
	 * @author YHY
	 * @version 2014-08-19
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014-08-19
	 */
	public static String toHtml(Page<?> page) {
		return toHtml(page, "page");
	}

	/**
	 * 根据page生成分页html代码.
	 * 
	 * @param page
	 * @param funcName 设置点击页码调用的js函数名称，默认为page，在一页有多个分页对象时使用
	 * @return
	 * @author YHY
	 * @version 2014-08-19
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014-08-19
	 */
	public static String toHtml(Page<?> page, String funcName) {
		if (StringUtils.isBlank(funcName)) {
			funcName = "page"; // 设置点击页码调用的js函数名称，默认为page，在一页有多个分页对象时使用
		}

		StringBuilder sb = new StringBuilder();
		int pageNo = page.getNumber() + 1;
		int pageSize = page.getSize();
		long count = page.getTotalElements(); // 总记录数
		int prev = page.hasPrevious() ? page.getNumber() : first; // 上一页索引
		int next = page.hasNext() ? pageNo + 1 : pageNo; // 下一页索引
		int last = (int) (count / pageSize); // 尾页索引
		if (count % pageSize != 0 || last == 0) {
			last++;
		}
		if (last < first) {
			last = first;
		}

		// 上一页
		if (pageNo == first) {// 如果当前页是首页
			sb.append("<a class='disabled icon item'><i class='left arrow icon'></i></a>\n");
		} else {
			sb.append("<a class='icon item' href='javascript:" + funcName + "(" + prev + "," + pageSize + ");'><i class='left arrow icon'></i></a>\n");
		}

		int begin = pageNo - (length / 2);

		if (begin < first) {
			begin = first;
		}

		int end = begin + length - 1;

		if (end >= last) {
			end = last;
			begin = end - length + 1;
			if (begin < first) {
				begin = first;
			}
		}

		// ...和之前的页
		if (begin > first) {
			int i = 0;
			for (i = first; i < first + slider && i < begin; i++) {
				sb.append("<a class='item' href='javascript:" + funcName + "(" + i + "," + pageSize + ");'>" + (i + 1 - first) + "</a>\n");
			}
			if (i < begin) {
				sb.append("<div class='disabled item'>...</div>\n");
			}
		}

		for (int i = begin; i <= end; i++) {
			if (i == pageNo) {
				sb.append("<a class='active item' href='javascript:'>" + (i + 1 - first) + "</a>\n");
			} else {
				sb.append("<a class='item' href='javascript:" + funcName + "(" + i + "," + pageSize + ");'>" + (i + 1 - first) + "</a>\n");
			}
		}

		if (last - end > slider) {
			sb.append("<div class='disabled item'>...</div>\n");
			end = last - slider;
		}

		// ...之后的页
		for (int i = end + 1; i <= last; i++) {
			sb.append("<a class='item' href='javascript:" + funcName + "(" + i + "," + pageSize + ");'>" + (i + 1 - first) + "</a>\n");
		}

		// 下一页
		if (pageNo == last) {
			sb.append("<a class='disabled icon item'><i class='right arrow icon'></i></a>\n");
		} else {
			sb.append("<a class='icon item' href='javascript:" + funcName + "(" + next + "," + pageSize + ");'><i class='right arrow icon'></i></a>\n");
		}

		// 手动跳转页
		sb.append("<div class='disabled item'>");
		sb.append("<div class='mt-5 mb-5'>当前 ");
		sb.append("<input class='w40' type='text' value='" + pageNo + "'");
		sb.append("onkeydown='var e=e||event;var c=e.keyCode||e.which||e.charCode;if(c==13)" + funcName + "(this.value," + pageSize + ");' /> / ");
		sb.append("<input class='w40' type='text' value='" + pageSize + "'");
		sb.append("onkeydown='var e=e||event;var c=e.keyCode||e.which||e.charCode;if(c==13)" + funcName + "(" + pageNo + ",this.value);' /> 条");
		sb.append("，共 " + count + " 条</div></div>");

		return sb.toString();
	}

	/**
	 * 根据page生成Web分页html代码.
	 * 
	 * @param page
	 * @return
	 * @author YHY
	 * @version 2015-01-24
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-01-24
	 */
	public static String toWebHtml(Page<?> page) {
		return toWebHtml(page, "page");
	}

	/**
	 * 根据page生成Web分页html代码.
	 * 
	 * @param page
	 * @param funcName 设置点击页码调用的js函数名称，默认为page，在一页有多个分页对象时使用
	 * @return
	 * @author YHY
	 * @version 2015-01-24
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-01-24
	 */
	public static String toWebHtml(Page<?> page, String funcName) {
		if (StringUtils.isBlank(funcName)) {
			funcName = "page"; // 设置点击页码调用的js函数名称，默认为page，在一页有多个分页对象时使用
		}

		StringBuilder sb = new StringBuilder();
		int pageNo = page.getNumber() + 1;
		int pageSize = page.getSize();
		long count = page.getTotalElements(); // 总记录数
		//int prev = page.hasPrevious() ? page.getNumber() : first; // 上一页索引
		//int next = page.hasNext() ? pageNo + 1 : pageNo; // 下一页索引
		int last = (int) (count / pageSize); // 尾页索引
		if (count % pageSize != 0 || last == 0) {
			last++;
		}
		if (last < first) {
			last = first;
		}

		// 首页

		if (pageNo == first) { // 如果当前页是首页
			sb.append("<a href=\"javascript:\" class=\"pageHome\"><span><</span>首页</a>\n");
		} else {
			sb.append("<a href=\"javascript:" + funcName + "(1," + pageSize + ");\"><span><</span>首页</a>\n");
		}

		int begin = pageNo - (length / 2);

		if (begin < first) {
			begin = first;
		}

		int end = begin + length - 1;

		if (end >= last) {
			end = last;
			begin = end - length + 1;
			if (begin < first) {
				begin = first;
			}
		}

		// ...和之前的页
		if (begin > first) {
			int i = 0;
			for (i = first; i < first + slider && i < begin; i++) {
				sb.append("<a href=\"javascript:" + funcName + "(" + i + "," + pageSize + ");\">" + (i + 1 - first) + "</a>\n");
			}
			if (i < begin) {
				sb.append("<a href=\"javascript:\">...</a>\n");
			}
		}

		for (int i = begin; i <= end; i++) {
			if (i == pageNo) {
				sb.append("<a href=\"javascript:\" class=\"curr\">" + (i + 1 - first) + "</a>\n");
			} else {
				sb.append("<a href=\"javascript:" + funcName + "(" + i + "," + pageSize + ");\">" + (i + 1 - first) + "</a>\n");
			}
		}

		if (last - end > slider) {
			sb.append("<a href=\"javascript:\">...</a>\n");
			end = last - slider;
		}

		// ...之后的页
		for (int i = end + 1; i <= last; i++) {
			sb.append("<a href=\"javascript:" + funcName + "(" + i + "," + pageSize + ");\">" + (i + 1 - first) + "</a>\n");
		}

		// 尾页
		if (pageNo == last) {
			sb.append("<a href=\"javascript:\" class=\"pageLast\">尾页<span>></span></a>\n");
		} else {
			sb.append("<a href=\"javascript:" + funcName + "(" + last + "," + pageSize + ");\" class=\"pageLast\">" + "尾页<span>></span></a>\n");
		}

		// 手动跳转页
		sb.append("<span>共" + last + "页</span>\n");
		sb.append("<span>转到</span>\n");
		sb.append("<input id=\"" + funcName + "PageNo\" type=\"text\" value=\"" + pageNo + "\" onkeydown=\"var e=e||event;var c=e.keyCode||e.which||e.charCode;if(c==13)");
		sb.append(funcName + "(this.value," + pageSize + ");\" onclick=\"this.select();\"/>");
		sb.append("<a class=\"pageBtn\" href=\"javascript:" + funcName + "($('#" + funcName + "PageNo').val()," + pageSize + ");\">确定</a>\n");

		return sb.toString();
	}

}
