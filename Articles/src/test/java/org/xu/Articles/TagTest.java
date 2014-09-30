package org.xu.Articles;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.OptionTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.junit.Test;

public class TagTest {

	@Test
	public void testSelect() {
		NodeFilter inputFilter = new NodeClassFilter(InputTag.class);
		NodeFilter selectFilter = new NodeClassFilter(SelectTag.class);
		NodeList nodeList = null;

		try {
			Parser parser = new Parser();
			parser.setInputHTML("<head><title>OrFilter Test</title>"
					+ "<link href=’/test01/css.css’ text=’text/css’ rel=’stylesheet’ />"
					+ "<link href=’/test02/css.css’ text=’text/css’ rel=’stylesheet’ />"
					+ "</head>"
					+ "<body>"
					+ "<input type=’text’ value=’text1′ name=’text1′/>"
					+ "<input type=’text’ value=’text2′ name=’text2′/>"
					+ "<select><option value='hello1' id=’1′>1</option><option value='hello2' id=’2′>2</option><option value='hello3' id=’3′>3</option></select>"
					+ "<a href=’http://www.yeeach.com’>yeeach.com</a>"
					+ "</body>");
			
			 		parser.setEncoding(parser.getEncoding());
			
			
			
			nodeList = parser.extractAllNodesThatMatch(new NodeClassFilter(OptionTag.class));
			
			for (int i = 0; i <= nodeList.size(); i++) {
				if (nodeList.elementAt(i) instanceof OptionTag) {
					OptionTag tag = (OptionTag) nodeList.elementAt(i);
					
					
					
						System.out.println("OrFilter tag name is :"
								+ tag.getTagName() + " ,tag html is:"
								+ tag.toHtml()+",tag text="+tag.toPlainTextString()+",value="+tag.getAttribute("value"));
					
					
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}
}
