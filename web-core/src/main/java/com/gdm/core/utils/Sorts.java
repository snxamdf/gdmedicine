/*
 * 
 *
 * 
 */
package com.gdm.core.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

import com.gdm.core.constants.DOMAIN;
import com.gdm.core.search.Filter;
import com.google.common.collect.Lists;

/**
 * 查询排序工具类.
 * 
 * @author YHY
 * @version 2015-01-14
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-14
 */
public class Sorts {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(Sorts.class);

	public static Sort byMap(final Map<String, Object> params) {
		return byFilter(Filter.parse(params).values());
	}

	public static Sort byFilter(final Filter... filters) {
		List<Filter> fs = Lists.newArrayList(filters);
		return byFilter(fs);
	}

	public static Sort byFilter(final Collection<Filter> filters) {
		Sort sort = null;
		for (Filter filter : filters) {
			switch (filter.operator) {
			case AC:
				if (sort == null) {
					sort = new Sort(Sort.Direction.ASC, filter.fieldName);
				} else {
					sort = sort.and(new Sort(Sort.Direction.ASC, filter.fieldName));
				}
				break;
			case DC:
				if (sort == null) {
					sort = new Sort(Sort.Direction.DESC, filter.fieldName);
				} else {
					sort = sort.and(new Sort(Sort.Direction.DESC, filter.fieldName));
				}
				break;
			default:
				break;
			}
		}
		if (sort == null) {
			sort = new Sort(Sort.Direction.DESC, DOMAIN.MODIFIED);
		}
		return sort;
	}

}
