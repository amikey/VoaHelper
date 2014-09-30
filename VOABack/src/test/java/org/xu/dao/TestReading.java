package org.xu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xu.database.TestDBManager;
import org.xu.pojo.ArticleAbs;
import org.xu.pojo.ListeningAbs;

public class TestReading {

    ReadingDao dao = null;
	
	private static Logger logger = LoggerFactory.getLogger(TestReading.class);

	
	private Map<String,String>  MONS = new HashMap<String,String>();
	
	
	@Before
	public void setUp()
	{
		//这里性能 怎么样？？？？
		dao  = new ReadingDao();
	}
	
	@Test
	public void testViews()
	{
		List<ArticleAbs> lis =  dao.views(0,5);
		int dx= 0;
		for(ArticleAbs li : lis)
		{
			System.out.println("=======================dx===============================");
			System.out.println(li.getTitle());
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	//	
//	@Test
//	public void testArticle_by_title()
//	{
//		logger.debug("testArticle_by_title");
//		System.out.println(dao.Readings_by_title("关于诚实的一篇作文").getContent());
////		System.out.println(dao.Article_by_title("Rainy Day is Not Enough to Slow the World Cup Party").getContent());
//	}
	
	@After
	public void tearDown()
	{
		logger.debug("tear down");
	}
}
