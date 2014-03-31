package crawl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

public class webPageRuc{
	String url;
	String cookie;
	public String result=null;
	
	public static void main(String[] args) throws IOException{
    	Cookie cookie = new Cookie();
    	String cookie_string=cookie.getCookie();
    	String url_string="http://portal.ruc.edu.cn/idc/education/selectcourses/schedulequery/ScheduleQueryAction.do";

		webPageRuc test = new webPageRuc(url_string,cookie_string);
	}
	public webPageRuc(){
		
	}
	public webPageRuc(String url, String cookie){
		this.url = url;
		this.cookie = cookie;
		this.getResult();
	}
	
	public void setUrl(String s){
		url = s;
	}


	public void setCookie(String s){
		cookie = s;
	}
	public void printResult(){
		System.out.println(result);
	}
	public static byte[] unGZip(byte[] data) throws Exception{
		byte[] b = null;
		//System.out.println(data);
		ByteArrayInputStream bis = null;
		GZIPInputStream gzip = null;
		ByteArrayOutputStream baos = null;
		try {
			bis = new ByteArrayInputStream(data);
			gzip = new GZIPInputStream(bis);
			byte[] buf = new byte[1024];
			int num = -1;
			baos = new ByteArrayOutputStream();
			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			b = baos.toByteArray();
			baos.flush();
			
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		finally
		{
		    if (baos != null)
				baos.close();
			if (gzip != null)
				gzip.close();
			if (bis != null)
				bis.close();
		}
		return b;
	}
	
	public void getResult(){
		GetMethod getMethod;
		HttpClient httpClient = new HttpClient();
		int retry1 = 3;
		L: //bad network	
		while (retry1 > 0) {
			getMethod = new GetMethod(url);
			getMethod.setRequestHeader("Referer",
					"http://portal.ruc.edu.cn/portalhtml/rd/wdzm/zzfwxs.htm");
			// getMethod.setRequestHeader("Accept-Encoding","gzip, deflate");
			getMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
			getMethod.setRequestHeader("Connection", "Keep-Alive");
			getMethod
					.setRequestHeader(
							"User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
			getMethod.setRequestHeader("Accept-Encoding", "deflate");
			getMethod.setRequestHeader("Host", "cwc.ruc.edu.cn");
			getMethod.setRequestHeader("Cookie", cookie);
			

			System.out.println("sending cookie!");
			
			try {
        		int statusCode = httpClient.executeMethod(getMethod);
				//System.out.println("**********"+statusCode);

				// 301����302
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
				}

				byte[] responseBody = getMethod.getResponseBody();
				
			

				
				result = new String(responseBody);
				//System.out.println(result);
				//result = new String(result.getBytes(),"utf-8");
				System.out.println(result);
//				String result1 = new String(response);
//				result1 = new String(result1.getBytes(),"utf-8");
//				System.out.println(response);

			} catch (HttpException e) {
				System.out
						.println("Please check your provided http address!");
				e.printStackTrace();
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				retry1 = retry1 - 1;
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("bad network!");
				if(true)continue L;
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//					
//				}
//				retry1 = retry1 - 1;
//				continue;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("not connected to the Internet!");
				if(true)continue L;
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
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
}