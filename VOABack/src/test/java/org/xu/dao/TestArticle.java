package org.xu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xu.Utils.DateUtils;
import org.xu.dao.ArticleDao;
import org.xu.database.TestDBManager;
import org.xu.pojo.ArticleAbs;
import org.xu.pojo.ListeningAbs;

public class TestArticle {

	ArticleDao dao = null;
	
	private static Logger logger = LoggerFactory.getLogger(TestDBManager.class);

	
	private Map<String,String>  MONS = new HashMap<String,String>();
	
	
	@Before
	public void setUp()
	{
		//这里性能 怎么样？？？？
		dao  = new ArticleDao();
	}
	
	
	@Test
	public void testViews()
	{
		List<ArticleAbs> lis =  dao.views(0,100);
		int dx= 0;
		for(ArticleAbs li : lis)
		{
			System.out.println("=======================dx===============================");
			System.out.println(li.getTitle());
//			System.out.println(li.getIntro());
//			System.out.println(li.getContent());
//			System.out.println(li.getTime());
		}
		
	}
	
	
//	@Test
//	public void testFresh()
//	{
//		
//		String  origi = dao.Fresh_Date();
//		//将两个日期 数据 进行比较
//		
//		//两种方案   
//		//1.转换 数据格式
//		//2.自定义 比较方式
//		System.out.println(origi);
//		
//	}
	

//	@Test
//	public void testArticle_by_title()
//	{
//		System.out.println(" Name : "+dao.Article_by_title("关于论毅力").getName());
//		System.out.println(" Content: " + dao.Article_by_title("关于论毅力").getContent());
//		
////		System.out.println(dao.Article_by_title("Rainy Day is Not Enough to Slow the World Cup Party").getContent());
//	}
//	
	@After
	public void tearDown()
	{
		dao = null;
		logger.debug("tear down");
	}
	
}
