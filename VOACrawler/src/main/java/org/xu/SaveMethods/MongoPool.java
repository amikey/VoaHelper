package org.xu.SaveMethods;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class MongoPool {
	
	private static MongoClient mongoClient = null;
	private static DB db = null;
	
	private static String dbName = "test5";
	private static final int maxSeconds = 100;
	private static int maxConn = 20 ;
	
	private static List<DB> pools = null;
	
	//修改操作频繁  使用LinkList
	private static List<Boolean> records = null ; 
	
	static{
		try {
			synchronized (MongoPool.class) {
				mongoClient = new MongoClient("localhost", 27017);
				
				pools = new LinkedList<DB>();
				records = new LinkedList<Boolean>();
				
				while(maxConn!=0)
				{
					records.add(true);
					maxConn--;
					pools.add(mongoClient.getDB(dbName));
				}
				
				
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getMaxConn() {
		return maxConn;
	}

	public static void setMaxConn(int maxConn) {
		
		MongoPool.maxConn = maxConn;
	}

	public static String getDbname() {
		return dbName;
	}

	public static void setDBname(String dbName){
		MongoPool.dbName = dbName;
	}
	/*
	 * 这里 应该 抛出异常   或者 等待一段时间  还是 阻塞呢？？？？？？？？？？？？？？？？
	 * synchronized
	 */
	public static  DB  getConn() {
		/*
		 * mongoClient默认是长连接的
		 */
		return mongoClient.getDB(dbName);
		

	}
	
	public static void setConn()
	{
		
	}
}
