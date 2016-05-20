/* 
 * 
 *
 * 
 */
package com.yhy.core.mapper;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yhy.core.utils.Reflections;

/**
 * mybatis分页plugin.
 * 
 * @author YHY
 * @version 2015-02-09
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-02-09
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PaginationInterceptor implements Interceptor {

	protected static Logger logger = LoggerFactory.getLogger(PaginationInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		RoutingStatementHandler statement = (RoutingStatementHandler) invocation.getTarget();
		PreparedStatementHandler handler = (PreparedStatementHandler) Reflections.getFieldValue(statement, "delegate");
		RowBounds rowBounds = (RowBounds) Reflections.getFieldValue(handler, "rowBounds");
		int offset = rowBounds.getOffset();
		int limit = rowBounds.getLimit();
		Reflections.setFieldValue(rowBounds, "offset", 0);
		if (limit > 0 && limit < RowBounds.NO_ROW_LIMIT) {
			BoundSql boundSql = statement.getBoundSql();
			String sql = boundSql.getSql();
			sql = getMysqlLimitString(sql, offset, limit);
			Reflections.setFieldValue(boundSql, "sql", sql);
			logger.debug(sql.replaceAll("[\n\r]", " "));
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties arg0) {
	}

	public String getOracleLimitString(String sql, int offset, int limit) {
		sql = sql.trim();
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		pagingSelect.append("select * from ( select row_.*, rownum rownum_ from (");
		pagingSelect.append(sql);
		pagingSelect.append(") row_ where rownum <= " + offset + limit + ") where rownum_ > " + offset);
		return pagingSelect.toString();
	}

	public String getMysqlLimitString(String sql, int offset, int limit) {
		StringBuffer pagingSelect = new StringBuffer(sql);
		pagingSelect.append(" limit ");
		pagingSelect.append(offset);
		pagingSelect.append(",");
		pagingSelect.append(limit);
		return pagingSelect.toString();
	}

}