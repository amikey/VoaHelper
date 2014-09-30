package org.xu.Readings;

import java.io.InputStream;
import java.io.ObjectInputStream.GetField;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.OptionTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UrlReader implements Runnable{

	//记录当前 的 记录条数，共享队列中的数量
	private static int count = 0;
	
	//日志记录
	private static Logger logger = LoggerFactory.getLogger( Downloader.class);
	
	//起始的Url
	private StringBuilder startUrl = null;

	//预读取到的 url  的 队列
	private Queue<StringBuilder> queues = new ConcurrentLinkedQueue<StringBuilder>();

	//工作队列，共享数据  url 的 保存区域
	Frontier frontier = null;
	

	public Frontier getFrontier() {
		return frontier;
	}

	//桥接模式
	public void setFrontier(Frontier frontier) {
		this.frontier = frontier;
	}

	/*
	 * 构造函数  提供起始url
	 */
	public UrlReader(StringBuilder startUrl) {
		this.startUrl = startUrl;
	}
	
	
	public void startReader() {

		preReader();
		produce();
		logger.debug("结局---->"+count);
		
	}

	/*
	 * 将网页 转化成 stream 流 进行处理
	 */
	private void produce(){
		while(!queues.isEmpty())
		{
			logger.info("预队列解析的url:"+queues.peek());
			
			InputStream  stream = null;
			stream = WebClient.url2sTream(queues.poll());
			if(stream==null){
				logger.warn("url解析的stream 为null");
				continue;
			}else
				logger.debug("toString--->"+stream.toString());
			
			StringBuilder builder = Strategy.getString(stream); 
			logger.debug("builder--->"+builder.toString());
			in_Produce(builder);
		}
	}
	
	public void in_Produce(StringBuilder html){
		
		 Document doc = Jsoup.parse(html.toString());
		 logger.debug("title:"+doc.title());
	

		 Element content = doc.getElementsByAttributeValue("class", "content-list").first() ; //   getElementById("content"); 
		 Elements lis = content.getElementsByTag("li"); 
		 logger.debug("size= "+lis.size());
		 for (Element li : lis) { 
			 //解析相关的参数
			 Element el = li.getElementsByTag("a").first();
			 String  href =  el.attr("href");
			 String  name = el.text();
			 
			 el = li.getElementsByTag("span").first();
			 String origi = el.text();
			 int index = origi.indexOf('：');
//			 logger.debug("index:"+  index);
			 String  time =  origi.substring(index+1) ;
			 
			 el = li.getElementsByTag("p").first();
			 String  intro =  el.text();
			 
			 //String name,String time,String intro,String href
			 Article article = new Article(name,time,intro,href);
			 
			 SaveStrategy.dbSave("readings").save(article);
			 frontier.putUrl(href);
			 	
			logger.debug("name:"+name+" ;href:"+href+" ;time:"+time+" ;into:"+intro);
			

		 }
		 
		 
	}
	/*
	 * 通过起始 url 得到 待读取 的 url 队列
	 */
	private void preReader() {

		in_Queue(Strategy.getString(WebClient.url2sTream(startUrl)));
		
		return;
	}

	/*
	 * 将待读的的 url  提取出来，并放进 队列中
	 * 
	 */
	protected void in_Queue(StringBuilder str) {
		// 正则表达式 匹配
		Parser parser = null;
		NodeFilter filter = null;
		NodeList nodeList = null;

		try {

			// 第一种方案
			parser = new Parser(str.toString()); 
			filter = new NodeClassFilter(OptionTag.class); 
			
			nodeList = parser.extractAllNodesThatMatch(filter);
			if (nodeList == null || nodeList.size() <= 0) {
				
				logger.debug("这里为空");
				return;
			}
			for (int i = 0; i < nodeList.size(); i++) {
				OptionTag node = (OptionTag) nodeList.elementAt(i);
				logger.debug("value is--->" + node.getAttribute("value") );
				queues.add(new StringBuilder(node.getAttribute("value")));
			}
			parser.reset();
			
		} catch (ParserException pe) {
			logger.error("预读取队列操作解析出错:"+pe.toString());
		} 
		return;
	}

	//用于测试使用
	public static void main(String[] args) {
		UrlReader exmp = new UrlReader(new StringBuilder(
				"http://www.adreep.cn/fw/"));
		exmp.setFrontier(new DBFrontier("Queue"));
		
		InputStream str = WebClient.url2sTream((new StringBuilder("http://www.adreep.cn/fw/")));
		StringBuilder content = Strategy.getString(str);

		logger.debug("所有内容:"+content);
		logger.debug("------------------------------");
		exmp.in_Produce(content); 
		//exmp.preReader();
		//		exmp.startReader();
	}

	public void run() {
		// TODO Auto-generated method stub
		startReader();
	}

}