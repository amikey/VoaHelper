package org.xu.Readings;



import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;


import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoPool {
	
	private static MongoClient mongoClient = null;
	
	
	private static String dbName = "Readings";
	
	
	static{
		try {
			
				mongoClient = new MongoClient("localhost", 27017);
				
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	
	
}
