/*
 * 
 *
 * 
 */
package com.yhy.core.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import com.yhy.core.constants.SYMBOL;
import com.yhy.core.dto.Result;
import com.yhy.core.search.Filter;
import com.yhy.core.search.Filter.Operator;
import com.google.common.collect.Lists;

/**
 * 查询条件工具类.
 * 
 * @author YHY
 * @version 2014-08-08
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-08-08
 */
public class Specifications {

	private static Logger logger = LoggerFactory.getLogger(Specifications.class);

	public static <T> Specification<T> byMap(final Map<String, Object> params) {
		return byFilter(Filter.parse(params).values());
	}

	public static <T> Specification<T> byFilter(final Filter... filters) {
		List<Filter> fs = Lists.newArrayList(filters);
		return byFilter(fs);
	}

	public static <T> Specification<T> byFilter(final Collection<Filter> filters) {
		return byFilters(null, filters);
	}

	public static <T> Specification<T> byMap(final Class<T> entityClazz, final Map<String, Object> params) {
		return byFilter(entityClazz, Filter.parse(params).values());
	}

	public static <T> Specification<T> byFilter(final Class<T> entityClazz, final Filter... filters) {
		List<Filter> fs = Lists.newArrayList(filters);
		return byFilter(entityClazz, fs);
	}

	public static <T> Specification<T> byFilter(final Class<T> entityClazz, final Collection<Filter> filters) {
		Object entity = null;
		try {
			entity = entityClazz.newInstance();
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return byFilters(entity, filters);
	}

	private static <T> Specification<T> byFilters(final Object entity, final Collection<Filter> filters) {
		return new Specification<T>() {
			@Override
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (Collections3.isNotEmpty(filters)) {

					List<Predicate> predicates = Lists.newArrayList();
					for (Filter filter : filters) {
						// 当是排序的话忽略
						if (filter.operator == Operator.AC || filter.operator == Operator.DC) {
							continue;
						}
						// 过滤掉空值
						if (filter.value == null || StringUtils.isBlank(filter.value.toString())) {
							continue;
						}

						// nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
						String[] names = StringUtils.split(filter.fieldName, SYMBOL.DOT);
						String fieldName = names[0];
						Result<Object> result = resetField(fieldName, filter.value);
						if (result.isSuccess()) {
							fieldName = result.getMessage();
							filter.value = result.getData();
						}
						if (entity != null) {
							if (!PropertyUtils.isReadable(entity, fieldName)) {
								continue;
							}
						}
						Path expression = root.get(fieldName);
						for (int i = 1; i < names.length; i++) {
							String fieldName4 = names[0];
							Result<Object> result4 = resetField(fieldName4, filter.value);
							if (result4.isSuccess()) {
								fieldName = result4.getMessage();
								filter.value = result4.getData();
							}
							expression = expression.get(fieldName4);
						}

						// logic operator
						switch (filter.operator) {
						case EQ:
							predicates.add(builder.equal(expression, filter.value));
							break;
						case NE:
							predicates.add(builder.notEqual(expression, filter.value));
							break;
						case GT:
							predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
							break;
						case LT:
							predicates.add(builder.lessThan(expression, (Comparable) filter.value));
							break;
						case GE:
							predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
							break;
						case LE:
							predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
							break;
						case LI:
							predicates.add(builder.like(expression, filter.value + ""));
							break;
						case LK:
							predicates.add(builder.like(expression, SYMBOL.PERCENT + filter.value + SYMBOL.PERCENT));
							break;
						case LL:
							predicates.add(builder.like(expression, SYMBOL.PERCENT + filter.value));
							break;
						case LR:
							predicates.add(builder.like(expression, filter.value + SYMBOL.PERCENT));
							break;
						case BE:
							String[] bes = StringUtils.split(filter.value.toString(), SYMBOL.COMMA);
							if (bes != null && bes.length == 2) {
								predicates.add(builder.between(expression, bes[0], bes[1]));
							}
							break;
						case IN:
							if (filter.value instanceof String) {
								String[] ins = StringUtils.split(filter.value.toString(), SYMBOL.COMMA);
								if (ins != null && ins.length > 0) {
									List<String> ls = Arrays.asList(ins);
									predicates.add(expression.in(ls));
								}
							} else {
								predicates.add(expression.in(filter.value));
							}
							break;
						case NU:
							predicates.add(builder.isNull(expression));
							break;
						case NN:
							predicates.add(builder.isNotNull(expression));
							break;
						default:
							predicates.add(builder.equal(expression, filter.value));
							break;
						}
					}

					// 将所有条件用 and 联合起来
					if (!predicates.isEmpty()) {
						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}

				return builder.conjunction();
			}
		};
	}

	/**
	 * 给几个查询条件字段特殊处理.
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 * @author YHY
	 * @version 2015-01-26
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-01-26
	 */
	private static Result<Object> resetField(String fieldName, Object value) {
		if (Strings.startsWith(fieldName, "filterStartDate2")) {
			fieldName = Strings.substringAfter(fieldName, "filterStartDate2");
			return Results.success(fieldName, resetDateValue(value, false));
		} else if (Strings.startsWith(fieldName, "filterStopDate2")) {
			fieldName = Strings.substringAfter(fieldName, "filterStopDate2");
			return Results.success(fieldName, resetDateValue(value, false));
		} else if (Strings.startsWith(fieldName, "filterStopDate4")) {
			fieldName = Strings.substringAfter(fieldName, "filterStopDate4");
			return Results.success(fieldName, resetDateValue(value, true));
		} else if (Strings.startsWith(fieldName, "filterBeginDate2")) {
			fieldName = Strings.substringAfter(fieldName, "filterBeginDate2");
			return Results.success(fieldName, resetDateValue(value, false));
		} else if (Strings.startsWith(fieldName, "filterEndDate2")) {
			fieldName = Strings.substringAfter(fieldName, "filterEndDate2");
			return Results.success(fieldName, resetDateValue(value, false));
		} else if (Strings.startsWith(fieldName, "filterEndDate4")) {
			fieldName = Strings.substringAfter(fieldName, "filterEndDate4");
			return Results.success(fieldName, resetDateValue(value, true));
		} else if (Strings.startsWith(fieldName, "filterMinAmount2")) {
			fieldName = Strings.substringAfter(fieldName, "filterMinAmount2");
			return Results.success(fieldName, value);
		} else if (Strings.startsWith(fieldName, "filterMaxAmount2")) {
			fieldName = Strings.substringAfter(fieldName, "filterMaxAmount2");
			return Results.success(fieldName, value);
		}
		return Results.fault(fieldName, value);
	}

	/**
	 * 重置日期类型查询条件值.
	 * 
	 * @param value
	 * @param next
	 * @author YHY
	 * @version 2015-01-26
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-01-26
	 */
	private static Object resetDateValue(Object value, boolean next) {
		Date date = null;
		if (value != null) {
			if (value instanceof String) {
				date = Dates.parse(value.toString());
			}
			if (value instanceof Date) {
				date = (Date) value;
			}
			if (next) {
				date = Dates.plusDays(date, 1);
			}
		}
		return date;
	}

}
