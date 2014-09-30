package org.xu.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xu.dao.ListeningDaoAbs;
import org.xu.database.DBManager;
import org.xu.pojo.Listening;
import org.xu.pojo.ListeningAbs;
import org.xu.wrapper.ListeningWrapper;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public  abstract class DelData {

	//��������ɾ��
	private  DB listeningDB = null;
	protected  DBCollection pages = null;
	
	private static Logger logger = LoggerFactory.getLogger(DelData.class);

	//Ĭ�����ݿ���ĵ�  �� ��������
	public DelData(){
			System.out.println("listeningDB");
			
			listeningDB = DBManager.getDB("voas");
			
			pages = listeningDB.getCollection("listening");
	}
	
	//�Զ������ݿ���ĵ�  �� ��������
	public DelData(String collection,String docname){
	
		System.out.println("listeningDB");
		
		listeningDB = DBManager.getDB(collection);
		
		pages = listeningDB.getCollection(docname);
	
	}
	/*
	 * ѭ����ȡԴ���ݿ��е����ݽ��д�����ŵ�Ŀ�����ݿ�
	 * 
	 */
	public  void Del() {
		
	
		DBCursor  cursor = this.pages.find().sort(new BasicDBObject("date",-1));
		List<ListeningAbs> lis = new ArrayList<ListeningAbs>();
		
		int i = 0;
		while(cursor.hasNext())
		{
			i++;
			System.out.println(i);


		DBObject object  =cursor.next();
				
		try{
			logger.debug("mp3:"+object.get("mp3").toString());
			logger.debug("title:"+object.get("title").toString());
			logger.debug("date:"+object.get("date").toString());
			
			
		
		}catch(Exception ex){
			logger.debug(ex.toString());
			ex.printStackTrace();
			
		}
		
		if(del_jus(object)){
			
			//�������ݵ�ɾ��
		
			pages.remove(object);
			lis.add(new ListeningWrapper(new Listening(object.get("mp3").toString(), object.get("content").toString(), object.get("title").toString(), object.get("date").toString())));
			
		}
		
		}
		
		
		System.out.println("�ܹ�ɾ��:"+lis.size());
		for(ListeningAbs abs: lis){
			System.out.println(abs.getTime().toString());
		}
		logger.debug("���������");
		
		
		return;
	}
	
	//���󷽷����Զ���ʵ��  �����ж��Ƿ���Ҫɾ��
	public  abstract boolean  del_jus(DBObject object);
	
	
	public static void main(String[]args)
	{
//		String del = "Sep";
		
		//ɾ�� ����Sep July �·ݵ����ݣ���SepΪ����
		DelData fun = new DelData(){

			@Override
			public boolean del_jus(DBObject object) {
				// TODO Auto-generated method stub
				return object.get("date").toString().contains("Oct");
			}
			
		};
		
		//ɾ��  ���ڸ�ʽ������  2014-9-17 
		DelData fun2 = new DelData(){

			@Override
			public boolean del_jus(DBObject object) {
				// TODO Auto-generated method stub
				String source = object.get("date").toString();
				String[]  Dt_ars = source.split("-");
				 
				String  identify = Dt_ars[1];
				
				if(identify.length()==1)
					return true;
				
				return false;
			}
			
		};
		
		fun2.Del();
	}
}
