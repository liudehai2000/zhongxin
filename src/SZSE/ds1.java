package SZSE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

class ds2{
	String sql;
	
	String rid;
	String type; //in or out
	String torder;//1-5
	String name;
	String insum;
	String outsum;
	
	ds2(String[] data,String table){
		rid = data[0];
		type = data[1];
		torder = data[2];
		name = data[3];
		insum = data[4];
		outsum = data[5];
		
		sql = "insert into "+table+" values("+
				rid+","+
				type+","+
				torder+","+
				name+","+
				insum+","+
				outsum+");";
	}
	
	
}

public class ds1{
	String sql;
	
	String rid;
	String date;
	String code;
	String alias;
	String tradesum;
	String tradeamount;
	String reason;
	String percent;
	ds1(){
		
	}
	ds1(String[] data,String table){
		this.rid = data[0];
		this.date = data[1];
		this.alias = data[2];
		this.tradesum = data[3];
		this.tradeamount = data[4];
		this.reason = data[5];
		this.percent = data[6];
		
		sql = "insert into "+table+" values("+
				rid+","+
				date+","+
				code+","+
				alias+","+
				tradesum+","+
				tradeamount+","+
				reason+","+
				percent+");";
		
	}
	static void writeDB() throws SQLException{
		com.mysql.jdbc.Connection conn = dbConnection.getConnection();
		Statement stmt = conn.createStatement();
		String sql1 = "insert into test values (1);";
		String sql2 = "select * from test;";
		stmt.executeUpdate(sql1);
		ResultSet rs = stmt.executeQuery(sql2);
		if(rs.next()){
			System.out.println(rs.getInt(1));
		}
	}
	public static void main(String[] args) throws SQLException{
		ds1.writeDB();
	}

	
}