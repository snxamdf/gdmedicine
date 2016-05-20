/*
 * 
 *
 * 
 */
package com.yhy.core.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mybatis工具类.
 * 
 * @author YHY
 * @version 2013-7-22
 * @----------------------------------------------------------------------------------------
 * @updated 创建.
 * @updated by YHY
 * @updated at 2013-7-22
 */
public abstract class Statements {

	private static Logger logger = LoggerFactory.getLogger(Statements.class);

	/**
	 * 构建SqlSource.
	 */
	public static SqlSource getSqlSource(final BoundSql boundSql) {
		return new SqlSource() {
			@Override
			public BoundSql getBoundSql(Object parameterObject) {
				return boundSql;
			}
		};
	}

	/**
	 * 复制MappedStatement
	 */
	public static MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource sqlSource) {
		Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), sqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if (ms.getKeyProperties().length > 0) {
			builder.keyProperty(ms.getKeyProperties()[0]);
		} else {
			builder.keyProperty("id");
		}
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.cache(ms.getCache());
		MappedStatement newMs = builder.build();
		return newMs;
	}

	/**
	 * 获取countMappedStatement
	 */
	public static MappedStatement getCountMappedStatement(MappedStatement ms, Map<String, Object> params) {
		BoundSql boundSql = ms.getBoundSql(params);
		String countBoundSql = "select count(*) from (" + boundSql + ") as total";
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), countBoundSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
		MappedStatement newMs = copyFromMappedStatement(ms, getSqlSource(newBoundSql));
		return newMs;
	}

	/**
	 * 获取pageMappedStatement
	 */
	public static MappedStatement getPageMappedStatement(MappedStatement ms, Map<String, Object> params) {
		BoundSql boundSql = ms.getBoundSql(params);
		String begin = "select * from ( select row_.*, rownum rownum_ from (";
		String end = ") row_ where rownum <= #{limit}) where rownum_ > #{offset}";
		String pageBoundSql = begin + boundSql + end;
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), pageBoundSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
		MappedStatement newMs = copyFromMappedStatement(ms, getSqlSource(newBoundSql));
		return newMs;
	}

	/**
	 * 获取分页查询总数.
	 */
	public static int getRowCount(SqlSession sqlSession, String selectId, Object parameter) {
		int count = 0;
		try {
			MappedStatement mst = sqlSession.getConfiguration().getMappedStatement(selectId);
			BoundSql boundSql = mst.getBoundSql(parameter);
			String sql = " select count(1) as row_count from (" + boundSql.getSql() + ") as total ";
			logger.debug(sql.replaceAll("[\n\r]", " "));
			PreparedStatement pstmt = sqlSession.getConnection().prepareStatement(sql);
			setParameters(pstmt, mst, boundSql, parameter);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("row_count");
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			count = 0;
			throw new RuntimeException(e);
		}
		return count;
	}

	/**
	 * 设置查询参数.
	 */
	@SuppressWarnings("unchecked")
	private static void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					@SuppressWarnings("rawtypes")
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName + " of statement " + mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}

}
