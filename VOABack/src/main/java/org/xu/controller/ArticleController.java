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
import org.xu.dao.ArticleDao;
import org.xu.dao.ListeningDao;
import org.xu.database.DBManager;
import org.xu.pojo.ArticleAbs;
import org.xu.pojo.ListeningAbs;


@Controller
@RequestMapping ( "/Article" )  
public class ArticleController {

private static Logger logger = LoggerFactory.getLogger(ArticleController.class);

	
	private  ArticleDao dao = new ArticleDao();
	
	/*
	 * 获取一些数据
	 */
	@RequestMapping(value = "views", method = RequestMethod.GET)
	@ResponseBody
	public  Object views(@RequestParam("starter") String starter,@RequestParam("space") String space) {
		
		int start = Integer.parseInt(starter);
		int sp = Integer.parseInt(space);
		logger.debug(starter+"---->"+sp);
		List<ArticleAbs> lis = dao.views(start,sp);
		
		
		return lis;
		
	}
	
	/*
	 * 获取 最新数据，一般是5条记录
	 */
	@RequestMapping(value = "Fresh", method = RequestMethod.GET)
	@ResponseBody
	public  Object Fresh(@RequestParam("date") String date) {
		
		String fresh  =  dao.Fresh_Date();
		logger.debug("date="+date+";fresh="+fresh);
		try{
			
			fresh = fresh.replace('-', '0');
			date = date.replace('-', '0');
			
			return  views("0","5");
			
//			if(Integer.parseInt(fresh)>Integer.parseInt( DateUtils.Convert_Date(date)))
//			{
//				//获取最新数据
//				return  views("0","5");
//			}
		}catch(Exception e){
			logger.error("转换字符串为Integer出错:"+e.toString());
		}
		
		return null;
	}
	
	@RequestMapping(value = "Article", method = RequestMethod.GET)
	@ResponseBody
	public  Object Article(@RequestParam("title") String title) {
		
//		return dao.Article_by_title("Rainy Day is Not Enough to Slow the World Cup Party");
		return dao.Article_by_title(title);
	}
	
	
	
	
	//这个用来做  调试 用的
	@RequestMapping(value = "model", method = RequestMethod.GET)
	public ModelAndView model() {
		
		ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.setViewName( "index" );  
	       return modelAndView;  
	}
	
}
