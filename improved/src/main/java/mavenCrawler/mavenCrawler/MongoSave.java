package mavenCrawler.mavenCrawler;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;

public class MongoSave implements UrlSave{
	
	public static DB db  = null;

	//使用no无意义的标示符，表示  未设置 集合名和文档名
	public static String dbname = "no";	
	
	public static String doc_name = "no";
	
	public MongoSave()
	{
		db = MongoPool.getConn();
		
	}
	public MongoSave(String name)
	{
		this.dbname = name;
		db = MongoPool.getConn(this.dbname);
	}
	
	/*
	 * name  mongo集合名称
	 * doc_name  mongo文档名称
	 */
	public MongoSave(String name,String doc_name)
	{
		this.dbname = name;
		db = MongoPool.getConn(this.dbname);
		this.doc_name = doc_name;
	}
	public void save(Object obj) {
		
		Page  page = (Page) obj;
		
		
		System.out.println("title-->"+page.getTitle());
		System.out.println("mp3-->"+page.getMp3());
		System.out.println("content-->"+page.getContent());
		
		//定义了相关的集合名
		if(!this.doc_name.equals("no")){
			db.getCollection(this.doc_name).insert(new BasicDBObject("title",page.getTitle().toString()).append("mp3", page.getMp3().toString()).append("content",page.getContent().toString()).append("date",page.getDate().toString()));

		}
		
		// TODO Auto-generated method stub
	
		
		
		if(db!=null)
		{
			db.getCollection("pages").insert(new BasicDBObject("title",page.getTitle().toString()).append("mp3", page.getMp3().toString()).append("content",page.getContent().toString()).append("date",page.getDate().toString()));
		}
	}
	
	/*
	 * doc_name  mongodb中集合的文档的名字
	 * obj  存储的对象（这里是page类的音频对象）
	 */
	public void save(String doc_name,Object obj) {
		// TODO Auto-generated method stub
		Page  page = (Page) obj;
		
		
		System.out.println("title-->"+page.getTitle());
		System.out.println("mp3-->"+page.getMp3());
		System.out.println("content-->"+page.getContent());
		
		
		if(db!=null)
		{
			db.getCollection(doc_name).insert(new BasicDBObject("title",page.getTitle().toString()).append("mp3", page.getMp3().toString()).append("content",page.getContent().toString()).append("date",page.getDate().toString()));
		}
	}
	
	
	public static void  testSave()
	{
		db = MongoPool.getConn();
		
		if(db!=null)
		{
			db.getCollection("pages").insert(new BasicDBObject("title","page----Title").append("mp3","mp3url").append("content","xujianhai").append("date","2011-1-2"));
		}
	}
	public static void main(String[]args)
	{
		testSave();
	}
	
}
