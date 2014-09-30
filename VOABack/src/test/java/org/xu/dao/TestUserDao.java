package org.xu.dao;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestUserDao {


		UserDao dao = null;
		
		private static Logger logger = LoggerFactory.getLogger(TestUserDao.class);

		
		@Before
		public void setUp()
		{
			//这里性能 怎么样？？？？
			dao  = new UserDao();
		}

		
//		@Test
//		public void testAdd()
//		{
//			
//			String name = "1723262513@qq.com";
//			String pass = "xujianhai";
//			 dao.addUser(name,pass);
//			 
//			 assert(dao.login(name, pass));
//			 if(dao.login(name, pass))
//			 {
//				 logger.debug("成功登陆");
//			 }else{
//				 logger.debug("登陆失败");
//			 }
//		}
//		@Test
//		public void testLogin()
//		{
//			dao.login("xujianhai","xujianhai");
//		}

		//测试修改密码的功能
		@Test
		public void testPass()
		{
			
			String name = "1723262513@qq.com";
			String pass = "xujianhai";
			 dao.chngPass(name, pass);
			 
			 assert(dao.login(name, pass));
			 if(dao.login(name, pass))
			 {
				 logger.debug("成功登陆");
			 }else{
				 logger.debug("登陆失败");
			 }
		}
		
		@After
		public void tearDown()
		{
			logger.debug("tear down");
		}
		
}
