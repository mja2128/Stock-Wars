package com.meto.stockwars;

/**
 * A class to test the functionality of the Player class.
 */

/**
 * @author METO Solutions
 *
 */
public class PlayerTester
{
	private static Player player = new Player();
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		getFieldTest();
		setValueTest();
	}
	
	private static void getFieldTest()
	{
		boolean pass = true;
		if(player.getField(Player.Field.CASH) != 2000.00)
			pass = false;
		if(player.getField(Player.Field.DEBT) != 5000.00)
			pass = false;
		if(player.getField(Player.Field.BANKBALANCE) != 0)
			pass = false;
		if(player.getField(Player.Field.STOCKSBOUGHT) != 0)
			pass = false;
		if(player.getField(Player.Field.STOCKSSOLD) != 0)
			pass = false;
		if(pass)
			System.out.println("getFieldTest: Pass");
		else
			System.out.println("getFieldTest: Fail");
	}
	
	private static void setValueTest()
	{
		// setting fields to arbitrary values to make sure this function works
		boolean pass = true;
		if(!player.setValue(Player.Field.CASH, 55.80f))
			pass = false;
		if(player.getField(Player.Field.CASH) != 55.80)
			pass = false;
		if(!player.setValue(Player.Field.DEBT, 7856.60f))
			pass = false;
		if(player.getField(Player.Field.DEBT) != 7856.60)
			pass = false;
		if(!player.setValue(Player.Field.BANKBALANCE, 2380.80f))
			pass = false;
		if(player.getField(Player.Field.BANKBALANCE) != 2380.80)
			pass = false;
		if(!player.setValue(Player.Field.STOCKSBOUGHT, 76))
			pass = false;
		if(player.getField(Player.Field.STOCKSBOUGHT) != 76)
			pass = false;
		if(!player.setValue(Player.Field.STOCKSSOLD, 37))
			pass = false;
		if(player.getField(Player.Field.STOCKSSOLD) != 37)
			pass = false;
		if(pass)
			System.out.println("setValueTest: Pass");
		else
			System.out.println("setValueTest: Fail");
	}
}
