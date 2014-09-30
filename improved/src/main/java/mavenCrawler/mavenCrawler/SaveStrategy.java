package mavenCrawler.mavenCrawler;

public class SaveStrategy {

	public MongoSave dbSave(){
		
		return new MongoSave();
	}
	
	public void fileSave(){
		
	}
	
}
