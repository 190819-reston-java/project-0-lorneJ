package com.revature.controller;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.model.BAcct;
import com.revature.service.StreamCloser;
import com.revature.service.connectionUtil;
public class UnitTesting {

private static BAcct bAcct = null;
	
	@Before
	public void setUp() {
		bAcct = new BAcct(0, null, 0);
	}
	
	@After
	public void tearDown() {
		bAcct = null;
	}
	
	
	
	@Test
	public void doesFileExist() 
	{
		 File tmpDir = new File("src\\main\\resources\\passUser.txt");
		    boolean exists = tmpDir.exists();
		    if (exists) System.out.println("passUser.txt\t EXISTS \tALIENS");
	}
	
	@Test
	public void createBAcct() 
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		
		
		final String query = "INSERT INTO bacct VALUES (?, ?, ?);";
		try 
		{
			conn = connectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, 78);
			stmt.setString(2, "Peter Maxine");
			stmt.setDouble(3, 10.53);
			stmt.execute();
		}catch(SQLException e) 
		{
			e.printStackTrace();
			
		}
		finally 
		{
			StreamCloser.close(stmt);
			StreamCloser.close(conn);
		}

	}
	
	@Test
	public void deposit() 
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		
		final String query = "UPDATE bacct "
				+ "SET balance = balance + ?"
				+ "WHERE account_id = 1;";
		
		try {
				conn = connectionUtil.getConnection();
				stmt = conn.prepareStatement(query);
				stmt.setDouble(1, 5);
				
				stmt.executeUpdate();
				
		}catch(SQLException e) 
		{
			e.printStackTrace();
			return;
		}
		finally 
		{
			StreamCloser.close(stmt);
			StreamCloser.close(conn);
		}
		return;
	}
	
	@Test
	public void withdraw() 
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		
		final String query = "UPDATE bacct "
				+ "SET balance = balance - ?"
				+ "WHERE account_id = 1;";
		
		try {
				conn = connectionUtil.getConnection();
				stmt = conn.prepareStatement(query);
				stmt.setDouble(1, 5);
				
				stmt.executeUpdate();
				
		}catch(SQLException e) 
		{
			e.printStackTrace();
			return;
		}
		finally 
		{
			StreamCloser.close(stmt);
			StreamCloser.close(conn);
		}
		return;
	}
	
	@Test
	public void addition() 
	{
		double a = 3;
		double b = 8;
		
		if(a + b == 11) 
		{
			return;
		}
		
	}
	
	@Test
	public void subtraction() 
	{
		double a = 3;
		double b = 8;
		
		if(a - b == -5) 
		{
			return;
		}
		
	}
	

}
