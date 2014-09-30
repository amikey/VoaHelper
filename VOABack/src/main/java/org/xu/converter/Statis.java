package org.xu.converter;

import java.util.HashSet;
import java.util.Set;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xu.database.DBManager;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Statis {

	private DB src;
	private DB desti;
	private DBCollection pages;
	private DBCollection listening;

	private Parser parser = null;
	private NodeFilter filter = null;
	private NodeList nodeList = null;

	private int count = 0;

	private static Logger logger = LoggerFactory.getLogger(DBManager.class);

	private Set<String> all = new HashSet<String>();

	// 里面的参数 应该是可以 自定义的
	public Statis() {
		src = DBManager.getDB("voa");
		desti = DBManager.getDB("voa");
		pages = src.getCollection("listening");
		listening = desti.getCollection("listening");
	}

	// 这个方法 会变
	public void read() throws InterruptedException {

		DBCursor cur = pages.find();

		while(cur.hasNext()) {
			DBObject obj = cur.next();

			String content = (String) obj.get("content");
			String title = (String) obj.get("title");
			String date = (String) obj.get("date");
			/*
			 * 对content进行处理 ， 获取 mp3 地址
			 */
			String mp3 = (String) obj.get("mp3");

			/*
			 * 对content进行处理 ， 去除不必要的数据内容
			 */
			convert(content);
		}

		logger.debug("一共有 = " + count);
		for (String ele : all) {
			logger.debug(ele);
		}
	}

	public String convert(String origi) {
		String result = "";

		try {
			Document doc = Jsoup.parseBodyFragment(origi);

			Element content = doc.getElementsByAttributeValue("id", "content")
					.first();

			Elements els = content.children();

			for (Element el : els) {
				String tag = "没有标签:";

				tag = el.tagName();
				all.add(tag);

				
				logger.debug("=====\n" + tag + "-----" + el.tagName());
				
				logger.debug("文本内容---->" + "hasText---->"+el.hasText() +" ====isBlock--->"+el.isBlock()+"====html:=="+el.html());
				//.html 获取的是 文本内的东西
			}

			count++;

		} catch (Exception e) {
			logger.error("解析出问题" + e.toString());
		}

		return result;
	}

	public static void main(String[] args) {
		Statis cnvrt = new Statis();
		try {
			cnvrt.read();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
		}

	}

}
