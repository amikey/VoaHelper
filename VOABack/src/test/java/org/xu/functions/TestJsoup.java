package org.xu.functions;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class TestJsoup {

	@Test
	public void testReadUrl()
	{
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.51voa.com/VOA_Standard_English/northern-california-quake-no-way-to-know-when-next-one-will-hit-58294.html").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String title = doc.title();
		System.out.println("title--->"+title);
	}
	
	/*
	 * 尝试 解析 class  元素
	 * 随便 熟悉 使用 jsoup
	 */
	@Test
	public void testSPAN()
	{
		File input = new File(System.getProperty("user.dir")
				+ "//src/test/java/resources/span.txt");
		Document doc = null;
		try {
			doc = Jsoup.parse(input, "UTF-8", "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Elements links = doc.select("SPAN[datatime]]"); //带有href属性的a元素
	
		  //扩展名为.png的图片

		Element el = doc.getElementsByClass("datetime").first();		
		if(el==null)
		{
			System.out.println("么得");
		}else{
			
			System.out.println("TagName----->"+el.tagName());
			System.out.println("text---->"+el.tagName());
			System.out.println("toString------>"+el.toString());
			System.out.println("val------>"+el.val());
			System.out.println("html--->"+el.html());
		}
	
	}
	
	/*
	 * 测试没有双引号的class元素
	 */
	@Test
	public void testSapn_Noquote()
	{
		
		String html = "<SPAN class=datetime>October 29,2013</SPAN>";
	
		Document doc = Jsoup.parseBodyFragment(html);
		Element el = doc.getElementsByClass("datetime").first();
		if(el!= null)
		{
			System.out.println("html--->"+el.html());
		}
	}
}
