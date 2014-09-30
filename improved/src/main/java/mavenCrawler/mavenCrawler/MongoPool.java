package mavenCrawler.mavenCrawler;

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
	
	private static String dbName = "MP3";

	
	
	static{
		try {
			synchronized (MongoPool.class) {
				mongoClient = new MongoClient("localhost", 27017);
				
				
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static String getDbname() {
		return dbName;
	}

	public static void setDBname(String vardbName){
		 dbName = vardbName;
	}
	/*
	 * 这里 应该 抛出异常   或者 等待一段时间  还是 阻塞呢？？？？？？？？？？？？？？？？
	 */
	public static synchronized DB  getConn() {
		
		return mongoClient.getDB(dbName);

	}
	
	/*
	 *获取一个自定义的数据库连接
	 */
	public static synchronized DB  getConn(String dbName) {
		
		return mongoClient.getDB(dbName);

	}

}
