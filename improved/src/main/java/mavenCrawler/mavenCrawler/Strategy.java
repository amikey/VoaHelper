package mavenCrawler.mavenCrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;

/*
 * 采用策略模式
 * 同样 都是讲  InputStream 转换为  StringBuilder
 * 根据不同的 要求  ，存在 不同的 解析策略
 */
public class Strategy {

	@SuppressWarnings("finally")
	public static StringBuilder getPageList(InputStream stream, String filter) {

		String builder = null;
		StringBuilder result = null;
		if (stream == null)
			return null;

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));

		try {
			while ((builder = reader.readLine()) != null) {
				if (builder.contains(filter)) {
					result = new StringBuilder(builder);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stream.close();
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

				return result;

			}
		}
	}

	@SuppressWarnings("finally")
	public static StringBuilder getList(InputStream stream) {

		String str = null;
		StringBuilder builder = null;		
		if (stream == null)
			return null;

		BufferedReader reader = null;
		builder = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(stream));
			int k = 0;
			while ((str = reader.readLine()) != null) {
				if (k < 10) {
					k++;
					continue;
				}
				builder.append(str);
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			reader.close();
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder;
	}

}
