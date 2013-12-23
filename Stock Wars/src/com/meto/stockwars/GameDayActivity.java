package com.meto.stockwars;

import java.util.Random;

import com.meto.stockwars.Player.Field;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class GameDayActivity extends Activity {
	public static Player player;
	public static Stock[] stocks;
	public static int currentDay;
	public static int game_length;
	public static RandomEvent[] randomEvents;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initNewGame();
		Intent intent = getIntent();
		game_length = intent.getIntExtra(NewGameDialogFragment.EXTRA_GAME_LENGTH, 30);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_day);
		setTitle(R.string.stock_wars);
		updateDisplay();
	}
	
	public void updateDisplay()
	{
		ListView stocksListView = (ListView) findViewById(R.id.stocksListView);
		StockAdapter stockAdapter = new StockAdapter(stocks, this);
		stocksListView.setAdapter(stockAdapter);
		TextView cashTextView = (TextView) findViewById(R.id.cashTextView);
		cashTextView.setText("Cash: $" + String.valueOf(player.getField(Field.CASH)));
		TextView bankTextView = (TextView) findViewById(R.id.bankTextView);
		bankTextView.setText("Bank: $" + String.valueOf(player.getField(Field.BANKBALANCE)));
		TextView dayTextView = (TextView) findViewById(R.id.dayTextView);
		dayTextView.setText("Day: " + currentDay);
		TextView debtTextView = (TextView) findViewById(R.id.debtTextView);
		debtTextView.setText("Debt: " + player.getField(Field.DEBT));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_day, menu);
		return true;
	}
	
	public void onAdvanceButton(View view)
	{
		if(currentDay <= game_length)
		{
			possiblyCaught();
			boolean randomEvent = false;
			Random rand = new Random();
			// 1/6 chance of a random event
			if(rand.nextInt(6)+1 == 4)
				randomEvent = true;
			
			int stockIndex = 0;
			if(randomEvent)
			{
				int eventIndex = rand.nextInt(randomEvents.length);
				stockIndex = rand.nextInt(stocks.length);
				randomEvents[eventIndex].setStockName(stocks[stockIndex].getName());
				float currentPrice = stocks[stockIndex].getPrice();
				stocks[stockIndex].setPrice(currentPrice*randomEvents[eventIndex].getPriceChange() + currentPrice);
				stocks[stockIndex].addToPriceHistory(currentDay);
				LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View popupView = layoutInflater.inflate(R.layout.randomeventpopup, null);  
			    final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,  LayoutParams.WRAP_CONTENT);
			    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
			    String randomEventTitle = randomEvents[eventIndex].getTitle();
			    TextView randomEventTitleText = (TextView) popupView.findViewById(R.id.randomEventTitle);
			    randomEventTitleText.setText(randomEventTitle);
			    String randomEventCompany = randomEvents[eventIndex].getStockName();
			    TextView randomEventCompanyText = (TextView) popupView.findViewById(R.id.randomEventCompany);
			    randomEventCompanyText.setText("Company: " + randomEventCompany);
			    String randomEventDesc = randomEvents[eventIndex].getDescription();
			    TextView randomEventDescText = (TextView) popupView.findViewById(R.id.randomEventDescText);
			    randomEventDescText.setText(randomEventDesc);
			    Button btnDismiss = (Button)popupView.findViewById(R.id.backRandomEvent);
			    btnDismiss.setOnClickListener(new Button.OnClickListener(){

			        @Override
			        public void onClick(View v) {
			        // TODO Auto-generated method stub
			        popupWindow.dismiss();
			}});
			}
			for(int i = 0; i < stocks.length; i++)
			{
				if(randomEvent)
				{
					if(i == stockIndex)
						continue;
				}
				stocks[i].addToPriceHistory(currentDay);
				stocks[i].randomPriceChange();
			}
			currentDay++;
			float bankInterest = player.getField(Field.BANKBALANCE) * 0.01f;
			player.addToField(Field.BANKBALANCE, bankInterest);
			float debtInterest = player.getField(Field.DEBT) * 0.03f;
			player.addToField(Field.DEBT, debtInterest);
			if(player.isStockBrokerEnabled())
				player.subtractFromField(Field.CASH, 50.0f);
			if(player.insiderInfoDay() > 0)
			{
				player.subtractFromField(Field.CASH, 100.0f);
				player.setInsiderInfoDay(player.insiderInfoDay()-1);
				for(int i = 0; i < stocks.length; i++)
				{
					if(player.getSharesOwned(stocks[i].getName()) > 0)
						stocks[i].setPrice(stocks[i].getPrice()*0.8f + stocks[i].getPrice());
				}
			}
			updateDisplay();
		}
		else
		{
			// game is over, set up results and show that
			// finish this activity
			String cash = "Cash: $" + Float.toString(player.getField(Field.CASH));
		    String bank = "Bank: $" + Float.toString(player.getField(Field.BANKBALANCE));
		    String debt = "Debt: $" + Float.toString(player.getField(Field.DEBT));
		    float endValue = player.getField(Field.CASH) + player.getField(Field.BANKBALANCE) - player.getField(Field.DEBT);
		    // start value is initial cash - initial debt, or 2000 - 5000 = -3000
		    float startValue = -3000.0f;
		    float netValue = endValue - startValue;
		    String net = "Net: $" + Float.toString(netValue);
		    String maxStock = "";
	    	int currentMaxAmount = 0;
		    for(int i = 0; i < stocks.length; i++)
		    {
		    	if(player.getSharesPurchased(stocks[i].getName()) > currentMaxAmount)
		    	{
		    		maxStock = stocks[i].getName();
		    		currentMaxAmount = player.getSharesPurchased(stocks[i].getName());
		    	}
		    }
		    String mostPurchased = "Most Purchased Stock: " + maxStock;
			Intent intent = new Intent(this, ResultsActivity.class);
			intent.putExtra("com.meto.stockwars.CASH", cash);
			intent.putExtra("com.meto.stockwars.BANK", bank);
			intent.putExtra("com.meto.stockwars.DEBT", debt);
			intent.putExtra("com.meto.stockwars.NET", net);
			intent.putExtra("com.meto.stockwars.MOST_PURCHASED", mostPurchased);
			startActivity(intent);
			finish();
		}
	}
	
	public void onBankButton(View view)
	{
		bank();
	}
	
	public void onTradingBoosts(View v)
	{
		tradingBoost();
	}
	
	public void onGameDayHelpButton(View view)
	{
		Intent helpIntent = new Intent(this, GameDayHelpActivity.class);
		startActivity(helpIntent);
	}
	
	private void initNewGame()
	{
		player = new Player();
		stocks = new Stock[15];
		stocks[0] = new Stock("Macrosoft Corp.", 35.0f);
		stocks[1] = new Stock("Tweeter, Inc.", 60.0f);
		stocks[2] = new Stock("Searcher, Inc.", 1100.0f);
		stocks[3] = new Stock("Pear, Inc.", 550.0f);
		stocks[4] = new Stock("ICM Corp.", 180.0f);
		stocks[5] = new Stock("Wintel Corp.", 25.0f);
		stocks[6] = new Stock("SmarterTech Corp.", 5.0f);
		stocks[7] = new Stock("Cool Cats LLC", 100.0f);
		stocks[8] = new Stock("Cooler Dogs, Inc.", 300.0f);
		stocks[9] = new Stock("Greatest Purchase Corp.", 600.0f);
		stocks[10] = new Stock("All-Mart Company", 875.0f);
		stocks[11] = new Stock("Namikaze, Inc.", 450.0f);
		stocks[12] = new Stock("Queen Industries", 2000.0f);
		stocks[13] = new Stock("Daxam LLC", 1650.0f);
		stocks[14] = new Stock("Kr Corp.", 1350.0f);
		currentDay = 1;
		randomEvents = new RandomEvent[6];
		randomEvents[0] = new RandomEvent(getString(R.string.successful_launch), getString(R.string.successful_launch_desc));
		randomEvents[0].setPriceChange(0.8f);
		randomEvents[1] = new RandomEvent(getString(R.string.ceo_fired), getString(R.string.ceo_fired_desc));
		randomEvents[1].setPriceChange(-0.8f);
		randomEvents[2] = new RandomEvent(getString(R.string.ad_campaign), getString(R.string.ad_campaign_desc));
		randomEvents[2].setPriceChange(0.75f);
		randomEvents[3] = new RandomEvent(getString(R.string.product_recall), getString(R.string.product_recall_desc));
		randomEvents[3].setPriceChange(-0.75f);
		randomEvents[4] = new RandomEvent(getString(R.string.new_location), getString(R.string.new_location_desc));
		randomEvents[4].setPriceChange(0.5f);
		randomEvents[5] = new RandomEvent(getString(R.string.lost_lawsuit), getString(R.string.lost_lawsuit_desc));
		randomEvents[5].setPriceChange(-0.5f);
	}
	
	private void bank()
	{
		LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View popupView = layoutInflater.inflate(R.layout.bankactivitypopup, null);  
	    final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,  LayoutParams.WRAP_CONTENT);
	    popupWindow.setFocusable(true);
	    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
	    String cash = "Cash: $" + Float.toString(player.getField(Field.CASH));
	    String bank = "Bank: $" + Float.toString(player.getField(Field.BANKBALANCE));
	    String debt = "Debt: $" + Float.toString(player.getField(Field.DEBT));
	    final TextView cashText = (TextView) popupView.findViewById(R.id.cashView);
	    cashText.setText(cash);
	    final TextView bankText = (TextView) popupView.findViewById(R.id.bankView);
	    bankText.setText(bank);
	    final TextView debtText = (TextView) popupView.findViewById(R.id.debtView);
	    debtText.setText(debt);
	    final EditText  amount = (EditText)popupView.findViewById(R.id.amountTextField);
	    Button btnDismiss = (Button)popupView.findViewById(R.id.back);
	    btnDismiss.setOnClickListener(new Button.OnClickListener(){

	        @Override
	        public void onClick(View v) {
	        // TODO Auto-generated method stub
	        popupWindow.dismiss();
	}});
	        
	        
	        Button withdraw = (Button)popupView.findViewById(R.id.withdraw);
	        withdraw.setOnClickListener(new Button.OnClickListener(){
	        @Override
	public void onClick(View v) {
	// TODO Auto-generated method stub
	        if(amount.getText() == null || amount.getText().toString().equalsIgnoreCase(""))
	        {
	        Toast toast = Toast.makeText(getApplicationContext(), "Enter Amount", Toast.LENGTH_SHORT);
	        toast.show();
	        }
	        else if(Float.parseFloat(amount.getText().toString()) > player.getField(Field.BANKBALANCE))
	        {
	        Toast toast = Toast.makeText(getApplicationContext(), "Inadequate Money in Bank", Toast.LENGTH_SHORT);
	        toast.show();
	        }
	        else if(Float.parseFloat(amount.getText().toString()) <= 0)
	        {
	        	Toast toast = Toast.makeText(getApplicationContext(), "Invalid Amount", Toast.LENGTH_SHORT);
	        	toast.show();
	        }
	        else{
	        player.addToField( Field.CASH, Float.parseFloat(amount.getText().toString()));
	        player.subtractFromField(Field.BANKBALANCE, Float.parseFloat(amount.getText().toString()));
	        cashText.setText("Cash: $" + Float.toString(player.getField(Field.CASH)));
	        bankText.setText("Bank: $" + Float.toString(player.getField(Field.BANKBALANCE)));
	        debtText.setText("Debt: $" + Float.toString(player.getField(Field.DEBT)));
	        updateDisplay();
	        }
	}});

	        
	        Button deposit = (Button)popupView.findViewById(R.id.deposit);
	        deposit.setOnClickListener(new Button.OnClickListener(){
	        @Override
	public void onClick(View v) {
	// TODO Auto-generated method stub
	        if(amount.getText() == null || amount.getText().toString().equalsIgnoreCase(""))
	        {
	        Toast toast = Toast.makeText(getApplicationContext(), "Enter Amount", Toast.LENGTH_SHORT);
	        toast.show();
	        }
	        else if(Float.parseFloat(amount.getText().toString()) > player.getField(Field.CASH))
	        {
	        Toast toast = Toast.makeText(getApplicationContext(), "Inadequate Cash", Toast.LENGTH_SHORT);
	        toast.show();
	        }
	        else if(Float.parseFloat(amount.getText().toString()) <= 0)
	        {
	        	Toast toast = Toast.makeText(getApplicationContext(), "Invalid Amount", Toast.LENGTH_SHORT);
	        	toast.show();
	        }
	        else{
	        player.addToField( Field.BANKBALANCE, Float.parseFloat(amount.getText().toString()));
	        player.subtractFromField(Field.CASH, Float.parseFloat(amount.getText().toString()));
	        cashText.setText("Cash: $" + Float.toString(player.getField(Field.CASH)));
	        bankText.setText("Bank: $" + Float.toString(player.getField(Field.BANKBALANCE)));
	        debtText.setText("Debt: $" + Float.toString(player.getField(Field.DEBT)));
	        updateDisplay();
	        }
	}});
	        

	        Button paydebt = (Button)popupView.findViewById(R.id.paydebt);
	        paydebt.setOnClickListener(new Button.OnClickListener(){
	        @Override
	public void onClick(View v) {
	// TODO Auto-generated method stub
	        if(amount.getText() == null || amount.getText().toString().equalsIgnoreCase(""))
	        {
	        Toast toast = Toast.makeText(getApplicationContext(), "Enter Amount", Toast.LENGTH_SHORT);
	        toast.show();
	        }
	        else if(Float.parseFloat(amount.getText().toString()) > player.getField(Field.CASH))
	        {
	        Toast toast = Toast.makeText(getApplicationContext(), "Inadequate Cash", Toast.LENGTH_SHORT);
	        toast.show();
	        }
	        else if(Float.parseFloat(amount.getText().toString()) > player.getField(Field.DEBT))
	        {
	        Toast toast = Toast.makeText(getApplicationContext(), "Debt less than amount", Toast.LENGTH_SHORT);
	        toast.show();
	        }
	        else if(Float.parseFloat(amount.getText().toString()) <= 0)
	        {
	        	Toast toast = Toast.makeText(getApplicationContext(), "Invalid Amount", Toast.LENGTH_SHORT);
	        	toast.show();
	        }
	        else{
	        player.subtractFromField( Field.CASH, Float.parseFloat(amount.getText().toString()));
	        player.subtractFromField(Field.DEBT, Float.parseFloat(amount.getText().toString()));
	        cashText.setText("Cash: $" + Float.toString(player.getField(Field.CASH)));
	        bankText.setText("Bank: $" + Float.toString(player.getField(Field.BANKBALANCE)));
	        debtText.setText("Debt: $" + Float.toString(player.getField(Field.DEBT)));
	        updateDisplay();
	        }
	}});
	}
	
	private void tradingBoost()
	{
	    LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
	    final View popupView1 = layoutInflater.inflate(R.layout.tradingboostpopup, null);  
	    final PopupWindow popupWindow = new PopupWindow(popupView1, LayoutParams.MATCH_PARENT,  LayoutParams.WRAP_CONTENT);
	    popupWindow.showAtLocation(popupView1, Gravity.CENTER, 0, 0);
	        
	    Button btnDismiss = (Button)popupView1.findViewById(R.id.backTradingBoosts);
	    Button stockBroker = (Button)popupView1.findViewById(R.id.stockBrokerButton);
	    Button insiderInfo = (Button)popupView1.findViewById(R.id.insiderInfoButton);
	    if(player.isStockBrokerEnabled())
	    {
	        stockBroker.setText("Fire Stock Broker");
	    }
	    else
	    {
	        stockBroker.setText("Hire Stock Broker");
	    }
	        
	    if(player.insiderInfoDay() > 0)
	    {
	        insiderInfo.setText("Insider Info Enabled");
	    }
	    else
	    {
	        insiderInfo.setText("Insider Info");
	    }
	        
	       
	    
	        
	    btnDismiss.setOnClickListener(new Button.OnClickListener(){
	        @Override
	        public void onClick(View v) {
	        // TODO Auto-generated method stub
	        popupWindow.dismiss();
	    }});
	        
	        
	        
	    stockBroker.setOnClickListener(new Button.OnClickListener(){
	        @Override
	        public void onClick(View v) {
	        // TODO Auto-generated method stub
	        if(!player.isStockBrokerEnabled())
	        {
	            player.setStockBroker(true);
	            player.subtractFromField(Field.CASH, 50.0f);
	            updateDisplay();
	            Toast toast = Toast.makeText(getApplicationContext(), "Stock Broker Hired", Toast.LENGTH_SHORT);
	            toast.show();
	        }
	        else
	        {
	            player.setStockBroker(false);
	            Toast toast = Toast.makeText(getApplicationContext(), "Stock Broker Fired", Toast.LENGTH_SHORT);
	            toast.show();
	        }
	            
	    }});
	        
	        
	        
	    insiderInfo.setOnClickListener(new Button.OnClickListener(){
	        @Override
	        public void onClick(View v) {
	        // TODO Auto-generated method stub
	        if(player.insiderInfoDay() > 0)
	        {
	            Toast toast = Toast.makeText(getApplicationContext(), "Insider Info Already Enabled", Toast.LENGTH_SHORT);
	            toast.show();
	        }
	        else
	        {
	            player.setInsiderInfoDay(5);
	            player.subtractFromField(Field.CASH, 100.0f);
	            updateDisplay();
	            Toast toast = Toast.makeText(getApplicationContext(), "Insider Info Has Been Enabled", Toast.LENGTH_SHORT);
	            toast.show();
	        }
	            
	    }});
	}
	
	private void possiblyCaught()
	{
		if(player.insiderInfoDay() > 0)
		{
			Random randomGenerator = new Random();
			int chance = randomGenerator.nextInt(3);
			if(chance == 2)
				initialCaughtByPolice();
		}
	}
	
	private void successEvadeCops()
	{
		LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View popupView4 = layoutInflater.inflate(R.layout.successevadecops, null);  
	    final PopupWindow popupWindow = new PopupWindow(popupView4, LayoutParams.MATCH_PARENT,  LayoutParams.WRAP_CONTENT);
	    popupWindow.setFocusable(true);
	    popupWindow.showAtLocation(popupView4, Gravity.CENTER, 0, 0);
	    Button btnDismiss = (Button)popupView4.findViewById(R.id.backSuccessEvade);
	    btnDismiss.setOnClickListener(new Button.OnClickListener(){

	        @Override
	        public void onClick(View v) {
	        // TODO Auto-generated method stub
	        popupWindow.dismiss();
	}});	
	}
	
	private void caughtByPolice()
	{
		LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View popupView3 = layoutInflater.inflate(R.layout.caughtbypolicepopup, null);  
	    final PopupWindow popupWindow = new PopupWindow(popupView3, LayoutParams.MATCH_PARENT,  LayoutParams.WRAP_CONTENT);
	    popupWindow.setFocusable(true);
	    popupWindow.showAtLocation(popupView3, Gravity.CENTER, 0, 0);
	    player.subtractFromField( Field.BANKBALANCE, (player.getField(Field.BANKBALANCE)*1/3));
	    player.subtractFromField(Field.CASH,(player.getField(Field.CASH)*1/3) );
	    
	    Button btnDismiss = (Button)popupView3.findViewById(R.id.backFailedEvade);
	    btnDismiss.setOnClickListener(new Button.OnClickListener(){

	        @Override
	        public void onClick(View v) {
	        // TODO Auto-generated method stub
	        updateDisplay();
	        popupWindow.dismiss();
	}});	
	}
	
	private void initialCaughtByPolice()
	{
		final String ransequence = "";
		String str = "AB";
		final String userinputsequence = "";
		Random randomGenerator = new Random();
		for(int i=0; i<=8; i++)
		{
			int randomInt = randomGenerator.nextInt(2)+1;
			if(randomInt == 1)
				ransequence.concat("A");
			else if(randomInt == 2)
				ransequence.concat("B");
		}
		LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View popupView2 = layoutInflater.inflate(R.layout.initiallycaughtpopup, null);  
	    final PopupWindow popupWindow = new PopupWindow(popupView2, LayoutParams.MATCH_PARENT,  LayoutParams.WRAP_CONTENT);
	    popupWindow.setFocusable(true);
	    popupWindow.showAtLocation(popupView2, Gravity.CENTER, 0, 0);
	    final TextView clocktime = (TextView) popupView2.findViewById(R.id.timerview);
	    TextView sequence= (TextView) findViewById(R.id.initiallyCaughtSequence);
	    final Button a = (Button)popupView2.findViewById(R.id.initiallyCaughtAButton);
	    final Button b = (Button)popupView2.findViewById(R.id.initiallyCaughtBButton);
	    final TextView uinput= (TextView) popupView2.findViewById(R.id.initiallyCaughtUInput);
	    uinput.setText(userinputsequence);
	    new CountDownTimer(5000, 1000) {

	        public void onTick(long millisUntilFinished) {
	            clocktime.setText("seconds remaining: " + millisUntilFinished / 1000);
	        }

	        public void onFinish() {
	        a.setOnClickListener(new Button.OnClickListener(){
	            @Override
	            public void onClick(View v) {
	            userinputsequence.concat("A");
	            uinput.setText(userinputsequence);
	    }});
	            
	            b.setOnClickListener(new Button.OnClickListener(){
	            @Override
	            public void onClick(View v) {
	            userinputsequence.concat("B");
	            uinput.setText(userinputsequence);
	    }});
	        new CountDownTimer(4000, 1000) {

	            public void onTick(long millisUntilFinished) {
	                clocktime.setText("seconds remaining: " + millisUntilFinished / 1000);
	                
	            }
	            
	            public void onFinish() {
	                clocktime.setText("done!");
	                a.setVisibility(View.GONE);
	                b.setVisibility(View.GONE);
	                if(userinputsequence.equals(ransequence))
	                {
	                	successEvadeCops();
	                	popupWindow.dismiss();
	                }
	                else
	                {
	                	caughtByPolice();
	                	popupWindow.dismiss();
	                }
	            }
	        }.start();
	        }
	        
	    }.start();
	}
}
