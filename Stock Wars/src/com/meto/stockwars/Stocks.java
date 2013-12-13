import java.util.Random;
public class Stocks 
{
	private double StockPrice;
	private String StockName;
	private double[] StockHistory = new double[121]; 
	
	Stocks(String name, double setprice)
	{
		StockName = name;
		StockPrice = setprice;
	}
	
	public void randomPriceChange()
	{
			Random rand = new Random(); 
			int value = rand.nextInt(4);
			double percent = rand.nextInt(25)+1;
		
			if(value>1)
				StockPrice = StockPrice+((percent/100)*StockPrice);
			else if(value<=1)
			{
				StockPrice = StockPrice-((percent/100)*StockPrice) ;
				if(StockPrice <= 0)
					StockPrice = (-1* StockPrice) + 10;
				}
				
	}
	
	
	public void stockHistory(int day)
	{
	
		StockHistory[day] = StockPrice;
	}
	
	public double getPrice()
	{
		return StockPrice;
	}
	
	
	public String getName()
	{
		return StockName;
	}
	public void setPrice(double price) {
		StockPrice = price;
	}
}
