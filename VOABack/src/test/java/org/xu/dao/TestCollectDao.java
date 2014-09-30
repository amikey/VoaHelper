package org.xu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xu.pojo.VOAbs;

public class TestCollectDao {

	CollectDao dao = null;
	
	private static Logger logger = LoggerFactory.getLogger(TestCollectDao.class);

	
	

	@Before
	public void setUp()
	{
		//这里性能 怎么样？？？？
		dao  = new CollectDao();
	}
	
	@Test
	public void testCollect()
	{
		for(int i=0;i<3;i++){
		dao.Collect("listening","Online Girls’ School Aims to Offer New Opportunities", "zhangsan");
		dao.Collect("listening","Online Girls’ School Aims to Offer New Opportunities", "zhangsan");
		dao.Collect("listening","Online Girls’ School Aims to Offer New Opportunities", "zhangsan");
		dao.Collect("listening","Online Girls’ School Aims to Offer New Opportunities", "zhangsan");
		}
//		dao.Collect("Article","关于一个空盒子", "zhangsan");
//		dao.Collect("Reading","控制你的情绪 Control Your Emotion", "zhangsan");
	}
	
//	@Test
//	public void testUnCollect()
//	{
//		dao.UnCollect("listening","Online Girls’ School Aims to Offer New Opportunities", "zhangsan");
//		//错误title的操作
////		dao.UnCollect("listening","Online Girls’ School Aims to Offer Ne", "zhangsan");
//		//错误列表的操作
////		dao.UnCollect("listeningsdfdsf","Online Girls’ School Aims to Offer New Opportunities", "zhangsan");
////		dao.UnCollect("listening","Online Girls’ School Aims to Offer New Opportunities", "zhangsansdfsd");
//	}
	
//	@Test
//	public void testView()
//	{
////		logger.debug("1-3");
////		List<VOAbs>  results = dao.views("listening","zhangsan",1,2);
////		for(VOAbs abs : results)
////		{
////			logger.debug("toString-->"+abs.toString());
////		}
//		
//		logger.debug("1-(-1)");
//		dao.views("listening","zhangsan",1,-2);
//		logger.debug("(-1)-(3)");
//		dao.views("listening","zhangsan",-1,4);
//		logger.debug("(-1)-(-3)");
//		dao.views("listening","zhangsan",-1,-2);
//		
////		dao.Collect("Article","关于一个空盒子", "zhangsan");
////		dao.Collect("Reading","控制你的情绪 Control Your Emotion", "zhangsan");
//	}
	@After
	public void tearDown()
	{
		logger.debug("tear down");
	}
}
