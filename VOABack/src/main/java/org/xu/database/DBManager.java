package org.xu.database;

import java.net.UnknownHostException;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.mongodb.DB;
import com.mongodb.MongoClient;

/*
 * ʹ�õ���ģʽ
 */
public class DBManager {
	
	//��֤ �ͻ��˵�Ψһ��
	//��Ϊ  �ڲ�ʵ���� һ�����ӳأ���mongdo���� �� �̰߳�ȫ�ģ�����  ��ȡ�� db���� ���Զ����յ�
	private static MongoClient mongoClient = null;
	
	private static Logger logger = LoggerFactory.getLogger(DBManager.class);

	static{
		logger.debug("Hello come to DBManager");
		try {
			synchronized (DBManager.class) {
				//�������  �е� ���࣬��ʱ ���� ��ȥ��
				if(mongoClient==null){
					mongoClient = new MongoClient("localhost", 27017);
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			logger.error("mongoClientʵ����ʧ��:"+e.toString());		
		}
	}
	public static DB getDB(String name)
	{
		return mongoClient.getDB(name);
	}
	protected DBManager()
	{
		
	}
}
