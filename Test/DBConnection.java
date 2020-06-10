package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DBConnection {
	private static String url = "jdbc:mysql://localhost:3306/userprofile?autoReconnect=true&useSSL=false";
	private static String dbname = "userprofile";
	private static String servername = "localhost";
	private static String user = "root";
	private static String password = "";
	private static int portnumber = 3306;

	public static Connection getDBConnection() {
		
		Connection con = null;
		
		MysqlDataSource datasource = new MysqlDataSource();
		datasource.setServerName(servername);
		datasource.setUser(user);
		datasource.setPassword(password);
		datasource.setDatabaseName(dbname);
		datasource.setPortNumber(portnumber);
		datasource.setUrl("jdbc:mysql://localhost/userprofile?autoReconnect=true&useSSL=false");

		try {
			con = datasource.getConnection();
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			Logger.getLogger(" Get Connection -> " + DBConnection.class.getName()).log(Level.SEVERE, null, ex);
		} 
		
		return con;
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			con = DriverManager.getConnection(url, user, password);
//		} catch (ClassNotFoundException ex) {
//			ex.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return con;
	}

//	public static void main(String[] args) throws SQLException {
//		Connection connection = getDBConnection();
//		
//		if (connection != null) {
//			System.out.println("Success");
//		} else {
//			System.out.println("Failed");
//		}

//		Statement myStmt = connection.createStatement();
//		ResultSet myRs = myStmt.executeQuery("select * from login");
//		while (myRs.next()) {
//			System.out.println(myRs.getString("Username") + myRs.getString("Password") + myRs.getString("IPaddress"));
//		}
//	}

}
