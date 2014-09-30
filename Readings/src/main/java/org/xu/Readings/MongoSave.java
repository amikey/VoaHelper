package org.xu.Readings;




import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MongoSave implements ContentSaver{
	
	private static Logger logger =  LoggerFactory.getLogger(MongoSave.class);
	
	private static DB db  = null;

	private String collectionName = "reading" ;
	
	
	
	public MongoSave()
	{
		
	}
	
	public MongoSave(String col)
	{
		this.collectionName = col;
	}
	public String getCollectionName() {
		return collectionName;
	}




	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}




	public void save(Object obj)
	{
		// TODO Auto-generated method stub
		
		Article article = (Article)obj;
		
		do{
			db = MongoPool.getConn();
		}while(db==null);
		
		System.out.println("MongoSave");
		System.out.println("name-->"+article.getName());
		System.out.println("href-->"+article.getHref() );
		System.out.println("intro-->"+article.getIntro());
		System.out.println("content-->"+article.getContent());
		
		
		db.getCollection(collectionName).insert(new BasicDBObject("name",article.getName()).append("time", article.getTime()).append("content",article.getContent()).append("intro", article.getIntro()).append("href", article.getHref()));

	}
	
	

	
	public static void main(String[]args)
	{
		System.out.println("===============开始啦===========================");
		
		
//		testSave();
		
//		testUpdate();
		
		System.out.println("=================结束啦================");
	}



	
	public void update(Object origi, Object modi) {
		// TODO Auto-generated method stub
		
		
		do{
			db = MongoPool.getConn();
		}while(db==null);
		
		if((origi instanceof String)&&(modi instanceof String))
		{
			logger.debug("yes String");
			
			String url = (String) origi;
			String content = (String) modi;
			
			
			DBObject condition = new BasicDBObject("href",url);
		 	 DBObject obj=  new BasicDBObject("content",modi);
		 	 DBObject upsertValue=new BasicDBObject("$set",obj);  
		 	 
		 	 
		 	db.getCollection(collectionName).findAndModify(condition,upsertValue);
			
		}else{
			logger.debug("not String");
		}
		
		
		
//		Article arti_ori = (Article) origi;
//		Article arti_modi = (Article) modi;
//		
//		do{
//			db = MongoPool.getConn();
//		}while(db==null);
		
//		
//		db.getCollection("articles").findAndModify(new BasicDBObject("name",arti_ori.getName()),new BasicDBObject("name",arti_modi.getName()).append("time", arti_modi.getTime()).append("content",arti_modi.getContent()).append("intro", arti_modi.getIntro()));
//		
//		db.getCollection("articles").findAndModify
		
//	 	 DBObject obj=  new BasicDBObject("content",arti_modi.getContent());
//	 	 
//	 	 DBObject upsertValue=new BasicDBObject("$set",obj);  
//	 	 
//		db.getCollection(collectionName).findAndModify(new BasicDBObject("name",arti_ori.getName()),upsertValue);
		
	}
}
