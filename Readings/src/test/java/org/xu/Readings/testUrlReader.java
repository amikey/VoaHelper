package org.xu.Readings;

import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class testUrlReader {

	private static Logger logger =  LoggerFactory.getLogger(testUrlReader.class);
	
	private UrlReader reader = null;
	
	@Before
	public void setUp()
	{	
		reader = new UrlReader(new StringBuilder(" ")); 
		reader.setFrontier(new DBFrontier("Readings"));
	}
	
	
	@Test
	public void testMongoProduce()
	{
		
		
		InputStream  stream = null;
		stream = WebClient.url2sTream(new StringBuilder("?page=5"));
		if(stream==null){
			logger.warn("url解析的stream 为null");
		}else{
		
			logger.debug("toString--->"+stream.toString());
		
			StringBuilder builder = Strategy.getString(stream); 
			logger.debug("builder--->"+builder.toString());
		
			reader.in_Produce(builder);
			
		}
	}
	
	@After
	public void tearDown()
	{
		reader = null;
	}
}
