package SZSE;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class testSQL{
	public static void main(String[] args) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		//String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String JDriver = "com.mysql.jdbc.Driver";
		//String connectDB = "jdbc:sqlserver://10.23.129.106:1433;DatabaseName=WebData";
		String connectDB = "jdbc:mysql://localhost:3306/test";
		//String user = "lxw";
		String user = "root";
		String pwd = "111111";
		//String pwd = "1qaz2wsx";
		try{
			Class.forName(JDriver);
			conn = DriverManager.getConnection(connectDB,user,pwd);
			String sql = "insert into test values (1);";
			String sql1 = "select * from test;";
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			ResultSet rs = stmt.executeQuery(sql1);
			if(rs.next()){
				System.out.println(rs.getInt(1));
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			stmt.close();
			conn.close();
		}
		
	}
}