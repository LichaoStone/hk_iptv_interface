package com.hicon.frame.page;


import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Intercepts({@org.apache.ibatis.plugin.Signature(type = org.apache.ibatis.executor.Executor.class,
			method = "query", 
			args   = {
					MappedStatement.class, 
					Object.class, 
					RowBounds.class,
					org.apache.ibatis.session.ResultHandler.class
					}) 
			})
public class PaginationInterceptor implements Interceptor {
	public static final Logger logger = Logger.getLogger(PaginationInterceptor.class);
	
	Dialect dialect = null;
    
	/**
	 * mybatis分页拦截器
	 */
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = invocation.getArgs()[1];
		
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		String originalSql = boundSql.getSql().trim();
		RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];
		
		logger.info("【分页拦截器】sql语句:" +originalSql);
		
		Object parameterObject = boundSql.getParameterObject();
		if ((boundSql == null) || (boundSql.getSql() == null) || ("".equals(boundSql.getSql()))){
			return null;
		}

		Page page = null;
		PageContext context = PageContext.getContext();
		logger.info("【分页拦截器】context.isPaginationController():" +context.isPaginationController());
		logger.info("【分页拦截器】context.isPaginationService():" +context.isPaginationService());
		
		if ((page == null) 
				&& (context.isPaginationController()) 
				&& (context.isPaginationService())){
			page = context;
		}
		
		if ((page != null)
				&& (context.isPaginationController()) 
				&& (context.isPaginationService())){
			
			originalSql = "SELECT * FROM (" + originalSql + ") TEMP WHERE 1=1 ";
			
			String sqlWhere = page.getSqlWhere();
			if ((sqlWhere != null) && (!(sqlWhere.trim().equals("")))) {
				originalSql = originalSql + sqlWhere;
			}

			logger.info("--sqlwhere--="+originalSql);


			//int totRows = page.getTotalRows();
			//if (totRows == 0) {
				int totRows=0;
				StringBuffer countSql = new StringBuffer(originalSql.length() + 100);
				countSql.append("select count(1) from (").append(originalSql).append(") t");
				logger.info("【分页拦截器】countSql:" +countSql.toString());
				
				Connection connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
				PreparedStatement countStmt = connection.prepareStatement(countSql.toString());
				BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql.toString(),
						boundSql.getParameterMappings(), parameterObject);
				setParameters(countStmt, mappedStatement, countBS, parameterObject);
				ResultSet rs = countStmt.executeQuery();
				if (rs.next()) {
					totRows = rs.getInt(1);
				}
				logger.info("【分页拦截器】totRows:" +totRows);
				rs.close();
				countStmt.close();
				connection.close();
			//}

			page.setTotalRows(totRows);
			
			if ((rowBounds == null) || (rowBounds == RowBounds.DEFAULT)) {
				rowBounds = new RowBounds(page.getPageStartRow(), page.getPageSize());
			}

			String sortWhere = page.getSortWhere();
			if ((sortWhere != null) && (!(sortWhere.trim().equals("")))) {
				originalSql = originalSql + sortWhere;
			}

			logger.info("--sortWhere--"+originalSql);


			String pagesql = this.dialect.getLimitString(originalSql, rowBounds.getOffset(), rowBounds.getLimit());
			logger.info("【分页拦截器】pagesql:" +pagesql);
			
			invocation.getArgs()[2] = new RowBounds(0, 2147483647);
			BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), pagesql,
					boundSql.getParameterMappings(), boundSql.getParameterObject());

			MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
			invocation.getArgs()[0] = newMs;
		}
		
		return invocation.proceed();
	}

	
	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	
	public void setProperties(Properties p) {
		this.dialect = (this.dialect = new MySql5Dialect());
	}

	private void setParameters(PreparedStatement ps,MappedStatement mappedStatement,
			BoundSql boundSql,Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		
		List parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = (parameterObject == null) ? null : configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); ++i) {
				ParameterMapping parameterMapping = (ParameterMapping) parameterMappings.get(i);
				if (parameterMapping.getMode() == ParameterMode.OUT)
					continue;
				String propertyName = parameterMapping.getProperty();
				PropertyTokenizer prop = new PropertyTokenizer(propertyName);
				Object value;
				if (parameterObject == null) {
					value = null;
				} else {
					if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else {
						if (boundSql.hasAdditionalParameter(propertyName)) {
							value = boundSql.getAdditionalParameter(propertyName);
						} else if ((propertyName.startsWith("__frch_"))
								&& (boundSql.hasAdditionalParameter(prop.getName()))) {
							    value = boundSql.getAdditionalParameter(prop.getName());
							if (value != null)
								value = configuration.newMetaObject(value)
										.getValue(propertyName.substring(prop.getName().length()));
						} else {
							value = (metaObject == null) ? null : metaObject.getValue(propertyName);
						}
					}
				}
				TypeHandler typeHandler = parameterMapping.getTypeHandler();
				if (typeHandler == null) {
					throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName
							+ " of statement " + mappedStatement.getId());
				}
				typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
			}
		}
	}

	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
			MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource,
					ms.getSqlCommandType());
			builder.resource(ms.getResource());
			builder.fetchSize(ms.getFetchSize());
			builder.statementType(ms.getStatementType());
			builder.keyGenerator(ms.getKeyGenerator());
	
			builder.timeout(ms.getTimeout());
			builder.parameterMap(ms.getParameterMap());
			builder.resultMaps(ms.getResultMaps());
			builder.cache(ms.getCache());
			MappedStatement newMs = builder.build();
			return newMs;
	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;
		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}
		public BoundSql getBoundSql(Object parameterObject) {
			return this.boundSql;
		}
	}
}