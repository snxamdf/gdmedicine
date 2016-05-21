/*
 * 
 *
 * 
 */
package com.yhy.core.search;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.yhy.core.constants.SYMBOL;
import com.google.common.collect.Maps;

/**
 * 查询过滤类.
 * 
 * @author YHY
 * @version 2014-08-08
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-08-08
 */
public class Filter {

	public static final String DELETION = "EQ_deletion"; // 逻辑删除查询条件名
	public static final String GENRE = "EQ_genre"; // 类型查询条件名
	public static final String PARENT_ID = "EQ_parentId"; // 父节点查询条件名
	public static final String ORDINAL = "AC_ordinal"; // 父节点查询条件名

	private static final String NULL = "_filter_nil_"; // 默认零值

	/**
	 * EQ : equal( = ) NE : not equal( <> ) GT : greater than( > ) LT : less
	 * than( < ) GE : greater than or equal( >= ) LE : less than or equal( <= )
	 * LI : like '?' LK : like '%?%' LL : like '%?' LR : like '?%' BE : between
	 * 'a,b' IN : in 'a,b,c,d' NU : is null NN : is not null AC : asc(order by)
	 * DC : desc(order by)
	 */
	public enum Operator {
		EQ, NE, GT, LT, GE, LE, LI, LK, LL, LR, BE, IN, NU, NN, AC, DC
	}

	public String fieldName;
	public Object value;
	public Operator operator;

	/**
	 * 只用于 NU : is null NN : is not null AC : asc(order by) DC : desc(order by)
	 * 
	 * @param fieldName
	 * @param operator
	 */
	public Filter(String fieldName, Operator operator) {
		this.fieldName = fieldName;
		this.value = Filter.NULL;
		this.operator = operator;
	}

	public Filter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	/**
	 * searchParams中key的格式为OPERATOR_FIELDNAME
	 */
	public static Map<String, Filter> parse(Map<String, Object> searchParams) {
		Map<String, Filter> filters = Maps.newHashMap();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null || StringUtils.isBlank(value.toString())) {
				continue;
			}

			// 拆分operator与filedAttribute
			String[] names = StringUtils.split(key, SYMBOL.UNDERLINE);
			if (names.length != 2) {
				throw new IllegalArgumentException(key + " is not a valid search filter name");
			}
			String filedName = names[1];
			Operator operator = Operator.valueOf(names[0]);

			// 创建Filter
			Filter filter = new Filter(filedName, operator, value);
			filters.put(key, filter);
		}

		return filters;
	}

}
