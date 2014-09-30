package org.xu.Readings;

import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserStrategy {

	
	//日志记录
	/*
	 * 
	 */
	private static Logger logger = LoggerFactory.getLogger( ParserStrategy.class);
		
	
	public static String getP(String url){
	
		InputStream stream  = WebClient.url2sTream(new StringBuilder(url));
		
		if(stream != null){
			
			StringBuilder html = Strategy.getString(stream);
			
			
			 Document doc = Jsoup.parse(html.toString());
			 logger.debug("title:"+doc.title());
		

			 Element content = null;
			 content = doc.getElementsByAttributeValue("class", "contentmain").first() ; //   getElementById("content"); 
			if(content==null){
			
				logger.error("没有相应的 class= contentmain 的标签");
				return null;
			}
			
			 Elements ps = null;
			 ps = content.getElementsByTag("p"); 
			 if(ps==null){
					
					logger.error("在 class= contentmain 没有相应的 的 p 的标签");
					return null;
				
			 }
			 
			 
			 
			 logger.debug("size= "+ ps.size());
			 String result = "";
			 for (Element p : ps) { 
				 //解析相关的参数
				
				 String   name = p.html();
				 result += name;
			 }
			return result;
		}else{
			return null;
		}
	}
	
	
	public static String getP2(String url){
		
		InputStream stream  = WebClient.url2sTream(new StringBuilder(url));
		
		if(stream != null){
			
			StringBuilder html = Strategy.getString(stream);
			
			
			 Document doc = Jsoup.parse(html.toString());
			 logger.debug("title:"+doc.title());
			 logger.debug("Url : "+ url);

			 Element content = null;
			 content = doc.getElementsByAttributeValue("class", "contentmain").first() ; //   getElementById("content"); 
			 if(content==null){
			
				logger.error("没有相应的 class= contentmain 的标签");
				return null;
			 }
			
			 //要么写正则表达式
			 //要么进行遍历  到了 p，指导 input 节点   
//			 Elements ps = null;
//			 ps = content.getElementsByTag("p"); 
			 
//			 logger.debug("contentMain : "+content.html());
			 String result = content.html() ;
//			 
			 int index = result.indexOf("<p>");
			 System.out.println("index="+index);
			 
			 return result.substring(index) ;
//			 return content.text();
		}
		return null;
	}
}
