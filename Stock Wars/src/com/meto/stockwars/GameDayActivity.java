package com.meto.stockwars;

import com.meto.stockwars.Player.Field;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class GameDayActivity extends Activity {
	public static Player player;
	public static Stock[] stocks;
	public static int currentDay;
	public static int game_length;

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
	
	private void updateDisplay()
	{
		ListView stocksListView = (ListView) findViewById(R.id.stocksListView);
		StockAdapter stockAdapter = new StockAdapter(stocks, stocksListView.getContext());
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
		if(currentDay < game_length)
		{
			// check for random event
			// apply trading boosts price effects
			// if insider info, check to see if they got caught
			for(int i = 0; i < stocks.length; i++)
			{
				stocks[i].addToPriceHistory(currentDay);
				stocks[i].randomPriceChange();
			}
			currentDay++;
			float bankInterest = player.getField(Field.BANKBALANCE) * 0.03f;
			player.addToField(Field.BANKBALANCE, bankInterest);
			float debtInterest = player.getField(Field.DEBT) * 0.05f;
			player.addToField(Field.DEBT, debtInterest);
			updateDisplay();
		}
		else
		{
			// game is over, set up results and show that
		}
	}
	
	public void onBankButton(View view)
	{
		Intent bankIntent = new Intent(this, BankActivity.class);
		startActivity(bankIntent);
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
	}

}
