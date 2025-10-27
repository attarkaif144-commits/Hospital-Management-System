package com.learnJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCPreparedStatement {

	public static void main(String[] args) {
		
		Connection con=null;
//		Statement stmt=null;
		ResultSet rs=null;
		
		String URL="jdbc:mysql://localhost:3306/JDBCMySQL";
		
		String Username="root";
		String Password="kaif@ATTAR1";
		
		String Query="select * from students where name=?";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Loaded!");
		} catch(ClassNotFoundException e)
		{
			System.out.println("Drivers not loaded");
			System.out.println("In catch1"+e.getMessage());
		}
		try {
			con=DriverManager.getConnection(URL,Username,Password);
//			Statement stmt=con.createStatement();
			PreparedStatement preparedStatement=con.prepareStatement(Query);
			preparedStatement.setString(1, "Gita");
			rs=preparedStatement.executeQuery();
			
		
		while(rs.next())
		{
			System.out.println("_________________________");
			
			int id=rs.getInt("id");
			String name=rs.getString("name");
			String dept=rs.getString("dept");
			
			System.out.println("Student ID:"+id);
			System.out.println("Student Name:"+name);
			System.out.println("Student Department:"+dept);
			
			System.out.println("______________________________");
		}
		rs.close();
//		stmt.close();
		con.close();
		}catch(SQLException e)
		{
			System.out.println("In catch"+e.getMessage());
		}
		finally
		{ if(con!=null)
		{
			try {
				rs.close();
//				stmt.close();
				con.close();
				
				System.out.println("Terminated Successfully");
			}
			catch(Exception e)
			{
				System.out.println("Oops! some serious issue");
			}
		}
		}
		// TODO Auto-generated method stub

	}
	}

