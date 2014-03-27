package SZSE;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class webPage{
	String url;
	String cookie;
	public String result=null;
	public webPage(){
		
	}
	public webPage(String url, String cookie){
		this.url = url;
		this.cookie = cookie;
		this.getResult();
	}
	
	public void setUrl(String s){
		url = s;
	}

	public void printResult(){
		System.out.println(result);
	}
	public void getResult(){
		GetMethod getMethod;
		HttpClient httpClient = new HttpClient();
		int retry1 = 3;
		L: //bad network	
		while (retry1 > 0) {
			getMethod = new GetMethod(url);
			getMethod.setRequestHeader("Accept-Encoding","gzip, deflate,sdch");
			getMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
			getMethod.setRequestHeader("Connection", "Keep-Alive");
			getMethod.setRequestHeader("Host","www.szse.cn");
			getMethod.setRequestHeader("Proxy-Connection","keep-alive");
			getMethod.setRequestHeader("Referer","http://www.szse.cn/main/disclosure/news/xxlb/");
			getMethod
					.setRequestHeader(
							"User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36");
			getMethod.setRequestHeader("Accept-Encoding", "sdch");
			//getMethod.setRequestHeader("Accept-Encoding", "deflate");
			
			try {
        		int statusCode = httpClient.executeMethod(getMethod);
				if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
						|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
					Header locationHeader = getMethod
							.getResponseHeader("location");
					String location = null;
					if (locationHeader != null) {
						location = locationHeader.getValue();
						System.out.println("The page was redirected to:"
								+ location);
					} else {
						System.err.println("Location field value is null.");
					}
					return;
				}
				if (statusCode != HttpStatus.SC_OK) {
					System.err.println("Method failed: "
							+ getMethod.getStatusLine());
					return;
				}

				//byte[] responseBody = unGZip(getMethod.getResponseBody());
				//byte[] responseBody = getMethod.getResponseBody();
				
				InputStream stream = getMethod.getResponseBodyAsStream();
				Workbook book = Workbook.getWorkbook(stream);
				Sheet sheet = book.getSheet(0);
				Cell cell = sheet.getCell(0,0);
				String result = cell.getContents();
				System.out.println(result);

				stream.close();



			} catch (HttpException e) {
				System.out
						.println("Please check your provided http address!");
				e.printStackTrace();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				retry1 = retry1 - 1;
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("bad network!");
				if(true)continue L;
				try {
					Thread.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				}
				retry1 = retry1 - 1;
				continue;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("not connected to the Internet!");
				if(true)continue L;
				try {
					Thread.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				retry1 = retry1 - 1;
				continue;
			}finally {
				getMethod.releaseConnection();
			}
			break;
		}
		getMethod=null;
		httpClient =null;
	}
	public static void main(String[] args){
		String url = "http://www.szse.cn/szseWeb/FrontController.szse?ACTIONID=8&CATALOGID=1842_xxpl&TABKEY=tab1&ENCODE=1&txtEnd=2014-03-27&txtStart=2014-03-27";
		webPage page = new webPage(url,null);
		//System.out.println(page.result);
	}
}