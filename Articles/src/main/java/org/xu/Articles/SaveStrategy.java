package org.xu.Articles;


//通过保存策略模式   提供多种方法的接口   ，或许  叫  工厂模式？？
public class SaveStrategy {

	
	
	public static ContentSaver dbSave(String collectionName){
		
		return new MongoSave(collectionName);
	}
	
	
	public static ContentSaver fileSave(){
		return null;
	}
	
}
