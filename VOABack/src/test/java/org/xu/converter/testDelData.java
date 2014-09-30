package org.xu.converter;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.mongodb.DBObject;

public class testDelData {

	
static Map<String,String>  MONS = new HashMap<String,String>();
	
	static{
		MONS.put("Jan", "01");MONS.put("Feb", "02");MONS.put("Mar", "03");MONS.put("Apr", "04");
		MONS.put("May", "05");MONS.put("Jun", "06");MONS.put("Jul", "07");MONS.put("Aug", "08");
		MONS.put("Sep", "09");MONS.put("Oct", "10");MONS.put("Nov", "11");MONS.put("Dec", "12");
	}
	
	@Test
	public void testDel()
	{
		DelData fun = new DelData(){

			@Override
			public boolean del_jus(DBObject object) {
				
				// TODO Auto-generated method stub
				
				String source = object.get("date").toString();
				if(source.contains("-")){
				
					System.out.println("date"+source);
					String[]  Dt_ars = source.split("-");
				 
					String  identify = Dt_ars[1];
				
					if(identify.length()==1)
						return true;
				}
				
				return false;
			}
			
		};
		
		fun.Del();
	}
	
//	@Test
//	public void testDel2()
//	{
//		DelData fun = new DelData(){
//
//			@Override
//			public boolean del_jus(DBObject object) {
//				// TODO Auto-generated method stub
////				for(String value: MONS.values())
//				if(object.get("date").toString().contains("Nov")||object.get("date").toString().contains("Jun")
//						||object.get("date").toString().contains("Jul")
//						||object.get("date").toString().contains("Aug"))
//				{
//					return true;
//				}else{
//				return false;
//			}
//			
//		}
//	};
//		fun.Del();
		
	
//	@Test
//	public void testDel2()
//	{
//		DelData fun = new DelData(){
//
//			@Override
//			public boolean del_jus(DBObject object) {
//				// TODO Auto-generated method stub
//				return object.get("date").toString().contains("/");
//			}
//			
//		};
//		fun.Del();
//		
//	}
	
//	}	
}
