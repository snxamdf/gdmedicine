/* 
 * 
 *
 * 
 */
package com.yhy.gen.utils;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.DataFileLoader;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;

/**
 * 数据库测试工具类.
 * 
 * @author YHY
 * @version 2013-05-15
 */
public abstract class DbUnits {

	/**
	 * 清除并插入XML数据文件到数据库.
	 * XML数据文件中涉及的表在插入数据前会先进行清除.
	 * 
	 * @param xmlPaths 符合Spring Resource路径格式的文件列表.
	 */
	public static void loadData(DataSource dataSource, String schema, String... xmlPaths) throws Exception {
		execute(DatabaseOperation.CLEAN_INSERT, dataSource, schema, xmlPaths);
	}

	/**
	 * 插入XML数据文件到数据库.
	 */
	public static void appendData(DataSource dataSource, String schema, String... xmlPaths) throws Exception {
		execute(DatabaseOperation.INSERT, dataSource, schema, xmlPaths);
	}

	/**
	 * 在数据库中删除XML数据文件中涉及的表的数据.
	 */
	public static void removeData(DataSource dataSource, String schema, String... xmlPaths) throws Exception {
		execute(DatabaseOperation.DELETE_ALL, dataSource, schema, xmlPaths);
	}

	/**
	 * 按DBUnit Operation执行XML数据文件的数据.
	 * 
	 * @param xmlPaths 符合Spring Resource路径格式的文件列表.
	 */
	private static void execute(DatabaseOperation operation, DataSource dataSource, String schema, String... xmlPaths) throws DatabaseUnitException, SQLException {
		IDatabaseConnection connection = new DatabaseConnection(dataSource.getConnection(), schema);
		// 解决mysql抛org.dbunit.dataset.NoSuchColumnException异常
		DatabaseConfig config = connection.getConfig();
		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
		config.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
		for (String xmlPath : xmlPaths) {
			DataFileLoader loader = new FlatXmlDataFileLoader();
			IDataSet ds = loader.load(xmlPath);
			operation.execute(connection, ds);
		}
	}

}
