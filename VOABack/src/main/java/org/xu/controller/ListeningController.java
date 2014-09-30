package org.xu.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.xu.Utils.DateUtils;
import org.xu.dao.ListeningDao;
import org.xu.database.DBManager;
import org.xu.pojo.Listening;
import org.xu.pojo.ListeningAbs;

@Controller
@RequestMapping ( "/listening" )  
public class ListeningController {
 
	private static Logger logger = LoggerFactory.getLogger(ListeningController.class);

	
	private ListeningDao dao = new ListeningDao();
	
	/*
	 * 获取一些数据
	 */
	@RequestMapping(value = "views", method = RequestMethod.GET)
	@ResponseBody
	public  Object views(@RequestParam("starter") String starter,@RequestParam("space") String space) {
		
		int start = Integer.parseInt(starter);
		int sp = Integer.parseInt(space);
		logger.debug(starter+"---->"+sp);
		List<ListeningAbs> lis = dao.views(start,sp);
		
		System.out.println("结束");
		return lis;
		
	}
	
	/*
	 * 获取 最新数据，一般是5条记录
	 * 
	 * date>fresh   不进行数据的传输，传回标示符  no
	 * date<fresh    进行json数据传输
	 */
	@RequestMapping(value = "Fresh", method = RequestMethod.GET)
	@ResponseBody
	public  Object Fresh(@RequestParam("date") String date) {
		
		logger.debug("Fresh:"+dao.Fresh_Date()+"; get date:"+ date);
		
		String fresh  = DateUtils.Convert_Date(dao.Fresh_Date());
		
		logger.debug("Fresh:"+fresh+"; get date:"+DateUtils.Convert_Date(date));
		try{
			if(Integer.parseInt(fresh)>Integer.parseInt( DateUtils.Convert_Date(date)))
			{
				//获取最新数据
				return  views("0","5");
			}
		}catch(Exception e){
			logger.error("转换字符串为Integer出错:"+e.toString());
		}
		//在ajax请求过程中，服务器返回null表示error
		return null;
	}
	
	@RequestMapping(value = "Article", method = RequestMethod.GET)
	@ResponseBody
	public  Object Article(@RequestParam("title") String title) {
		
//		return dao.Article_by_title("Rainy Day is Not Enough to Slow the World Cup Party");
		return dao.Article_by_title(title);
	}
	
	
	@RequestMapping(value = "MP3", method = RequestMethod.GET)
	@ResponseBody
	public  Object MP3(@RequestParam("name") String name) {
		
//		return dao.Article_by_title("Rainy Day is Not Enough to Slow the World Cup Party");
		return dao.MP3("rainy-day-is-not-enough-to-slow-the-world-cup-party.mp3");
	}
	
	//这个用来做  调试 用的
	@RequestMapping(value = "model", method = RequestMethod.GET)
	public ModelAndView model() {
		
		ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.setViewName( "index" );  
	       return modelAndView;  
	}
}
