package SZSE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class UrlImageUtils {
	/**
	 * 鐠囪褰囩紒娆忕暰閸ュ墽澧栭弬鍥︽閻ㄥ嫬鍞寸�鐧哥礉閻⑩�ileInputStream
	 * @param filePath
	 * @return
	 */
	public static byte[] readPicture(String filePath) {
		byte[] arr = null;
		FileInputStream fReader = null;
		try {
			File file = new File(filePath);
			fReader = new FileInputStream(file);
			arr = new byte[1024*100];
			fReader.read(arr);
		} catch (Exception  e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (fReader != null) {
				try {
					fReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return arr;
	}
	
	/**
	 * 閺嶈宓乥yte閺佹壆绮嶉敍灞藉灡瀵よ桨绔村鐘虫煀閸ヤ勘锟�	 * @param newFileName
	 * @param b
	 */
	public static void writePicture(String newFileName, byte[] b){
		try {
			File file = new File(newFileName);
			FileOutputStream fStream = new FileOutputStream(file);
			fStream.write(b);
			fStream.close();
			System.out.println("閸ュ墽澧栭崚娑樼紦閹存劕濮�   " + b.length);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 閼惧嘲褰囬幐鍥х暰缂冩垵娼冮惃鍕禈閻楀浄绱濇潻鏂挎礀閸忕Ωyte[]
	 * @param strUr1
	 * @return
	 */
	public static byte[] readPictureFromInternet(String strUr1){
		byte[] imgData = null;
		URL url;
		try {
			url = new URL(strUr1);
			URLConnection connection = url.openConnection();
			int length = (int)connection.getContentLength();
			InputStream is = connection.getInputStream();
			if (length!=-1) {
				imgData = new byte[length];
				byte[] temp = new byte[500*1024];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp))>0) {
					System.arraycopy(temp, 0, imgData, destPos, readLen);
					//arraycopy(Object src, int srcPos, Object dest, int destPos, int length) 
					//娴犲孩瀵氱�姘爱閺佹壆绮嶆稉顓烆槻閸掓湹绔存稉顏呮殶缂佸嫸绱濇径宥呭煑娴犲孩瀵氱�姘辨畱娴ｅ秶鐤嗗锟筋潗閿涘苯鍩岄惄顔界垼閺佹壆绮嶉惃鍕瘹鐎规矮缍呯純顔剧波閺夛拷					destPos+=readLen;
				}
			}
			return imgData;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imgData;
	}
	
	/**
	 * 閻╁瓨甯撮懢宄板絿閹稿洤鐣剧純鎴濇絻閻ㄥ嫬娴橀悧鍥风礉娣囨繂鐡ㄩ崚鐗堟瀮娴犳湹鑵�	 * @param filePath
	 * @param strUr1
	 */
	public static void downloadFromInternet(String filePath, String strUr1){
		try {
			URL url = new URL(strUr1);
			InputStream fStream = url.openConnection().getInputStream();
			int b = 0;
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			while ((b=fStream.read())!=-1) {
				fos.write(b);
			}
			fStream.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("fxk");
		downloadFromInternet("D:/1.xls", "http://www.szse.cn/szseWeb/FrontController.szse?ACTIONID=8&CATALOGID=1842_xxpl&TABKEY=tab1&ENCODE=1&txtEnd=2014-03-27&txtStart=2014-03-27");
	}
}
