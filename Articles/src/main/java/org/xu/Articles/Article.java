package org.xu.Articles;

public class Article extends ArticleAbs{

	public Article()
	{
		
	}
	
	public Article(String name,String time,String intro,String href)
	{
		this.name = name;
		this.time= time;
		this.intro = intro;
		this.href = href;
		this.content=" ";
	}

	
}
