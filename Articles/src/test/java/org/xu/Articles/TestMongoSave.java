package org.xu.Articles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMongoSave {

	private static Logger logger =  LoggerFactory.getLogger(TestMongoSave.class);
	
	private static MongoSave saver = null;
	
	@Before
	public void setUp()
	{
		saver = new MongoSave("testart");
	}
	
//	@Test
//	public  void  testSave()
//	{
//		//String name,String time,String intro,String href
//		Article origi = new Article("origi","2014-7-7","intro aaaa","href=abcdefg");
//		saver.save(origi);
//		logger.debug("完成");
//		
//	}
	
//	@Test
//	public 
//	void testUpdate()
//	{
//		Article origi = new Article("origi","2014-7-7","intro aaaa","href=abcdefg");
//		Article modi = new Article("modi","xxxx-xx-xx","intro xxxxxxxxxx","href=xxxxxxxxxxxx");
//		modi.setContent("contentjjjjjjjjjjjjjjj");
//		
//		saver.update(origi, modi);
//	}
	
	@Test
	public 
	void testUpdate()
	{
		Article origi = new Article("origi","2014-7-7","intro aaaa","href=abcdefg");
		Article modi = new Article("modi","xxxx-xx-xx","intro xxxxxxxxxx","href=xxxxxxxxxxxx");
		modi.setContent("contentjjjjjjjjjjjjjjj");
		
		saver.update("href=abcdefg", "contentjjjjjjjjjjjjjjj");
	}
	@After
	public void tearDown()
	{
		saver = null;
	}
}
