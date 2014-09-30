package org.xu.converter;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xu.database.DBManager;
import org.xu.pojo.Listening;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ConvertCnt {

	private DB src;
	private DB desti;
	private DBCollection pages;
	private DBCollection listening;

	private static Logger logger = LoggerFactory.getLogger(DBManager.class);

	public ConvertCnt() {
		src = DBManager.getDB("test3");
		desti = DBManager.getDB("voas");
		pages = src.getCollection("pages");
		listening = desti.getCollection("listening");
	}

	/*
	 * 读取源数据库中的数据进行处理，存放到目的数据库
	 */
	public void read() throws InterruptedException {

		DBCursor cur = pages.find();
		while (cur.hasNext()) {
			DBObject obj = cur.next();
			String title = (String) obj.get("title");
			String content = (String) obj.get("content");
			/*
			 * 对content进行处理 ， 获取 mp3 地址
			 */
			String mp3 = (String) obj.get("mp3");
	
			String date = (String)obj.get("date");
			System.out.println(title + "\n" + date + "\n" + mp3 + "\n"
					+ content);
			
			//getContent(content);
			listening.insert(new BasicDBObject("title",title).append("mp3", mp3).append("content", content)
					.append("date", date)) ;
			
		}
	}

	
		
	
	
}
