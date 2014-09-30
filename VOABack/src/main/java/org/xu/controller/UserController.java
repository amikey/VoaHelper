package org.xu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xu.dao.DaoAbs;
import org.xu.dao.UserDao;





@Controller
@RequestMapping ( "/user" )  
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private UserDao dao = new UserDao();
	
	/*
	 * µÇÂ½¹¦ÄÜ
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	@ResponseBody
	public String login(@RequestParam("name") String name,@RequestParam("pass") String pass)
	{
		
		if(dao.login(name,pass))
		{
			return "success";
		
		}else{
			return  "fail";
		}
		
	}
	
	/*
	 * ¸ü°®ÃÜÂë
	 */
	@RequestMapping(value = "Pass", method = RequestMethod.GET)
	@ResponseBody
	public String  Pass(@RequestParam("name") String name,@RequestParam("pass") String pass)
	{
		
		if(dao.chngPass(name, pass))
		{
			return "success";
		
		}else{
			return  "fail";
		}
	}
	
	
	
}
