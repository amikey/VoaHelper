package org.xu.SaveMethods;

import org.xu.POJO.Page;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;

public class MongoSave implements UrlSave{
	
	public static DB db  = null;

	public void save(Object obj) {
		// TODO Auto-generated method stub
		Page  page = (Page) obj;
		db = MongoPool.getConn();
		
		if(db!=null)
		{
			db.getCollection("pages").insert(new BasicDBObject("title",page.getTitle().toString()).append("mp3", page.getMp3().toString()).append("content",page.getContent().toString()));
		}
		
	}
	
	public static void  testSave()
	{
		db = MongoPool.getConn();
		
		if(db!=null)
		{
			db.getCollection("pages").insert(new BasicDBObject("title","page----Title").append("mp3","mp3url").append("content","xujianhai"));
		}
	}
	public static void main(String[]args)
	{
		testSave();
	}
	
}
