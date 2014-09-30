package mavenCrawler.mavenCrawler;

public interface Frontier {
	
	
	public String getNext();
	
	public void putUrl(String url);
}
