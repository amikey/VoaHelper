package org.xu.database;

import java.net.UnknownHostException;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.mongodb.DB;
import com.mongodb.MongoClient;

/*
 * 使用单例模式
 */
public class DBManager {
	
	//保证 客户端的唯一性
	//因为  内部实现了 一个连接池，且mongdo对象 是 线程安全的，并且  获取的 db对象 是自动回收的
	private static MongoClient mongoClient = null;
	
	private static Logger logger = LoggerFactory.getLogger(DBManager.class);

	static{
		logger.debug("Hello come to DBManager");
		try {
			synchronized (DBManager.class) {
				//这个好像  有点 多余，暂时 加入 进去吧
				if(mongoClient==null){
					mongoClient = new MongoClient("localhost", 27017);
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			logger.error("mongoClient实例化失败:"+e.toString());		
		}
	}
	public static DB getDB(String name)
	{
		return mongoClient.getDB(name);
	}
	protected DBManager()
	{
		
	}
}
