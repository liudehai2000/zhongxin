package SZSE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class parseHtml{
	public static String getContent(String strUr1){
		StringBuffer content = new StringBuffer();
		try {
			URL url = new URL(strUr1);
			InputStream fStream = url.openConnection().getInputStream();
			byte buf[] = new byte[1024];
			while ((fStream.read(buf))!=-1) {
				content.append(new String(buf));
			}
			fStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}
	public static void main(String[] args){
//		String url = "http://www.szse.cn/szseWeb/FrontController.szse?ACTIONID=8&CATALOGID=1842_xxpl&TABKEY=tab1&ENCODE=1&txtEnd=2014-03-30&txtStart=2014-03-26";
//		String html = getContent(url);
//		//System.out.println(html);
//		Document doc = Jsoup.parse(html);
//		Elements trs = doc.select("tr");
//		for(Element tr: trs){
//			Elements tds = tr.select("td");
//			tds.get(5)
//
//
//		}
	}

}
//
//String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
//Document doc = Jsoup.parse(html);
//Element link = doc.select("a").first();
//
//String text = doc.body().text(); // "An example link"
//String linkHref = link.attr("href"); // "http://example.com/"
//String linkText = link.text(); // "example""
//
//String linkOuterH = link.outerHtml(); 
//    // "<a href="http://example.com"><b>example</b></a>"
//String linkInnerH = link.html(); // "<b>example</b>"