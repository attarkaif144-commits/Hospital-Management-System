package com.learnJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
//import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUpdate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con=null;
		Statement stmt=null;
//		ResultSet rs=null;
		
		String URL="jdbc:mysql://localhost:3306/JDBCMySQL";
		
		String Username="root";
		String Password="kaif@ATTAR1";
				
		String UpdateRecord="Update students set name='Ram' where id=2";
		
		try {
			Class.forName("con.mysql.jdbc.driver");
		} catch(ClassNotFoundException e)
		{
			System.out.println("In catch"+e.getMessage());
		}
		try {
			con=DriverManager.getConnection(URL,Username,Password);
			stmt=con.createStatement();
			stmt.execute(UpdateRecord);
			System.out.println("yay! Table Created");
//			rs.close();
			stmt.close();
			con.close();
		} catch(SQLException e)
		{
			System.out.println("In catch"+e.getMessage());
		}
		finally
		{
			if(con!=null)
			{
				try {
//					rs.close();
					stmt.close();
					con.close();
					System.out.println("Terminated Successfully");
				}
				catch(Exception e)
				{
					System.out.println("Oops! some serious issue");
				}
			}
		}

	}

}
