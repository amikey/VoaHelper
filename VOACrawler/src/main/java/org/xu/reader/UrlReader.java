package org.xu.reader;

import java.io.InputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xu.DownloadMethods.Downloader;
import org.xu.frontier.DBFrontier;
import org.xu.frontier.Frontier;
import org.xu.utils.Strategy;
import org.xu.utils.WebClient;

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
		System.out.println("结局---->"+count);
		
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
			
			StringBuilder builder = Strategy.getList(stream); 
			logger.debug("builder--->"+builder.toString());
			in_Produce(builder);
		}
	}
	
	private void in_Produce(StringBuilder str){
		
		Parser  parser = null;
		NodeFilter filter = null;
		NodeList nodeList = null;
		
		try{
			parser = new Parser(str.toString()) ;
			filter = new HasAttributeFilter("id","list");
			nodeList = parser.extractAllNodesThatMatch(filter);
			parser.reset();
		}catch(ParserException pe){
			logger.error("解析出错:"+pe.toString());
			return;
		}
		
		
		if(nodeList!=null && nodeList.size() >0 )
		{
			Node nameNode = nodeList.elementAt(0);
			logger.debug("toHtml---->"+nameNode.toHtml());
			
			filter = new NodeClassFilter(LinkTag.class);
			try {
				parser.setResource(nameNode.toHtml());
				nodeList = parser.extractAllNodesThatMatch(filter);
				if(nodeList==null || nodeList.size()<=0){
					logger.warn("解析的弄得list 为空 ");
					return;
				}
				for(int i=0;i<nodeList.size();i++)
				{
					LinkTag node = (LinkTag) nodeList.elementAt(i);
					//没有及时回收，导致  8个 就死掉了
					frontier.putUrl(node.extractLink());
					count++;
				}
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				logger.error("解析出错："+e.toString());
			}
		}else{
			return;
		}
	}
	/*
	 * 通过起始 url 得到 待读取 的 url 队列
	 */
	private void preReader() {

		in_Queue(Strategy.getPageList(WebClient.url2sTream(startUrl),
				"pagelist"));
		
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
			filter = new NodeClassFilter(LinkTag.class);
			
			nodeList = parser.extractAllNodesThatMatch(filter);
			if (nodeList == null || nodeList.size() <= 0) {
				System.out.println("这里为空");
				return;
			}
			for (int i = 0; i < nodeList.size(); i++) {
				LinkTag node = (LinkTag) nodeList.elementAt(i);
				System.out.println("Link is--->" + node.extractLink());
				queues.add(new StringBuilder(node.extractLink()));
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
				"http://www.51voa.com/VOA_Standard_1.html"));
		exmp.setFrontier(new DBFrontier("Queue"));
		exmp.startReader();
	}

	public void run() {
		// TODO Auto-generated method stub
		startReader();
	}

}
