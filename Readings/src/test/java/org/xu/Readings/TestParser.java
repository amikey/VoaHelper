package org.xu.Readings;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestParser {

	
	//日志信息
		private static  Logger logger = LoggerFactory.getLogger( TestParser.class);
		
//	@Test
//	public void  testGetP()
//	{
//		String content = ParserStrategy.getP("http://www.adreep.cn/wz/53876.html");
//		logger.debug("Content: -->"+content);
//	}
		
		@Test
		public void  testGetP()
		{
			String content = ParserStrategy.getP2("http://www.adreep.cn/wz/53876.html");
			logger.debug("Content: -->"+content);
		}
		
}
