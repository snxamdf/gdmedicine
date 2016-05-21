/*
 * 
 *
 * 
 */
package com.gdm.gen.dbunit;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.DataFileLoader;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gdm.gen.GenApplication;

/**
 * 数据库初始化测试类.
 * 
 * @author YHY
 * @version 2015-01-10
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GenApplication.class)
public class DbunitTests {

	@Autowired
	private DataSource dataSource;

	@Test
	@Rollback(false)
	public void testInsert() throws Exception {
		IDatabaseConnection connection = new DatabaseConnection(dataSource.getConnection(), "ISY_DEV_DB");
		DatabaseConfig config = connection.getConfig();
		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
		config.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
		DataFileLoader loader = new FlatXmlDataFileLoader();
		IDataSet ds = loader.load("/data.xml");
		DatabaseOperation.CLEAN_INSERT.execute(connection, ds);
	}

}
