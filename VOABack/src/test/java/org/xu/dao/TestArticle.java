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
		//�������� ��ô����������
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
//		//���������� ���� ���бȽ�
//		
//		//���ַ���   
//		//1.ת�� ���ݸ�ʽ
//		//2.�Զ��� �ȽϷ�ʽ
//		System.out.println(origi);
//		
//	}
	

//	@Test
//	public void testArticle_by_title()
//	{
//		System.out.println(" Name : "+dao.Article_by_title("����������").getName());
//		System.out.println(" Content: " + dao.Article_by_title("����������").getContent());
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
