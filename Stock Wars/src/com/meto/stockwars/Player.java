package com.meto.stockwars;

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
	private double cash;
	private double bankBalance;
	private double debt;
	//private int[] sharesOwned;
	private HashMap<String, Integer> sharesOwned; 
	private int stocksBought;
	private int stocksSold;
	private boolean insiderInfo;
	private boolean stockBroker;
	
	public Player()
	{
		// these are the initial values for these fields
		cash = 2000.00;
		bankBalance = 0.00;
		debt = 5000.00;
		//sharesOwned = new int[15];
		sharesOwned = new HashMap<String, Integer>(15);
		stocksBought = 0;
		stocksSold = 0;
		insiderInfo = false;
		stockBroker = false;
	}
	
	public static enum Field
	{
		CASH, BANKBALANCE, DEBT, STOCKSBOUGHT, STOCKSSOLD
	}
	
	public enum Stock
	{
		STOCK1, STOCK2, STOCK3, STOCK4, STOCK5, STOCK6, STOCK7, STOCK8,
		STOCK9, STOCK10, STOCK11, STOCK12, STOCK13, STOCK14, STOCK15
	}
	
	public double getField(Field field)
	{
		double value = 0;
		switch(field)
		{
			case CASH:
				value = this.cash;
				break;
			case BANKBALANCE:
				value = this.bankBalance;
				break;
			case DEBT:
				value = this.debt;
				break;
			case STOCKSBOUGHT:
				value = this.stocksBought;
				break;
			case STOCKSSOLD:
				value = this.stocksSold;
				break;
		}
		return value;
	}
	
	public int getSharesOwned(String stockName)
	{
		if(sharesOwned.containsKey(stockName))
			return sharesOwned.get(stockName);
		else
			return 0;
	}
	
	/*public int getSharesOwned(Stock stock)
	{
		int value = 0;
		switch(stock)
		{
			case STOCK1:
				value = sharesOwned[0];
				break;
			case STOCK2:
				value = sharesOwned[1];
				break;
			case STOCK3:
				value = sharesOwned[2];
				break;
			case STOCK4:
				value = sharesOwned[3];
				break;
			case STOCK5:
				value = sharesOwned[4];
				break;
			case STOCK6:
				value = sharesOwned[5];
				break;
			case STOCK7:
				value = sharesOwned[6];
				break;
			case STOCK8:
				value = sharesOwned[7];
				break;
			case STOCK9:
				value = sharesOwned[8];
				break;
			case STOCK10:
				value = sharesOwned[9];
				break;
			case STOCK11:
				value = sharesOwned[10];
				break;
			case STOCK12:
				value = sharesOwned[11];
				break;
			case STOCK13:
				value = sharesOwned[12];
				break;
			case STOCK14:
				value = sharesOwned[13];
				break;
			case STOCK15:
				value = sharesOwned[14];
				break;
		}
		return value;
	}*/
	
	public boolean setValue(Field field, double value)
	{
		boolean result = false;
		switch(field)
		{
			case CASH:
				this.cash = value;
				result = true;
				break;
			case BANKBALANCE:
				this.bankBalance = value;
				result = true;
				break;
			case DEBT:
				this.debt = value;
				result = true;
				break;
			case STOCKSBOUGHT:
				this.stocksBought = (int) value;
				result = true;
				break;
			case STOCKSSOLD:
				this.stocksSold = (int) value;
				result = true;
				break;
		}
		return result;
	}
	
	public boolean addToField(Field field, double addAmount)
	{
		boolean result = false;
		switch(field)
		{
			case CASH:
				this.cash += addAmount;
				result = true;
				break;
			case DEBT:
				this.debt += addAmount;
				result = true;
				break;
			case BANKBALANCE:
				this.bankBalance += addAmount;
				result = true;
				break;
			case STOCKSBOUGHT:
				this.stocksBought += addAmount;
				result = true;
				break;
			case STOCKSSOLD:
				this.stocksSold += addAmount;
				result = true;
				break;
		}
		return result;
	}
	
	public boolean subtractFromField(Field field, double subAmount)
	{
		boolean result = false;
		switch(field)
		{
			case CASH:
				this.cash -= subAmount;
				result = true;
				break;
			case DEBT:
				this.debt -= subAmount;
				result = true;
				break;
			case BANKBALANCE:
				this.bankBalance -= subAmount;
				result = true;
				break;
			case STOCKSBOUGHT:
				this.stocksBought -= subAmount;
				result = true;
				break;
			case STOCKSSOLD:
				this.stocksSold -= subAmount;
				result = true;
				break;
		}
		return result;
	}
	
	public void setSharesOwned(String stockName, int amount)
	{
		sharesOwned.put(stockName, amount);	
	}
	
	/*public boolean setSharesOwned(Stock stock, int amount)
	{
		boolean result = false;
		switch(stock)
		{
			case STOCK1:
				sharesOwned[0] = amount;
				result = true;
				break;
			case STOCK2:
				sharesOwned[1] = amount;
				result = true;
				break;
			case STOCK3:
				sharesOwned[2] = amount;
				result = true;
				break;
			case STOCK4:
				sharesOwned[3] = amount;
				result = true;
				break;
			case STOCK5:
				sharesOwned[4] = amount;
				result = true;
				break;
			case STOCK6:
				sharesOwned[5] = amount;
				result = true;
				break;
			case STOCK7:
				sharesOwned[6] = amount;
				result = true;
				break;
			case STOCK8:
				sharesOwned[7] = amount;
				result = true;
				break;
			case STOCK9:
				sharesOwned[8] = amount;
				result = true;
				break;
			case STOCK10:
				sharesOwned[9] = amount;
				result = true;
				break;
			case STOCK11:
				sharesOwned[10] = amount;
				result = true;
				break;
			case STOCK12:
				sharesOwned[11] = amount;
				result = true;
				break;
			case STOCK13:
				sharesOwned[12] = amount;
				result = true;
				break;
			case STOCK14:
				sharesOwned[13] = amount;
				result = true;
				break;
			case STOCK15:
				sharesOwned[14] = amount;
				result = true;
				break;
		}
		return result;
	}*/
	
	/*public boolean addToSharesOwned(Stock stock, int addAmount)
	{
		boolean result = false;
		switch(stock)
		{
			case STOCK1:
				sharesOwned[0] += addAmount;
				result = true;
				break;
			case STOCK2:
				sharesOwned[1] += addAmount;
				result = true;
				break;
			case STOCK3:
				sharesOwned[2] += addAmount;
				result = true;
				break;
			case STOCK4:
				sharesOwned[3] += addAmount;
				result = true;
				break;
			case STOCK5:
				sharesOwned[4] += addAmount;
				result = true;
				break;
			case STOCK6:
				sharesOwned[5] += addAmount;
				result = true;
				break;
			case STOCK7:
				sharesOwned[6] += addAmount;
				result = true;
				break;
			case STOCK8:
				sharesOwned[7] += addAmount;
				result = true;
				break;
			case STOCK9:
				sharesOwned[8] += addAmount;
				result = true;
				break;
			case STOCK10:
				sharesOwned[9] += addAmount;
				result = true;
				break;
			case STOCK11:
				sharesOwned[10] += addAmount;
				result = true;
				break;
			case STOCK12:
				sharesOwned[11] += addAmount;
				result = true;
				break;
			case STOCK13:
				sharesOwned[12] += addAmount;
				result = true;
				break;
			case STOCK14:
				sharesOwned[13] += addAmount;
				result = true;
				break;
			case STOCK15:
				sharesOwned[14] += addAmount;
				result = true;
				break;
		}
		return result;
	}*/
	
	/*public boolean subtractFromSharesOwned(Stock stock, int subAmount)
	{
		boolean result = false;
		switch(stock)
		{
			case STOCK1:
				sharesOwned[0] -= subAmount;
				result = true;
				break;
			case STOCK2:
				sharesOwned[1] -= subAmount;
				result = true;
				break;
			case STOCK3:
				sharesOwned[2] -= subAmount;
				result = true;
				break;
			case STOCK4:
				sharesOwned[3] -= subAmount;
				result = true;
				break;
			case STOCK5:
				sharesOwned[4] -= subAmount;
				result = true;
				break;
			case STOCK6:
				sharesOwned[5] -= subAmount;
				result = true;
				break;
			case STOCK7:
				sharesOwned[6] -= subAmount;
				result = true;
				break;
			case STOCK8:
				sharesOwned[7] -= subAmount;
				result = true;
				break;
			case STOCK9:
				sharesOwned[8] -= subAmount;
				result = true;
				break;
			case STOCK10:
				sharesOwned[9] -= subAmount;
				result = true;
				break;
			case STOCK11:
				sharesOwned[10] -= subAmount;
				result = true;
				break;
			case STOCK12:
				sharesOwned[11] -= subAmount;
				result = true;
				break;
			case STOCK13:
				sharesOwned[12] -= subAmount;
				result = true;
				break;
			case STOCK14:
				sharesOwned[13] -= subAmount;
				result = true;
				break;
			case STOCK15:
				sharesOwned[14] -= subAmount;
				result = true;
				break;
		}
		return result;
	}*/
	
	public boolean isInsiderInfoEnabled()
	{
		return insiderInfo;
	}
	
	public void setInsiderInfo(boolean b)
	{
		insiderInfo = b;
	}
	
	public boolean isStockBrokerEnabled()
	{
		return stockBroker;
	}
	
	public void setStockBroker(boolean b)
	{
		stockBroker = b;
	}
}
