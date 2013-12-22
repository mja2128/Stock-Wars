package com.meto.stockwars;


public class Main {

	public static void main(String[] args) {
		{
			String Tomika = "Tomika Industries";
			Stock stock1 = new Stock(Tomika, 100.00f);
			stock1.addToPriceHistory(1);
			System.out.println("Here is the intial price for" + stock1.getName());
			System.out.println(stock1.getPrice());
			
			System.out.println();
			System.out.print("Now we are gonna change the price of the Stock by setting the value to a different price");
			System.out.println();
			System.out.println();
			stock1.setPrice(120.00f);
			stock1.addToPriceHistory(2);
			
			System.out.println("Here is the price for" + stock1.getName());
			System.out.println(stock1.getPrice());
			
			System.out.print("Now we are gonna change the price of the Stock Randomly");
			System.out.println();
			System.out.println();
			stock1.randomPriceChange();
			
			System.out.println("Here is the price for" + stock1.getName());
			System.out.println(stock1.getPrice());
			}
		}

	}


