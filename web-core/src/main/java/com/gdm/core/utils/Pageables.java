/*
 * 
 *
 * 
 */
package com.gdm.core.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * Pageable工具类.
 * 
 * @author YHY
 * @version 2014-07-18
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-07-18
 */
public abstract class Pageables {

	private final static int PAGE = 1;
	private final static int SIZE = 10;
	private final static String PAGE_NO = "pageNo";
	private final static String PAGE_SIZE = "pageSize";
	private final static String REPAGE = "repage";
	private final static String RE_PAGE_NO = "rePageNo";

	public static Pageable Build(int page) {
		return new PageRequest(page - 1, SIZE);
	}

	public static Pageable Build(int page, int size) {
		return new PageRequest(page - 1, size);
	}

	public static Pageable Build(Sort sort) {
		return new PageRequest(PAGE, SIZE, sort);
	}

	public static Pageable Build(int page, int size, Sort sort) {
		return new PageRequest(page - 1, size, sort);
	}

	public static Pageable Build(Direction direction, String... properties) {
		return new PageRequest(PAGE, SIZE, direction, properties);
	}

	public static Pageable Build(int page, int size, Direction direction, String... properties) {
		return new PageRequest(page - 1, size, direction, properties);
	}

	public static Pageable Build(HttpServletRequest request, HttpServletResponse response) {
		int pageNo = getAndSet(request, response, PAGE_NO);
		int pageSize = getAndSet(request, response, PAGE_SIZE);
		return new PageRequest(pageNo - 1, pageSize);
	}

	public static Pageable Build(HttpServletRequest request, HttpServletResponse response, Sort sort) {
		int pageNo = getAndSet(request, response, PAGE_NO);
		int pageSize = getAndSet(request, response, PAGE_SIZE);
		return new PageRequest(pageNo - 1, pageSize, sort);
	}

	public static Pageable Build(HttpServletRequest request, HttpServletResponse response, int size) {
		int pageNo = getAndSet(request, response, PAGE_NO);
		if (size < 1) {
			return new PageRequest(pageNo - 1, SIZE);
		} else {
			return new PageRequest(pageNo - 1, size);
		}
	}

	public static Pageable Build(HttpServletRequest request, HttpServletResponse response, int size, Sort sort) {
		int pageNo = getAndSet(request, response, PAGE_NO);
		if (size < 1) {
			return new PageRequest(pageNo - 1, SIZE, sort);
		} else {
			return new PageRequest(pageNo - 1, size, sort);
		}
	}

	public static Pageable Build(HttpServletRequest request, HttpServletResponse response, Direction direction, String... properties) {
		int pageNo = getAndSet(request, response, PAGE_NO);
		int pageSize = getAndSet(request, response, PAGE_SIZE);
		return new PageRequest(pageNo - 1, pageSize, direction, properties);
	}

	private static int getAndSet(HttpServletRequest request, HttpServletResponse response, String name) {
		int result = 0;
		// 设置分页参数（传递repage参数，记住分页参数）
		String val = request.getParameter(name);
		if (StringUtils.isNumeric(val)) {
			Cookies.setCookie(response, name, val);
			result = Integer.parseInt(val);
		} else if (request.getParameter(REPAGE) != null) {
			val = Cookies.getCookie(request, name);
			if (StringUtils.isNumeric(val)) {
				result = Integer.parseInt(val);
			}
		} else {
			if (StringUtils.isBlank(val)) {
				val = PAGE_NO.equals(name) ? String.valueOf(PAGE) : String.valueOf(SIZE);
				Cookies.setCookie(response, name, val);
				result = Integer.parseInt(val);
			}
		}
		if (PAGE_NO.equals(name)) {
			if (result < PAGE) {
				result = PAGE;
			} else {
				// 当rePageNo=true时，设置pageNo=1
				String rePageNo = request.getParameter(RE_PAGE_NO);
				if ("true".equals(rePageNo)) {
					result = PAGE;
				}
			}
		}
		return result;
	}

}
