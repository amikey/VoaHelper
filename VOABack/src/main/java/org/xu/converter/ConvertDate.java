package org.xu.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xu.Utils.DateUtils;
import org.xu.database.DBManager;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;



/*
 * 添加日期格式，用于比较，是否删除以前的日期 格式  取决于 自己
 */
public class ConvertDate {
	
	private DB src;
	private DB desti;
	private DBCollection src_pages;
	private DBCollection dest_pages;

	private static Logger logger = LoggerFactory.getLogger(ConvertDate.class);

	
	public ConvertDate() {
		
		src = DBManager.getDB("voas");
		src_pages = src.getCollection("listening");
	
	}

	
	/*
	 * 读取源数据库中的数据进行处理，存放到目的数据库
	 */
	public void read() throws InterruptedException {

		DBCursor cur = src_pages.find();
		while (cur.hasNext()) {
			DBObject obj = cur.next();
			String title = (String) obj.get("title");
			String content = (String) obj.get("content");
			/*
			 * 对content进行处理 ， 获取 mp3 地址
			 */
			String mp3 = (String) obj.get("mp3");
	
			String date = (String)obj.get("date");
			try{
				
				System.out.println(title + "\n" +DateUtils.Convert_Date_line(date) + "\n" + mp3);
			
				//getContent(content);
			
				BasicDBObject newDocument3 =new BasicDBObject().append("$set",new BasicDBObject().append("date",DateUtils.Convert_Date_line(date)));
			
			
				src_pages.update(new BasicDBObject().append("title",title), newDocument3);
		
			}catch(Exception ex){
			
				logger.debug("异常情况:"+ex.toString()+"  |此时date="+date);
				ex.printStackTrace();
			
			}
			
		}
	}

	
	
	public static void main(String[]args)
	{
	
		try {
			new  ConvertDate().read();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.debug(e.toString());
			
		}
		
		
	}

}
