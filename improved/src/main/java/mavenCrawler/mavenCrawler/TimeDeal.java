package mavenCrawler.mavenCrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/*
 * 时间转换，抽取内容中的time标签
 */
public class TimeDeal {

	private DB src;
	private DB desti;
	private DBCollection pages;
	private DBCollection listening;

	private static Logger logger = LoggerFactory.getLogger(TimeDeal.class);

	public  TimeDeal() {
		
		src = MongoPool.getConn("MP3");
		desti = MongoPool.getConn("voas");
		pages = src.getCollection("pages");
		listening = desti.getCollection("listening");
	}

	/*
	 * 读取源数据库中的数据进行处理，存放到目的数据库
	 */
	public void read() throws InterruptedException {

		DBCursor cur = pages.find();
		while (cur.hasNext()) {
			DBObject obj = cur.next();
			String title = (String) obj.get("title");
			String content = (String) obj.get("content");
			/*
			 * 对content进行处理 ， 获取 mp3 地址
			 */
			String mp3 = (String) obj.get("mp3");
			String date = getDate(content);
			System.out.println(title + "\n" + date + "\n" + mp3 + "\n"
					+ content);
			
			//getContent(content);
			listening.insert(new BasicDBObject("title",title).append("mp3", mp3).append("content", content)
					.append("date", date)) ;
			
		}
	}

	/*
	 * 查看文章内容  用于分析文章
	 */
	public String getContent(String content)
	{
		
		Document doc = Jsoup.parseBodyFragment(content);
		Elements els = doc.getElementsByTag("P") ;
		
		if(els!= null)
		{
			logger.debug("\n\n=============================\n\n");
			for(Element el:els)
			{
				if(el.html()==" ")
					continue;
				logger.debug(el.html());
			}
		}
		return null;
		
	}
	
	/*
	 * 获取文章中的日期
	 */
	public String getDate(String content) {

		Document doc = Jsoup.parseBodyFragment(content);
		Element el = doc.getElementsByClass("datetime").first();
		if(el!= null)
		{
			return el.html();
		}
		return null;
	}

	public static void main(String[] args) {
		TimeDeal cnvrt = new TimeDeal();
		try {
			cnvrt.read();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
		}

	}
}
