package org.xu.functions;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xu.dao.TestUserDao;
import org.xu.dao.UserDao;
import org.xu.database.DBManager;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class TestMongoModify {


	private DB db = null;
	private DBCollection  pages = null;
	
	
	private static Logger logger = LoggerFactory.getLogger(TestUserDao.class);

	
	@Before
	public void setUp()
	{

		logger.debug("UserDao");
		
		db = DBManager.getDB("Users");
		
		pages = db.getCollection("users");
	}

	
	
	/*
	 * update方法   不支持  多个对象同时修改   ,
	 * 只修改 找到的第一个对象
	 */
//	@Test
//	public void testModify()
//	{
//		DBObject object = 	pages.findOne((new BasicDBObject("name","xujianhai")));
//		
//		if(object != null){
//		
//			pages.update(new BasicDBObject("name","xujianhai").append("pass","xujianhai")  ,new BasicDBObject("name","xujianhai").append("pass","pass").append("sex","男") );
//			
//		}
//		
//		
//	}
//	
	
	//设置一个并不存在的列，但是 也只能是一个
//	@Test
//	public void testSet()
//	{
//			BasicDBObject newDocument3 =new BasicDBObject().append("$set",new BasicDBObject().append("occupation", "Stu"));
//			pages.update(new BasicDBObject().append("name", "xujianhai"), newDocument3);
//	}
	
	//设置一个嵌套的列， 以数组的形式,只能第一次的插入，以后的插入会进行覆盖
//		@Test
//		public void testRec()
//		{
//				//定义一个内层json
//				//关于  reading 的数组
//				BasicDBObject reading1 = new BasicDBObject().append("title", "a new book");
//				BasicDBObject reading2 = new BasicDBObject().append("title", "a old book");
//				DBObject readings = new BasicDBObject();
//				
//				
//				/*
//				 * put  方法是将  DBObject  作为key-value 形式的 元素 存在的
//				 *readings.put(reading1) ;
//				 * readings.put(reading2); 
//				 */
//				
//				List<DBObject> books = new ArrayList<DBObject>();  
//		        // 数组中的每一个文档，我们通过BasicDBObjectBuilder来构造  
//		        books.add(reading1);  
//		        books.add(reading2);
//				
//				BasicDBObject newDocument3 =new BasicDBObject().append("$set",new BasicDBObject().append("books", books));
//				pages.update(new BasicDBObject().append("name", "xujianhai"), newDocument3);
//				
//				
//		}
	
	
		//查看数组
//		@Test
//		public void testViewArr()
//		{
//			BasicDBObject reading1 = new BasicDBObject().append("title", "a new book");
//			BasicDBObject reading2 = new BasicDBObject().append("title", "a old book");
//			DBObject readings = new BasicDBObject();
//			
//			
//			DBObject has =  (DBObject) pages.find(new BasicDBObject("name","xujianhai")).one(); 
//			List<DBObject> books =  (List<DBObject>) has.get("books");
//			
//			
//			for(DBObject obj:books)
//			{
//				logger.debug("title="+obj.get("title"));
//			}
//		}
	
//		设置一个嵌套的列， 以数组的形式, 无论之前 是否存在
//		@Test
//		public void testAddRec()   
//		{
//				//定义一个内层json
//				//关于  reading 的数组
//				BasicDBObject reading1 = new BasicDBObject().append("title", "a new book").append("dollar", "$10");
//				BasicDBObject reading2 = new BasicDBObject().append("title", "a old book").append("dollar", "$11");
//				DBObject readings = new BasicDBObject();
//				
//				
//				DBObject has =  (DBObject) pages.find(new BasicDBObject("name","zhangsan")).one(); 
//				List<DBObject> books = (List<DBObject>) has.get("books");
//				if(books==null){
//					books =new ArrayList<DBObject>();
//				}
//				books.add(reading1);
//				books.add(reading2);
//				
//				/*
//				 * 先找出来    已经有的  collect 的  原来的 数组
//				 */
//				
//				
//				/*
//				 * put  方法是将  DBObject  作为key-value 形式的 元素 存在的
//				 *readings.put(reading1) ;
//				 * readings.put(reading2); 
//				 */
//				BasicDBObject newDocument3 =new BasicDBObject().append("$set",new BasicDBObject().append("books", books));
//				pages.update(new BasicDBObject().append("name", "zhangsan"), newDocument3);
//				
//				
//		}
	
	//删除数组中的一部分,json内容部分删除   不可能实现 
//	@Test
//	public void testRemoveRec()   
//	{
//			//定义一个内层json
//			//关于  reading 的数组
//			BasicDBObject reading1 = new BasicDBObject().append("title", "a new book");
//			
//			DBObject readings = new BasicDBObject();
//			
//			DBObject has =  (DBObject) pages.find(new BasicDBObject("name","zhangsan")).one(); 
//			List<DBObject> books = (List<DBObject>) has.get("books");
//			if(books==null){
//				books =new ArrayList<DBObject>();
//			}else{
//				books.remove(reading1);
//			}
//			
//			BasicDBObject newDocument3 =new BasicDBObject().append("$set",new BasicDBObject().append("books", books));
//			pages.update(new BasicDBObject().append("name", "zhangsan"), newDocument3);
//			
//	}
	
	//部分json内容的查询   进行删除
	@Test
	public void testRemoveCompletelyRec()   
	{
			//定义一个内层json
			//关于  reading 的数组
			BasicDBObject reading1 = new BasicDBObject().append("title", "a new book").append("dollar", "$10");

			DBObject readings = new BasicDBObject();
			
			
			DBObject has =  (DBObject) pages.find(new BasicDBObject("name","zhangsan")).one(); 
			List<DBObject> books = (List<DBObject>) has.get("books");
			if(books==null){
				books =new ArrayList<DBObject>();
			}else{
				books.remove(reading1);
			}
			
			
			BasicDBObject newDocument3 =new BasicDBObject().append("$set",new BasicDBObject().append("books", books));
			pages.update(new BasicDBObject().append("name", "zhangsan"), newDocument3);
			
	}
	
	@After
	public void tearDown()
	{
		logger.debug("tear down");
	}

}
