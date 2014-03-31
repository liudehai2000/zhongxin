package SZSE;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection{
	public static com.mysql.jdbc.Connection getConnection(){
		Connection conn = null;
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
			return (com.mysql.jdbc.Connection) conn;
			
		}catch(Exception e)
		{
		}
		return (com.mysql.jdbc.Connection) conn;
		
	}
}