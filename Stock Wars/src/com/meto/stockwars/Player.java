package com.meto.stockwars;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * A class to represent the player in the Stock Wars game.
 */

/**
 * @author METO Solutions
 *
 */
public class Player
{
	private float cash;
	private float bankBalance;
	private float debt;
	private HashMap<String, Integer> sharesOwned;
	private HashMap<String, Integer> sharesPurchased;
	private int insiderInfoDay;
	private boolean stockBroker;
	
	public Player()
	{
		// these are the initial values for these fields
		cash = 2000.00f;
		bankBalance = 0.00f;
		debt = 5000.00f;
		sharesOwned = new HashMap<String, Integer>(15);
		sharesPurchased = new HashMap<String, Integer>(15);
		insiderInfoDay = 0;
		stockBroker = false;
	}
	
	public static enum Field
	{
		CASH, BANKBALANCE, DEBT
	}
	
	public float getField(Field field)
	{
		float value = 0.00f;
		switch(field)
		{
			case CASH:
				value = round(this.cash, 2, BigDecimal.ROUND_HALF_UP);
				break;
			case BANKBALANCE:
				value = round(this.bankBalance, 2, BigDecimal.ROUND_HALF_UP);
				break;
			case DEBT:
				value = round(this.debt, 2, BigDecimal.ROUND_HALF_UP);
				break;
		}
		return value;
	}
	
	public int getSharesPurchased(String stockName)
	{
		if(sharesPurchased.containsKey(stockName))
			return sharesPurchased.get(stockName);
		else
			return 0;
	}
	
	public int getSharesOwned(String stockName)
	{
		if(sharesOwned.containsKey(stockName))
			return sharesOwned.get(stockName);
		else
			return 0;
	}
	
	public boolean setValue(Field field, float value)
	{
		boolean result = false;
		switch(field)
		{
			case CASH:
				this.cash = round(value, 2, BigDecimal.ROUND_HALF_UP);
				result = true;
				break;
			case BANKBALANCE:
				this.bankBalance = round(value, 2, BigDecimal.ROUND_HALF_UP);
				result = true;
				break;
			case DEBT:
				this.debt = round(value, 2, BigDecimal.ROUND_HALF_UP);
				result = true;
				break;
		}
		return result;
	}
	
	public boolean addToField(Field field, float addAmount)
	{
		boolean result = false;
		switch(field)
		{
			case CASH:
				this.cash += round(addAmount, 2, BigDecimal.ROUND_HALF_UP);
				result = true;
				break;
			case DEBT:
				this.debt += round(addAmount, 2, BigDecimal.ROUND_HALF_UP);
				result = true;
				break;
			case BANKBALANCE:
				this.bankBalance += round(addAmount, 2, BigDecimal.ROUND_HALF_UP);
				result = true;
				break;
		}
		return result;
	}
	
	public boolean subtractFromField(Field field, float subAmount)
	{
		boolean result = false;
		switch(field)
		{
			case CASH:
				this.cash -= round(subAmount, 2, BigDecimal.ROUND_HALF_UP);
				result = true;
				break;
			case DEBT:
				this.debt -= round(subAmount, 2, BigDecimal.ROUND_HALF_UP);
				result = true;
				break;
			case BANKBALANCE:
				this.bankBalance -= round(subAmount, 2, BigDecimal.ROUND_HALF_UP);
				result = true;
				break;
		}
		return result;
	}
	
	public void setSharesOwned(String stockName, int amount)
	{
		sharesOwned.put(stockName, amount);	
	}
	
	public void addToSharesPurchased(String stockName, int amount)
	{
		int current = getSharesPurchased(stockName);
		sharesPurchased.put(stockName, current+amount);
	}
	
	public int insiderInfoDay()
	{
		return insiderInfoDay;
	}
	
	public void setInsiderInfoDay(int d)
	{
		insiderInfoDay = d;
	}
	
	public boolean isStockBrokerEnabled()
	{
		return stockBroker;
	}
	
	public void setStockBroker(boolean b)
	{
		stockBroker = b;
	}
	
	private float round(float unrounded, int precision, int roundingMode)
	{
	    BigDecimal bd = new BigDecimal(unrounded);
	    BigDecimal rounded = bd.setScale(precision, roundingMode);
	    return rounded.floatValue();
	}
}
