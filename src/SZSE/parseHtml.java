package SZSE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			FileOutputStream f = new FileOutputStream("./1.html");

			
//			byte buf[] = new byte[2048000];
			int b;
			while ((b=fStream.read())!=-1) {
				//content.append(new String(buf));
				f.write(b);
			}
			fStream.close();
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			FileInputStream in = new FileInputStream("./1.html");
			//FileOutputStream f = new FileOutputStream("./1.html");
			
			byte[] buf = new byte[1024];
			while(in.read(buf)!=-1){
				content.append(new String(buf));
				//f.write(buf);
			}
			
			in.close();
			//f.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return content.toString();
	}
	
	public static void main(String[] args) throws IOException{
	
		String table = "record";
		String url = "http://www.szse.cn/szseWeb/FrontController.szse?ACTIONID=8&CATALOGID=1842_xxpl&TABKEY=tab1&ENCODE=1&txtEnd=2014-03-24&txtStart=2014-03-24";
		String html = getContent(url);
		//System.out.println(html);
		Document doc = Jsoup.parse(html);
		
		Elements trs = doc.select("tr.cls-data-tr");
		int i=0;
		for(Element tr: trs){
			//if(i++==0)continue;
			String[] results = new String[7];
			Elements tds = tr.select("td");
			
			int j=0;
			for(Element td : tds){
				if(j++<7)results[j-1] = td.html().replace(",","");
				System.out.println(td.html());
			}
			
			
			String regex = "([-]*\\d{1,2}.\\d{0,5})%";
			Pattern pat = Pattern.compile(regex);
			Matcher mat = pat.matcher(results[5]);
			if(mat.find()){
				results[6] = mat.group(1);
			}
			
//			for(j=0;j<7;j++){
//				System.out.print(results[j]+" ");
//			}
//			System.out.println();
			
			ds1 tmp = new ds1(results,table);
			
			




		}
		


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