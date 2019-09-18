package com.revature.service;
//Connects DB to Java 

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class connectionUtil {
	
	private static Connection conn = null;
	
	public static Connection getConnection() 
	{
		try 
		{
			//Boilerplate to work with Properties
			Properties props = new Properties();
			
			//the following lines just ensure we find connectionProperties
			//regardless of how our project is built
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			props.load(loader.getResourceAsStream("connectionProperties"));
			
			//All we've done is set these values to the values found in
			//connection.properties
			String url = props.getProperty("url");
			String username = props.getProperty("username");
			String password = props.getProperty("password");
			
			//how to actually make connections in JDBC
			conn = DriverManager.getConnection(url, username, password);
		}catch(IOException e)
		{
			e.printStackTrace();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return conn;
	}
}
