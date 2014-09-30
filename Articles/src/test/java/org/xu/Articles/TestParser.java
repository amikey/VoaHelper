package org.xu.Articles;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestParser {

	
	//日志信息
		private static  Logger logger = LoggerFactory.getLogger( TestParser.class);
		
	@Test
	public void  testGetP()
	{
		String content = ParserStrategy.getP("http://www.adreep.cn/fw/3266.html");
		logger.debug("Content: -->"+content);
	}
}
