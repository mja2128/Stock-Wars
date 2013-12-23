/**
 * A class to represent a random event happening during game play, which will significantly affect
 * the price of a Stock.
 */
package com.meto.stockwars;

/**
 * @author METO Solutions
 *
 */
public class RandomEvent
{
	private String title;
	private String description;
	private String stockName;
	private float priceChange;
	
	public RandomEvent(String t, String d)
	{
		title = t;
		description = d;
		stockName = "";
		priceChange = 0.0f;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String t)
	{
		title = t;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String d)
	{
		description = d;
	}
	
	public String getStockName()
	{
		return stockName;
	}
	
	public void setStockName(String s)
	{
		stockName = s;
	}
	
	public float getPriceChange()
	{
		return priceChange;
	}
	
	public void setPriceChange(float p)
	{
		priceChange = p;
	}
}
