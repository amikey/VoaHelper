package org.xu.dao;



import org.slf4j.Logger;


import org.slf4j.LoggerFactory;
import org.xu.Utils.DateUtils;
import org.xu.database.DBManager;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class UserDao extends DaoAbs{
	
	private static  Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	private DB db = null;
	private DBCollection  pages = null;

	
	public UserDao()
	{
		
		logger.debug("UserDao");
		
		db = DBManager.getDB("Users");
		
		pages = db.getCollection("users");
		
	}
	
	//判断用户名  密码  是否匹配
	public boolean login(String name,String pass)
	{
		DBObject object = 	pages.findOne((new BasicDBObject("name",name)).append("pass", pass));
		
		if(object != null){
			return true;
		}
		return false;
	}
	
	//更改密码
	public boolean chngPass(String userName,String pass){
		
		try{
			BasicDBObject newDocument3 =new BasicDBObject().append("$set",new BasicDBObject().append("pass",pass));
		
		
			pages.update(new BasicDBObject().append("name",userName), newDocument3);
		
		
			return true;
		}catch(Exception ex){
			logger.debug("数据库更改过程有问题:"+ex.toString());
			return false;
		}
	}
	
	
	public void addUser(String name,String pass)
	{
		pages.insert(new BasicDBObject("name",name).append("pass",pass));

	}
	
	
	public DBObject  findUser(String name)
	{
		return pages.findOne(new BasicDBObject("name",name));
		
	}
	
	public void  UpdateUser(String name,BasicDBObject newDocument3)
	{
		pages.update(new BasicDBObject().append("name", name), newDocument3);
		
		return ;
	}
	
}
