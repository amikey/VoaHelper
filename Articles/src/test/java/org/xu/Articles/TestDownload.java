package org.xu.Articles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDownload {

	
private static Logger logger =  LoggerFactory.getLogger(TestMongoSave.class);
	
	private static Downloader down = null;
	
	@Before
	public void setUp()
	{
		down = new Downloader(new DBFrontier("Articles")," "){
			
			@Override
			public void download(String Url) {
				// TODO Auto-generated method stub
				//下载  url 中的文本内容 ，需要解析 
				logger.debug("url---->"+Url);
				String result = ParserStrategy.getP(Url);
				
				
				if(result!=null)
				{
					logger.debug(result);
					SaveStrategy.dbSave("articles").update(Url, result);
					
				}else{
					//读取数据失败，放回数据，准备  后面  程序员的 检查
					this.frontier.putUrl(Url);
					logger.error("url 解析的 p节点  文本信息有问题，result = null");
				}
				
				
				
				return ;
				
			}
			
		};
	}
	

	
	@Test
	public 
	void testUpdate()
	{
		
		String[] urls = new String[]{"2370","2373","2377","2380","2383","2387"};
		
		
		for(int index = 0;index<urls.length;index++){
			down.download("/fw/"+ urls[index]+".html");
		}
		
	}
	@After
	public void tearDown()
	{
		down =  null;
		logger.debug("完成");
	}
	
}
