package SZSE;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ds2 {
	String sql;

	String date;
	String code;
	String type; // in or out
	// String torder;//1-5
	String name;
	String insum;
	String outsum;

	public static void main(String[] args){
		ds2 tmp = new ds2("2014-03-24","000537","detail");
	}
	ds2(String date, String code, String table) {
		this.date = date;
		this.code = code;
//		String url = "http://www.szse.cn/szseWeb/FrontController.szse?ACTIONID=7&SOURCEURL= http://www.szse.cn/szseWeb/FrontController.szse*_QUESTION_*ACTIONID=7*_AND_*AJAX=AJAX-TRUE*_AND_*CATALOGID=1842_xxpl*_AND_*tab1PAGENUM=1*_AND_*TABKEY=tab1*_AND_*txtStart="+date+"*_AND_*txtEnd="
//				+ date
//				+ "&CATALOGID=1842_detal&TABKEY=tab2&DQRQ="
//				+ date
//				+ "&ZQDM=" + code;
		String url = "http://www.szse.cn/szseWeb/FrontController.szse?ACTIONID=7&SOURCEURL= http://www.szse.cn/szseWeb/FrontController.szse*_QUESTION_*ACTIONID=7*_AND_*AJAX=AJAX-TRUE*_AND_*CATALOGID=1842_xxpl*_AND_*tab1PAGENUM=1*_AND_*TABKEY=tab1*_AND_*txtStart=2014-03-24*_AND_*txtEnd=2014-03-24&CATALOGID=1842_detal&TABKEY=tab2&DQRQ=2014-03-24&ZQDM=000537&ZBDM=1001";

		String html = parseHtml.getContent(url);
		Document doc = Jsoup.parse(html);
		Elements trs = doc.select("tr.cls-data-tr");
		int i = 0;
		for (Element tr : trs) {
			String[] results = new String[4];
			Elements tds = tr.select("td");
			int j = 0;
			for (Element td : tds) {
				if (j++ < 4)
					results[j - 1] = td.html().replace(",", "");
				System.out.println(td.html());
			}

			for (j = 0; j < 4; j++) {
				System.out.print(results[j] + " ");
			}
			System.out.println();

			// ds1 tmp = new ds1(results,table);

		}

	}
}