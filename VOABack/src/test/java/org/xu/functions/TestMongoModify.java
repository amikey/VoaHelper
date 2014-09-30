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
	 * update����   ��֧��  �������ͬʱ�޸�   ,
	 * ֻ�޸� �ҵ��ĵ�һ������
	 */
//	@Test
//	public void testModify()
//	{
//		DBObject object = 	pages.findOne((new BasicDBObject("name","xujianhai")));
//		
//		if(object != null){
//		
//			pages.update(new BasicDBObject("name","xujianhai").append("pass","xujianhai")  ,new BasicDBObject("name","xujianhai").append("pass","pass").append("sex","��") );
//			
//		}
//		
//		
//	}
//	
	
	//����һ���������ڵ��У����� Ҳֻ����һ��
//	@Test
//	public void testSet()
//	{
//			BasicDBObject newDocument3 =new BasicDBObject().append("$set",new BasicDBObject().append("occupation", "Stu"));
//			pages.update(new BasicDBObject().append("name", "xujianhai"), newDocument3);
//	}
	
	//����һ��Ƕ�׵��У� ���������ʽ,ֻ�ܵ�һ�εĲ��룬�Ժ�Ĳ������и���
//		@Test
//		public void testRec()
//		{
//				//����һ���ڲ�json
//				//����  reading ������
//				BasicDBObject reading1 = new BasicDBObject().append("title", "a new book");
//				BasicDBObject reading2 = new BasicDBObject().append("title", "a old book");
//				DBObject readings = new BasicDBObject();
//				
//				
//				/*
//				 * put  �����ǽ�  DBObject  ��Ϊkey-value ��ʽ�� Ԫ�� ���ڵ�
//				 *readings.put(reading1) ;
//				 * readings.put(reading2); 
//				 */
//				
//				List<DBObject> books = new ArrayList<DBObject>();  
//		        // �����е�ÿһ���ĵ�������ͨ��BasicDBObjectBuilder������  
//		        books.add(reading1);  
//		        books.add(reading2);
//				
//				BasicDBObject newDocument3 =new BasicDBObject().append("$set",new BasicDBObject().append("books", books));
//				pages.update(new BasicDBObject().append("name", "xujianhai"), newDocument3);
//				
//				
//		}
	
	
		//�鿴����
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
	
//		����һ��Ƕ�׵��У� ���������ʽ, ����֮ǰ �Ƿ����
//		@Test
//		public void testAddRec()   
//		{
//				//����һ���ڲ�json
//				//����  reading ������
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
//				 * ���ҳ���    �Ѿ��е�  collect ��  ԭ���� ����
//				 */
//				
//				
//				/*
//				 * put  �����ǽ�  DBObject  ��Ϊkey-value ��ʽ�� Ԫ�� ���ڵ�
//				 *readings.put(reading1) ;
//				 * readings.put(reading2); 
//				 */
//				BasicDBObject newDocument3 =new BasicDBObject().append("$set",new BasicDBObject().append("books", books));
//				pages.update(new BasicDBObject().append("name", "zhangsan"), newDocument3);
//				
//				
//		}
	
	//ɾ�������е�һ����,json���ݲ���ɾ��   ������ʵ�� 
//	@Test
//	public void testRemoveRec()   
//	{
//			//����һ���ڲ�json
//			//����  reading ������
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
	
	//����json���ݵĲ�ѯ   ����ɾ��
	@Test
	public void testRemoveCompletelyRec()   
	{
			//����һ���ڲ�json
			//����  reading ������
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
