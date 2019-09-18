package com.revature.model;

public class BAcct 
{
	
	private int id;
	private String name;
	private double bal;
	
	//auto generated getters, setters, constructor, toString:
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public double getBal() 
	{
		return bal;
	}
	public void setBal(double bal) 
	{
		this.bal = bal;
	}
	
	
	//to String method
	@Override
	public String toString() 
	{
		return "\nBank Account Info [ id = " + id + ", name = " + name + ", bal = " + bal + " ]";
	}
	
	public BAcct(int id, String name, double bal) 
	{
		super();
		this.id = id;
		this.name = name;
		this.bal = bal;
	}
}
