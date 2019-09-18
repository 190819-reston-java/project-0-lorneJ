package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.exception.CreatorException;
import com.revature.model.BAcct;
import com.revature.service.connectionUtil;
import com.revature.service.StreamCloser;

public class BAcctImplJDBC implements BAcctDAO 
{

	
	@Override
	public BAcct getBAcct(int id)
	{
		BAcct bAcct = null;
		
		try(Connection conn = connectionUtil.getConnection())
		{
			String query = "SELECT * FROM bacct WHERE account_id = ?;";
			try(PreparedStatement stmt = conn.prepareStatement(query))
			{
				stmt.setLong(1, id);
				if(stmt.execute()) 
				{
					try(ResultSet resultSet = stmt.getResultSet())
					{
						if(resultSet.next()) 
						{
							bAcct = createBAcctFromRS(resultSet);
						}
					}
				}
			}
		}catch(SQLException e) 
		{
			e.printStackTrace();
		}
		
		return bAcct;
		
	}
	
	
	@Override
	public BAcct getBAcct(String name) {
		ResultSet resultSet = null;
		//PreparedStatements are better than simple ones
		PreparedStatement statement = null;
		BAcct bAcct= null;
		
		//try-with-resources:
		//any resource that is AutoClosable(an interface)
		//can be used with try(resource) {}  and it will close itself
		try(Connection conn = connectionUtil.getConnection())
		{
			statement = conn.prepareStatement(
					"SELECT * FROM bacct WHERE username = ?;");
			//in our preparedStatement, we set values to be filled in later with ?
			//we'll set those values using the "index" of the ?, starting at 1.
			
			//fill in the ? with name argument
			statement.setString(1, name);
			if(statement.execute()) 
			{
				//get ResultSet
				resultSet = statement.getResultSet();
				
				//check  for a single row and use it
				if(resultSet.next()) 
				{
					bAcct = createBAcctFromRS(resultSet);
				}
			}
			
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally 
		{
			StreamCloser.close(resultSet);
			StreamCloser.close(statement);
		}
		return bAcct;
	}
	
		
	//@Override
	public List<BAcct> getBAcct() {
		//Statement and ResultSet interfaces
		Statement statement = null;
		ResultSet resultSet = null;
		Connection conn = null;
		
		//List to return
		List<BAcct> bAcct = new ArrayList<BAcct>();
		
		try 
		{
			//get connection from ConnectionUtil:
			conn = connectionUtil.getConnection();
			
			//create statement from connection
			statement = conn.createStatement();
			
			//Statements can execute sql queries:
			//ResultSet stores the results of a query 
			resultSet = statement.executeQuery("SELECT * FROM bacct;");
			
			//loop through resultSet
			while(resultSet.next()) 
			{
				//At each row in the ResultSet
				bAcct.add(createBAcctFromRS(resultSet));
			}
		}catch (SQLException e) 
		{
			e.printStackTrace();
		}finally 
		{
			StreamCloser.close(resultSet);
			StreamCloser.close(statement);
			StreamCloser.close(conn);
		}
		
		return bAcct;
	}
	
	
	@Override
	public boolean createBAcct(BAcct b) 
	{
		try {
		Connection conn = null;
		PreparedStatement stmt = null;
		final String query = "INSERT INTO bacct VALUES (?, ?, ?);";
		try 
		{
			conn = connectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, b.getName());
			stmt.setDouble(2, b.getBal());
			stmt.execute();
		}catch(SQLException e) 
		{
			e.printStackTrace();
			return false;
		}
		finally 
		{
			StreamCloser.close(stmt);
			StreamCloser.close(conn);
		}
		throw new CreatorException("It lives!\tIT LIVES!!");
		}catch(CreatorException ex) 
		{
			System.out.println("They said it couldn't be done");
		}
		return true;
	}
	
	@Override
	public boolean updateBAcct(BAcct b) 
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		
		final String query = "UPDATE bAcct SET username = ?, balance = ? WHERE account_id = ?;";
		
		try 
		{
			conn = connectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, b.getName());
			stmt.setDouble(2, b.getBal());
			
			stmt.execute();
		}catch(SQLException e) 
		{
			e.fillInStackTrace(); 
			return false;
			}finally 
		{
			StreamCloser.close(conn);
			StreamCloser.close(stmt);
		}
		return true;
	}
	
	
	public static void terminateBAcct(int number) 
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		
		final String query = "DELETE FROM bacct "
				+ "WHERE account_id = '1';";
		
		try {
				conn = connectionUtil.getConnection();
				stmt = conn.prepareStatement(query);
				stmt.executeUpdate();
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
	
	public static void deposit(double amount) 
	{
		//UPDATE bacct
		//SET balance = balance + 10
		//WHERE account_id = 1;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		final String query = "UPDATE bacct "
				+ "SET balance = balance + ?"
				+ "WHERE account_id = 1;";
		
		try {
				conn = connectionUtil.getConnection();
				stmt = conn.prepareStatement(query);
				stmt.setDouble(1, amount);
				
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
	
	public static void withdraw(double amount) 
	{
		//UPDATE bacct
		//SET balance = balance - 10
		//WHERE account_id = 1;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		final String query = "UPDATE bacct "
				+ "SET balance = balance - ?"
				+ "WHERE account_id = 1;";
		
		try {
				conn = connectionUtil.getConnection();
				stmt = conn.prepareStatement(query);
				stmt.setDouble(1, amount);
				
				stmt.executeUpdate();
				
		}catch(SQLException e) 
		{
			e.printStackTrace();
			return;
		}finally 
		{
			StreamCloser.close(stmt);
			StreamCloser.close(conn);
		}
		return;
	}
	
	
	//returns a BAcct object created using a single valid ResultSet

		private BAcct createBAcctFromRS(ResultSet resultSet) throws SQLException
		{
			return new BAcct(
					resultSet.getInt("account_id"), 
					resultSet.getString("username"), 
					resultSet.getDouble("balance"));
		}
}
