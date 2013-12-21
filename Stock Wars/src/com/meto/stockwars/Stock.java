package com.meto.stockwars;
import java.util.Random;

public class Stock
{
	private double price;
	private String name;
	private double[] priceHistory = new double[121]; 
	
	public Stock(String name, double setprice)
	{
		this.name = name;
		price = setprice;
	}
	
	public void randomPriceChange()
	{
		Random rand = new Random(); 
		int value = rand.nextInt(4);
		double percent = rand.nextInt(25)+1;
		
		if(value>1)
			price = price+((percent/100)*price);
		else if(value<=1)
		{
			price = price-((percent/100)*price) ;
			if(price <= 0)
				price = (-1* price) + 10;
		}
				
	}
	
	public void stockHistory(int day)
	{
	
		priceHistory[day] = price;
	}
	
	public double getPrice()
	{
		return price;
	}
	
	public void setPrice(double price)
	{
		this.price = price;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
}
