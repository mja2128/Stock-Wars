package com.meto.stockwars;

import java.math.BigDecimal;
import java.util.Random;

public class Stock
{
	private float price;
	private String name;
	private float[] priceHistory = new float[121]; 
	
	public Stock(String name, float setprice)
	{
		this.name = name;
		price = round(setprice, 2, BigDecimal.ROUND_HALF_UP);
	}
	
	public void randomPriceChange()
	{
		Random rand = new Random(); 
		int value = rand.nextInt(4);
		float percent = rand.nextInt(25)+1;
		
		if(value>1)
			price = round(price + ((percent/100)*price), 2, BigDecimal.ROUND_HALF_UP);
		else if(value<=1)
		{
			price = round(price-((percent/100)*price), 2, BigDecimal.ROUND_HALF_UP) ;
			if(price <= 0)
				price = round((-1* price) + 10, 2, BigDecimal.ROUND_HALF_UP);
		}
				
	}
	
	public void addToPriceHistory(int day)
	{
	
		priceHistory[day] = round(price, 2, BigDecimal.ROUND_HALF_UP);
	}
	
	public float getPrice()
	{
		return round(price, 2, BigDecimal.ROUND_HALF_UP);
	}
	
	public void setPrice(float price)
	{
		this.price = round(price, 2, BigDecimal.ROUND_HALF_UP);
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	private float round(float unrounded, int precision, int roundingMode)
	{
	    BigDecimal bd = new BigDecimal(unrounded);
	    BigDecimal rounded = bd.setScale(precision, roundingMode);
	    return rounded.floatValue();
	}
}
