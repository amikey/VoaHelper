package org.xu.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xu.database.DBManager;
import org.xu.pojo.ListeningAbs;

import com.mongodb.DB;
import com.mongodb.DBCollection;


public  abstract class ListeningDaoAbs{
	
	
	//实行懒加载，避免 不必要的 损耗
		private  DB listeningDB = null;
		protected  DBCollection pages = null;
		
		private static Logger logger = LoggerFactory.getLogger(ListeningDaoAbs.class);
		
		
		public ListeningDaoAbs()
		{
			System.out.println("listeningDB");
			
			listeningDB = DBManager.getDB("voas");
			
			pages = listeningDB.getCollection("listening");
		}
			
	public abstract  List<ListeningAbs> views(int starter,int space);
}
