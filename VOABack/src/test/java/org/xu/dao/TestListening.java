package org.xu.dao;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xu.Utils.DateUtils;
import org.xu.dao.ListeningDao;
import org.xu.database.DBManager;
import org.xu.database.TestDBManager;
import org.xu.pojo.Listening;
import org.xu.pojo.ListeningAbs;

public class TestListening {

	ListeningDao dao = null;
	
	private static Logger logger = LoggerFactory.getLogger(TestListening.class);

	
	
	private Map<String,String>  MONS = new HashMap<String,String>();
	
	
		

	@Before
	public void setUp()
	{
		//这里性能 怎么样？？？？
		dao  = new ListeningDao();
	}
//	
//	@Test
//	public void testViews()
//	{
//		List<Listening> lis =  dao.views(0,1);
//		int dx= 0;
//		for(Listening li : lis)
//		{
//			System.out.println("=======================dx===============================");
//			System.out.println(li.getTitle());
//		}
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
//	@Test
//	public void testFresh()
//	{
//		
//		String  origi = dao.Fresh_Date();
//		//将两个日期 数据 进行比较
//		//两种方案   
//		//1.转换 数据格式
//		//2.自定义 比较方式
//		System.out.println("origi:"+origi);
//		System.out.println("传回的数据"+DateUtils.Convert_Date(origi) );
//		
//	}
	
//	@Test
//	public void testMP3()
//	{
//		
//		OutputStream  stream = dao.MP3("rainy-day-is-not-enough-to-slow-the-world-cup-party.mp3");
//	
//		logger.debug(stream.toString());
//	}

//	@Test
//	public void  getMp3s(){
//		
//		List<ListeningAbs> lis =  dao.views(0,10);
//		int dx= 0;
//		for(ListeningAbs li : lis)
//		{
//			System.out.println("=======================dx===============================");
//			System.out.println(li.getMo3());
//		}
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	//	
//	@Test
//	public void testArticle_by_title()
//	{
//		System.out.println(dao.Article_by_title("Rainy Day is Not Enough to Slow the World Cup Party").getMo3());
//		String origi = dao.Article_by_title("Rainy Day is Not Enough to Slow the World Cup Party").getMo3();
//		String mp3 = origi.substring(origi.lastIndexOf('/')+1);
//		System.out.println("origi--->"+mp3);
//		System.out.println(dao.Article_by_title("Online Girls’ School Aims to Offer New Opportunities").getTime());
//	}
//	
	
	
	@Test
	public void testViews()
	{
		List<ListeningAbs> lis =  dao.views(0,10);
		int dx= 0;
		for(ListeningAbs li : lis)
		{
			System.out.println("=======================dx===============================");
			System.out.println(li.getTitle()+"\n time:"+li.getTime());
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@After
	public void tearDown()
	{
		logger.debug("tear down");
	}
	
}
