package org.xu.database;


import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.DBCollection;

import junit.framework.TestCase;

public class TestDBManager extends TestCase {
	
	private static DB db = null;
	private static Logger logger = LoggerFactory.getLogger(TestDBManager.class);

	@Before
	public void setUp()
	{
		db  = DBManager.getDB("test5");
		logger.debug("Hello");
	}
	@Test
	public void testDB()
	{
		Set<String> dbs = null;
		try{
			dbs  = db.getCollectionNames();
		}catch(Exception ex){
			logger.error(ex.toString());
		}
		for(String db : dbs)
		{
			System.out.println("dbName = "+db);
		}
	}
	
	@Test
	public void testAutoInc()
	{
		
		DBCollection	col = db.getCollection("listening");
	
		
		
		
	}
	@After
	public void tearDown()
	{
		logger.debug("tear down");
	}

}
