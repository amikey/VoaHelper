package org.xu.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xu.dao.CollectDao;
import org.xu.pojo.ListeningAbs;
import org.xu.pojo.VOAbs;





@Controller
@RequestMapping ( "/Collect" )   
public class CollectController {

	
	private static Logger logger = LoggerFactory.getLogger(CollectController.class);

	private static String Collected = "1";
	private static String UnCollected = "0";
	
	private  CollectDao dao  =new CollectDao();
	
	
		@RequestMapping(value = "love", method = RequestMethod.GET,produces="text/plain;charset=UTF-8;")
		@ResponseBody
		public  String love(@RequestParam("type") String type,@RequestParam("title") String title,
				@RequestParam("user")String userName,@RequestParam("flag")String flag){
	
			logger.debug("type:"+type+"---->title"+title+"--->"+userName+"--->"+(Integer.parseInt(flag)==Integer.parseInt(Collected))+"|"+
						(Integer.parseInt(flag)==Integer.parseInt(UnCollected))+"|"+(flag==flag));
			
			//flag =1 收藏,flag =0,取消
		
			if(flag.equals(Collected))
			{
				dao.Collect(type, title, userName);
				return "收藏成功";
			}else if(flag.equals(UnCollected)){
				
				dao.UnCollect(type, title, userName);
				return "取消成功";
			}else{
				return "非法操作";
			}
	
		}
		@RequestMapping(value = "views", method = RequestMethod.GET)
		@ResponseBody
		public  Object views(@RequestParam("type") String type,@RequestParam("user") String userName,@RequestParam("starter") String starter,@RequestParam("space") String space) {
			
			int start = 0;
			int sp =0;
			try{
				start = Integer.parseInt(starter);
			    sp = Integer.parseInt(space);
			}catch(Exception e)
			{
				logger.warn("非法数字传入"+e.toString());
				return null;
			}
			
			
			logger.debug(starter+"---->"+sp);
			List<VOAbs> lis = dao.views(type,userName,start,sp);
			
			System.out.println("结束");
			return lis;
			
		}
	 
	 
	 
}
