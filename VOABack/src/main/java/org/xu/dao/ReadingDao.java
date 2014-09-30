package org.xu.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xu.database.DBManager;
import org.xu.pojo.Article;
import org.xu.pojo.ArticleAbs;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ReadingDao extends DaoAbs{

	//实行懒加载，避免 不必要的 损耗
	private  DB db = null;
	private  DBCollection pages = null;
	
	private static Logger logger = LoggerFactory.getLogger(ReadingDao.class);

	
	public ReadingDao()
	{
		logger.debug("ReadingDB");
		
		db = DBManager.getDB("Readings");
		
		pages = db.getCollection("readings");
	}
	
	
	/*
	 * 获取最新文档的日期
	 */
	public String  Fresh_Date()
	{
		
		DBObject obj = pages.find().sort(new BasicDBObject("time",-1)).one();   //One();
		
		if(obj!=null){
			return (String) obj.get("time");
		}
		
		return null;
	}
	
	/*
	 * 获取一条文章记录
	 */
	public ArticleAbs Readings_by_title(String title)
	{
			DBObject object = 	pages.findOne(new BasicDBObject("name",title));
			
//			String name,String time,String content,String intro
			return  new Article(object.get("name").toString(), object.get("time").toString(), object.get("content").toString(), object.get("intro").toString());	
	}
	
	/*
	 * 获取一定区间范围的数据
	 */
	public  List<ArticleAbs> views(int starter,int space){
		
		
		DBCursor  cursor = pages.find().sort(new BasicDBObject("time",-1));
		List<ArticleAbs> lis = new ArrayList<ArticleAbs>();
		
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
					lis.add(new Article(object.get("name").toString(), object.get("time").toString(), object.get("content").toString(), object.get("intro").toString()));
				}catch(Exception ex){
					logger.debug(ex.toString());
					
				}
					}else if(i>end){
				break;
			}	
		}
		return lis;
	}
	
	public DBObject  findReading(String name)
	{
		return pages.findOne(new BasicDBObject("name",name));
		
	}
	
	


}
