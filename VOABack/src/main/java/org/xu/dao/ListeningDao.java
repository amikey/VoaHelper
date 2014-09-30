package org.xu.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xu.Utils.Config;
import org.xu.database.DBManager;
import org.xu.pojo.Listening;
import org.xu.pojo.ListeningAbs;
import org.xu.wrapper.ListeningWrapper;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


/*
 * sort  的效果很差
 */
public class ListeningDao {

	//实行懒加载，避免 不必要的 损耗
	private  DB listeningDB = null;
	private  DBCollection pages = null;
	
	private static Logger logger = LoggerFactory.getLogger(DBManager.class);

	
	public ListeningDao()
	{
		System.out.println("listeningDB");
		
		listeningDB = DBManager.getDB("voas");
		
		pages = listeningDB.getCollection("listening");
	}
	
	
	public OutputStream MP3(String name)
	{
		//应该还需要检测 合法性
		try {
			return new FileOutputStream(new File(Config.mp3_Dir +name));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
//		return null;
	}
	/*
	 * 获取最新文档的日期
	 */
	public String  Fresh_Date()
	{
		
		DBObject obj = this.pages.find().sort(new BasicDBObject("date",-1)).one() ;
		
//		DBCursor cursor = this.pages.find().sort(new BasicDBObject("date",-1)).limit(1);
//		if(cursor!=null){
//			DBObject obj = cursor.next();
//			return (String) obj.get("date");
//		}
		return (String) obj.get("date");
//		return null;
	}
	
	/*
	 * 获取一条文章记录
	 */
	public ListeningAbs Article_by_title(String title)
	{
		ListeningWrapper wrapper =null;	
		DBObject object = 	pages.findOne(new BasicDBObject("title",title));
		wrapper = new ListeningWrapper(new Listening(object.get("mp3").toString(), object.get("content").toString(), object.get("title").toString(), object.get("date").toString()));	
		return  wrapper;	
	}
	
	/*
	 * 获取一定区间范围的数据
	 */
	public  List<ListeningAbs> views(int starter,int space){
		
		
		
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
					lis.add(new ListeningWrapper(new Listening(object.get("mp3").toString(), object.get("content").toString(), object.get("title").toString(), object.get("date").toString())));
				}catch(Exception ex){
					logger.debug(ex.toString());
					
				}
					}else if(i>end){
				break;
			}
		
			
		}
		return lis;
	}
	
	public DBObject findListening(String name)
	{
		return pages.findOne(new BasicDBObject("title",name));
		
	}
//	public static void main(String[]args)
//	{
//		ListeningDao dao  = new ListeningDao();
//		List<Listening> lis =  dao.views(0,10);
//		int dx= 0;
//		for(Listening li : lis)
//		{
//			System.out.println("=======================dx===============================");
//			System.out.println(li);
//		}
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
