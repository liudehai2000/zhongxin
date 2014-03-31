package SZSE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mysql.jdbc.Connection;


public class ds1{
	static String sql;
	
	String rid;
	String date;
	String code;
	String alias;
	String tradesum;
	String tradeamount;
	String reason;
	float percent;
	ds1(){
		
	}
	ds1(String[] data,String table) {
		this.date = data[0];//==null?"null":data[0];
		this.code = data[1];//==null?"null":data[1];
		this.alias = data[2];//==null?"null":data[2];
		this.tradesum = data[3];//==null?"null":data[3];
		this.tradeamount = data[4];//==null?"null":data[4];
		this.reason = data[5];//==null?"null":data[5];
		this.percent = Float.parseFloat(data[6])/100;
		
		sql = "insert into "+table+" values("+
				"null,'" +
				date+"','"+
				code+"','"+
				alias+"',"+
				tradesum+","+
				tradeamount+",'"+
				reason+"',"+
				percent+");";
		try {
			writeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	static void writeDB() throws SQLException{
		com.mysql.jdbc.Connection conn = dbConnection.getConnection();
		Statement stmt = conn.createStatement();
		//String sql1 = "insert into test values (1);";
		//String sql2 = "select * from test;";
		
		System.out.println(sql);
		stmt.executeUpdate(sql);

//		ResultSet rs = stmt.executeQuery(sql2);
//		if(rs.next()){
//			System.out.println(rs.getInt(1));
//		}
	}
	
}