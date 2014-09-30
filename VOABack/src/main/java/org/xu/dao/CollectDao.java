package org.xu.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xu.Utils.DateUtils;
import org.xu.database.DBManager;
import org.xu.pojo.Article;
import org.xu.pojo.Listening;
import org.xu.pojo.ListeningAbs;
import org.xu.pojo.VOAbs;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class CollectDao extends DaoAbs{

	private static  Logger logger = LoggerFactory.getLogger(CollectDao.class);
	
	private DB db = null;
	private DBCollection  pages = null;
	
	
	
	private static   UserDao userDao = new UserDao();
	private static   ArticleDao articleDao = new ArticleDao();
	private static   ListeningDao  listeningDao = new ListeningDao();
	private static   ReadingDao readingDao = new ReadingDao();
	
	private static final int start_pos = 0;
	private static final int end_pos = 200;
	
	/*
	 * 添加到收藏，异常处理和  UnCollect一样
	 * 
	 * 
	 * 插入新的数组中的数据
	 * 		先要查询所有的相关数据，然后添加新数据 后， 一起 放在了  list 里面，最后set就可以了
	 */
	public  void Collect(String type,String title,String userName)
	{
		logger.debug("进入应用");
		DBObject user = userDao.findUser(userName);
		
		if(user==null){
			logger.warn("无效用户访问");
			return;
		}
		
		//需要收藏的对象
		DBObject obj = null;
		//需要收藏的对象的内部对象列表
		List<DBObject>  arrs = null;
		//日期
		String date = "white";
		String intro = " ";
		
		try{
			
		//查看收藏的对象	
		if(type.equals("Article")){
		
			arrs = (List<DBObject>) user.get("articles");
			Article temp = (Article) articleDao.Article_by_title(title);
			date = temp.getTime();
			intro = temp.getContent().substring(start_pos,end_pos);
			
		}else if(type.equals("listening")){
		
			
			arrs = (List<DBObject>) user.get("listenings");
			
			 ListeningAbs temp = (ListeningAbs) listeningDao.Article_by_title(title);
			 date =  temp.getTime();
			 intro = temp.getContent().substring(start_pos,end_pos);
			 
		}else if(type.equals("Reading")){
			
	
			arrs = (List<DBObject>) user.get("readings");
			Article temp = (Article) readingDao.Readings_by_title(title);
			date = temp.getTime() ;
			
			intro = temp.getContent().substring(start_pos,end_pos);
			
		}else{
			logger.warn("无效列表的操作");
			return;
		}
		}catch(Exception e){
			logger.warn("不合理的操作:"+e.toString());
			return;
		}
		//可能是第一次插入，此时 列表是空的
		if(arrs==null){
			arrs =new ArrayList<DBObject>();
			logger.debug("arrs 数组为空");
		}
		if(date==null){
			logger.debug("date==null");
		}
		
		//收藏日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        //日期到字符串的转换
        String collect_day = df.format(new Date());
		
		
		BasicDBObject new_obj = new BasicDBObject().append("title",title)
				.append("publish_time",DateUtils.Convert_Date_line(date)).append("collect_date", collect_day).append("intro", intro);
		arrs.add(new_obj);
		
		
		BasicDBObject newDocument3 =new BasicDBObject().append("$set",new BasicDBObject().append(type.toLowerCase()+"s", arrs));
		
		userDao.UpdateUser(userName, newDocument3);		
		//pages.update(new BasicDBObject().append("name", name), newDocument3);
		
		logger.debug("离开应用");
	}
	
	//当删除一个并不存在的title的时候，不进行任何实质性的操作,直接返回
	//当删除一个并不存在的列表的时候，不进行任何实质性的操作，直接返回
	//当用户名不存在，不进行任何操作，直接返回
	//这里应该进行  模式的 优化， 当 列表增多增加或者减少的时候， 要便于操作
	//列表名更改，也应该 能够 便于操作
	public  void  UnCollect(String type,String title,String userName)
	{
		
		DBObject user = userDao.findUser(userName);
		if(user==null){
			logger.warn("不存在想用的用户名:");
			return;
		}
		//需要收藏的对象
		DBObject obj = null;
		//需要收藏的对象的内部对象列表
		List<DBObject>  arrs = null;
		//日期
		String date = "white";
		
		
		//查看收藏的对象	
		logger.debug("type:"+type);
		try{
		if(type.equals("Article")){
			
			arrs = (List<DBObject>) user.get("articles");	
			date = articleDao.Article_by_title(title).getTime();
			
		}else if(type.equals("listening")){
			
			arrs = (List<DBObject>) user.get("listenings");
			date = listeningDao.Article_by_title(title).getTime();
//			
		}else if(type.equals("Reading")){
			
			arrs = (List<DBObject>) user.get("readings");
			date = readingDao.Readings_by_title(title).getTime() ;
			
		}else{
			logger.warn("无效列表的操作");
			return;
		}
		
		}catch(Exception e)
		{
			
			return;
		}
		
		//可能是第一次插入，此时 列表是空的
		if(arrs==null){
			arrs =new ArrayList<DBObject>();
			logger.debug("arrs 数组为空");
		}else{
			for(DBObject object :arrs){
				logger.debug("title:"+object.get("title"));
				if(object.get("title").equals(title)){
					arrs.remove(object);
					logger.debug("删除标题:"+title);
					break;
				}
			}
		}
		
		BasicDBObject newDocument3 =new BasicDBObject().append("$set",new BasicDBObject().append(type.toLowerCase()+"s", arrs));
		
		userDao.UpdateUser(userName, newDocument3);
			
	}
	
	//查看特定用户的相关类型的查询
	public List<VOAbs> views(String type,String userName,int start,int space)
	{
		//首先检查参数问题
		int end = start+space;
		if(start>end||start<0){
			logger.warn("非法操作 负数传入");
			return null;
		}
		
		DBObject user = userDao.findUser(userName);
		if(user==null){
			logger.warn("不存在相关的用户名:");
			return null;
		}
		
		//需要收藏的对象
		DBObject obj = null;
		//需要收藏的对象的内部对象列表
		List<DBObject>  arrs = null;
		
		List<VOAbs> results = new ArrayList<VOAbs>();
		
		//查看收藏的对象	
		logger.debug("type:"+type);
		try{
			if(type.equals("Article")){
					
				arrs = (List<DBObject>) user.get("articles");	
					
			}else if(type.equals("listening")){
					
				arrs = (List<DBObject>) user.get("listenings");
					
			}else if(type.equals("Reading")){
					
					arrs = (List<DBObject>) user.get("readings");
				
			}else{
				logger.warn("无效列表的操作");
					return null;
			}
		}catch(Exception e)
		{			
				return null;
		}
				
		//可能是第一次插入，此时 列表是空的
		if(arrs!=null){
				
				for(int i=0;i<arrs.size()&&i<end;i++){
					
					if(i<start){
							continue;
					}else if(i>=start&&i<end){
						DBObject object  = arrs.get(i);
						
						try{
							//String title, String publish_time, String collect_time,
							//String intro
							//"title",title)
							//.append("publish_time", date).append("collect_date", collect_day);
							results.add(new  VOAbs(object.get("title").toString(), object.get("publish_time").toString(), object.get("collect_date").toString(),object.get("intro").toString()));    
							
						}catch(Exception ex){
							logger.debug(ex.toString());
							
						}
					}
				}
				
		}
		return results;
	}
	
}
