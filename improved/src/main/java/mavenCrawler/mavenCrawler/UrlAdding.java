package mavenCrawler.mavenCrawler;

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

public class UrlAdding {


	private static int count = 0;
	private StringBuilder startUrl = null;

	//预读取到的 url  的 队列
	private Queue<StringBuilder> queues = new ConcurrentLinkedQueue<StringBuilder>();

	//工作队列
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
	public  UrlAdding(StringBuilder startUrl) {
		this.startUrl = startUrl;
	}

	public void startReader() {

		preReader();
		produce();
		System.out.println("结局---->"+count);
		
	}

	//从待读取的 urls队列中  依次读取  url,获取  相应的 流
	private void produce(){
		while(!queues.isEmpty())
		{
			System.out.println("来吧--->"+queues.peek());
			
			InputStream  stream = null;
			stream = WebClient.url2sTream(queues.poll());
			if(stream==null)
				System.out.println("stream is null");
			else
				System.out.println("toString--->"+stream.toString());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			
				e.printStackTrace();
			}
			StringBuilder builder = Strategy.getList(stream); 
			System.out.println("builder--->"+builder.toString());
			in_Produce(builder);
		}
	}

	//有可能需要改进
	//从相应的流中  读取  所有的url队列 ，准备 下载
	private void in_Produce(StringBuilder str){
		
		Parser  parser = null;
		NodeFilter filter = null;
		NodeList nodeList = null;
		
		try{
			parser = new Parser(str.toString()) ;
			filter = new HasAttributeFilter("id","list");
			nodeList = parser.extractAllNodesThatMatch(filter);
		}catch(ParserException pe){
			System.out.println("出错啦"+pe.toString());
		}
		
		System.out.println("开始啦");
		
		if(nodeList!=null && nodeList.size() >0 )
		{
			Node nameNode = nodeList.elementAt(0);
			System.out.println("toHtml---->"+nameNode.toHtml());
			//第一种方案
			filter = new NodeClassFilter(LinkTag.class);
			try {
				parser.setResource(nameNode.toHtml());
				nodeList = parser.extractAllNodesThatMatch(filter);
				if(nodeList==null || nodeList.size()<=0){
					System.out.println("这里为空");
					return;
				}
				for(int i=0;i<nodeList.size();i++)
				{
					LinkTag node = (LinkTag) nodeList.elementAt(i);
					System.out.println("Link is--->"+i+"--->"+node.extractLink());
					System.out.println("存在的URL---->"+count);
					//没有及时回收，导致  8个 就死掉了
					frontier.putUrl(node.extractLink());
					count++;
					
				}
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		parser.reset();
		System.out.println("这里结束啦");
	}
	/*
	 * 通过起始 url 得到 待读取 的 url 队列
	 */
	public void preReader() {

		this.queues.add(startUrl);
		
//		while(!queues.isEmpty())
//			System.out.println("--->"+queues.poll());
		return;
	}

	/*
	 * 将待读的的 url  提取出来，并放进 队列中
	 * 
	 */
	
	
	public static void main(String[] args) {
		UrlAdding exmp = new UrlAdding(new StringBuilder(
				"http://www.51voa.com/VOA_Standard_1.html"));
		exmp.setFrontier(new DBFrontier("Queue"));
		exmp.startReader();
	}
}
