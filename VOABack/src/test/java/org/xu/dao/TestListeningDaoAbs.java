package org.xu.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xu.pojo.Listening;
import org.xu.pojo.ListeningAbs;
import org.xu.wrapper.ListeningWrapper;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class TestListeningDaoAbs {

ListeningDaoAbs dao = null;
	
	private static Logger logger = LoggerFactory.getLogger(TestListeningDaoAbs.class);

	private Map<String,String>  MONS = new HashMap<String,String>();
	
	
	@Before
	public void setUp()
	{
		//这里性能 怎么样？？？？
//		dao  = new ListeningDaoAbs(){
//
//			@Override
//			public List<ListeningAbs> views(int starter, int space) {
//				
//				
//				
//
//				DBCursor  cursor = this.pages.find();
//						
//				List<ListeningAbs> lis = new ArrayList<ListeningAbs>();
//				
//				int i=-1;
//				int end = starter+space;
//				System.out.println("----->"+end);
//				while(cursor.hasNext())
//				{
//					i++;
//					System.out.println(i);
//					cursor.next();
//					if(i<starter){
//						
//						continue;
//					}else if(i>=starter&&i<=end){
//						DBObject object  =cursor.next();
//						
//						//lis.add(new Listening(object.get("mp3").toString(), null, object.get("title").toString(), object.get("date").toString()));
//					
//						try{
//							lis.add(new ListeningWrapper(new Listening(object.get("mp3").toString(), object.get("content").toString(), object.get("title").toString(), object.get("date").toString())));
//						
//						
//						}catch(Exception ex){
//							logger.debug(ex.toString());
//							
//						}
//							}else if(i>end){
//						break;
//					}
//				
//					
//				}
//				
//				logger.debug("输出相关语句");
//				
//				for(ListeningAbs obj:lis){
//					
//					System.out.println("listening:--->"+obj.getTime()+"   |title:"+obj.getTitle());
//				}
//				
//				return lis;
//			}
//			
//		};
		
		dao  = new ListeningDaoAbs(){

			@Override
			public List<ListeningAbs> views(int starter, int space) {
				
				
				

//				DBCursor  cursor = this.pages.find(new BasicDBObject("date", "September 21,2014"));
						
				DBCursor  cursor = this.pages.find().sort(new BasicDBObject("date",-1));
				List<ListeningAbs> lis = new ArrayList<ListeningAbs>();
				
				int i=-1;
				int end = starter+space;
				System.out.println("----->"+end);
				while(cursor.hasNext())
				{
					i++;
					System.out.println(i);
					cursor.next();
					if(i<starter){
						
						continue;
					}else if(i>=starter&&i<=end){
						DBObject object  =cursor.next();
						
						//lis.add(new Listening(object.get("mp3").toString(), null, object.get("title").toString(), object.get("date").toString()));
					
						try{
							logger.debug("mp3:"+object.get("mp3").toString());
							logger.debug("title:"+object.get("title").toString());
							logger.debug("date:"+object.get("date").toString());
							
							lis.add(new ListeningWrapper(new Listening(object.get("mp3").toString(), object.get("content").toString(), object.get("title").toString(), object.get("date").toString())));
						
						
						}catch(Exception ex){
							logger.debug(ex.toString());
							ex.printStackTrace();
							
						}
							}else if(i>end){
						break;
					}
				
					
				}
				
				logger.debug("输出相关语句");
				
				for(ListeningAbs obj:lis){
					
					
					System.out.println("listening:--->"+obj.getTime()+"   |title:"+obj.getTitle());
				}
				
				return lis;
			}
			
		};
	}

	@Test
	public void testView()
	{
		
		dao.views(1, 60);
	}

	@After
	public void tearDown()
	{
		logger.debug("tear down");
	}

}



