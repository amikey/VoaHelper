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
	 * ��ӵ��ղأ��쳣�����  UnCollectһ��
	 * 
	 * 
	 * �����µ������е�����
	 * 		��Ҫ��ѯ���е�������ݣ�Ȼ����������� �� һ�� ������  list ���棬���set�Ϳ�����
	 */
	public  void Collect(String type,String title,String userName)
	{
		logger.debug("����Ӧ��");
		DBObject user = userDao.findUser(userName);
		
		if(user==null){
			logger.warn("��Ч�û�����");
			return;
		}
		
		//��Ҫ�ղصĶ���
		DBObject obj = null;
		//��Ҫ�ղصĶ�����ڲ������б�
		List<DBObject>  arrs = null;
		//����
		String date = "white";
		String intro = " ";
		
		try{
			
		//�鿴�ղصĶ���	
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
			logger.warn("��Ч�б�Ĳ���");
			return;
		}
		}catch(Exception e){
			logger.warn("������Ĳ���:"+e.toString());
			return;
		}
		//�����ǵ�һ�β��룬��ʱ �б��ǿյ�
		if(arrs==null){
			arrs =new ArrayList<DBObject>();
			logger.debug("arrs ����Ϊ��");
		}
		if(date==null){
			logger.debug("date==null");
		}
		
		//�ղ�����
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        //���ڵ��ַ�����ת��
        String collect_day = df.format(new Date());
		
		
		BasicDBObject new_obj = new BasicDBObject().append("title",title)
				.append("publish_time",DateUtils.Convert_Date_line(date)).append("collect_date", collect_day).append("intro", intro);
		arrs.add(new_obj);
		
		
		BasicDBObject newDocument3 =new BasicDBObject().append("$set",new BasicDBObject().append(type.toLowerCase()+"s", arrs));
		
		userDao.UpdateUser(userName, newDocument3);		
		//pages.update(new BasicDBObject().append("name", name), newDocument3);
		
		logger.debug("�뿪Ӧ��");
	}
	
	//��ɾ��һ���������ڵ�title��ʱ�򣬲������κ�ʵ���ԵĲ���,ֱ�ӷ���
	//��ɾ��һ���������ڵ��б��ʱ�򣬲������κ�ʵ���ԵĲ�����ֱ�ӷ���
	//���û��������ڣ��������κβ�����ֱ�ӷ���
	//����Ӧ�ý���  ģʽ�� �Ż��� �� �б��������ӻ��߼��ٵ�ʱ�� Ҫ���ڲ���
	//�б������ģ�ҲӦ�� �ܹ� ���ڲ���
	public  void  UnCollect(String type,String title,String userName)
	{
		
		DBObject user = userDao.findUser(userName);
		if(user==null){
			logger.warn("���������õ��û���:");
			return;
		}
		//��Ҫ�ղصĶ���
		DBObject obj = null;
		//��Ҫ�ղصĶ�����ڲ������б�
		List<DBObject>  arrs = null;
		//����
		String date = "white";
		
		
		//�鿴�ղصĶ���	
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
			logger.warn("��Ч�б�Ĳ���");
			return;
		}
		
		}catch(Exception e)
		{
			
			return;
		}
		
		//�����ǵ�һ�β��룬��ʱ �б��ǿյ�
		if(arrs==null){
			arrs =new ArrayList<DBObject>();
			logger.debug("arrs ����Ϊ��");
		}else{
			for(DBObject object :arrs){
				logger.debug("title:"+object.get("title"));
				if(object.get("title").equals(title)){
					arrs.remove(object);
					logger.debug("ɾ������:"+title);
					break;
				}
			}
		}
		
		BasicDBObject newDocument3 =new BasicDBObject().append("$set",new BasicDBObject().append(type.toLowerCase()+"s", arrs));
		
		userDao.UpdateUser(userName, newDocument3);
			
	}
	
	//�鿴�ض��û���������͵Ĳ�ѯ
	public List<VOAbs> views(String type,String userName,int start,int space)
	{
		//���ȼ���������
		int end = start+space;
		if(start>end||start<0){
			logger.warn("�Ƿ����� ��������");
			return null;
		}
		
		DBObject user = userDao.findUser(userName);
		if(user==null){
			logger.warn("��������ص��û���:");
			return null;
		}
		
		//��Ҫ�ղصĶ���
		DBObject obj = null;
		//��Ҫ�ղصĶ�����ڲ������б�
		List<DBObject>  arrs = null;
		
		List<VOAbs> results = new ArrayList<VOAbs>();
		
		//�鿴�ղصĶ���	
		logger.debug("type:"+type);
		try{
			if(type.equals("Article")){
					
				arrs = (List<DBObject>) user.get("articles");	
					
			}else if(type.equals("listening")){
					
				arrs = (List<DBObject>) user.get("listenings");
					
			}else if(type.equals("Reading")){
					
					arrs = (List<DBObject>) user.get("readings");
				
			}else{
				logger.warn("��Ч�б�Ĳ���");
					return null;
			}
		}catch(Exception e)
		{			
				return null;
		}
				
		//�����ǵ�һ�β��룬��ʱ �б��ǿյ�
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
