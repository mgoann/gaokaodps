package com.gaokaoyizhantong.dpstools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Random;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

public class HttpUtil {
	public static void main(String[] args) throws HttpException, IOException,
			ParserException {
		getDataFromServer();
	}

	public static void getDataFromServer() throws HttpException, IOException,
			ParserException {
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost("http://www.gaokaogps.com", 80);
		Date date = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
		Cookie cookie1 = new Cookie(".www.gaokaogps.com", "AJSTAT_ok_pages",
				"34", "/", date, false);
		Cookie cookie2 = new Cookie(".www.gaokaogps.com", "AJSTAT_ok_times",
				"4", "/", date, false);
		Cookie cookie3 = new Cookie(".gaokaogps.com",
				"Hm_lpvt_6078846d157d8a8c9a86e68a7c03ff67", "1403532042", "/",
				date, false);
		Cookie cookie4 = new Cookie(".www.gaokaogps.com",
				"Hm_lvt_6078846d157d8a8c9a86e68a7c03ff67",
				"1403167909,1403491716", "/", date, false);
		Cookie cookie6 = new Cookie(
				".gaokaogps.com",
				"user",
				"email=v1851113&name=%e6%9c%b1%e6%99%af%e4%b8%80&sex=%e5%a5%b3&cate=%e8%92%99%e6%8e%88%e7%90%86%e7%a7%91&userid=146709",
				"/", date, false);
		client.getState().addCookies(
				new Cookie[] { cookie1, cookie2, cookie3, cookie4, cookie6 });
		for (int j = 2011; j <= 2013; j++) {
			for (int i = 1; i <= 10; i++) {
				GetMethod method = getMethod(i, j);
				int status = client.executeMethod(method);
				if (status != 200) {
					System.out.println("status error=" + status + "\n"
							+ method.getResponseBodyAsString());
				}
				InputStream is = method.getResponseBodyAsStream();
				FileUtils.copyInputStreamToFile(is, new File(
						"E:\\qipfpersonal\\gaokaoDPS\\dpstools\\data\\蒙授理科\\"
								+ j + "\\" + i + ".html"));

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				// String responseString = method.getResponseBodyAsString();
				//
				// Parser parser = Parser.createParser(responseString,
				// "GB2312");
				//
				// MyVistor vistor = new MyVistor();
				// parser.visitAllNodesWith(vistor);
				// Tag parentTag = vistor.getTag();
			}
		}
	}

	public static GetMethod getMethod(int pageNum, int year) {
		GetMethod post = new GetMethod(
				"http://www.gaokaogps.com/index_yxsj.aspx?currPage=" + pageNum
						+ "&action=fenshu&bscore=720&escore=0&year=" + year);
		post.setRequestHeader("Connection", "Keep-Alive");
		post.setRequestHeader(
				"Referer",
				"http://www.gaokaogps.com/index_yxsj.aspx?currPage=2&action=fenshu&bscore=720&escore=0&year=2013&J_ScoreProvince=&pici=");
		post.setRequestHeader(
				"User-Agent",
				"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET4.0C; .NET4.0E; InfoPath.2; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
		post.setRequestHeader("User-Agent",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.setRequestHeader("Accept-Language",
				"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		return post;
	}

}
