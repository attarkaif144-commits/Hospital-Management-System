package com.learnJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class InsertRecord {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Connection con=null;
		Statement stmt=null;
//		ResultSet rs=null;
		
        String URL="jdbc:mysql://localhost:3306/JDBCMySQL";
		
		String Username="root";
		String Password="kaif@ATTAR1";
		
		try {
			Class.forName("con.mysql.jdbc.driver");
		} catch(ClassNotFoundException e)
		{
			System.out.println("In catch"+e.getMessage());
		}
		try {
			con=DriverManager.getConnection(URL,Username,Password);
			stmt=con.createStatement();
			
		    stmt.execute("insert into Students values (3,'Sangeetha','IT')");
		    stmt.execute("insert into Students values (4,'Saina Nehwal','ECE')");
		    stmt.execute("insert into Students values (3,'Sindhu','EEE')");
		    stmt.execute("insert into Students values (3,'Sania Mirza','Mech')");
		    
		    System.out.println("Sucessfully inserted");
		    
//		    rs.close();
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


