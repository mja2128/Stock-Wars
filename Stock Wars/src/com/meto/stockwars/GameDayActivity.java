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
		ListView stocksListView = (ListView) findViewById(R.id.stocksListView);
		StockAdapter stockAdapter = new StockAdapter(stocks, stocksListView.getContext());
		stocksListView.setAdapter(stockAdapter);
		TextView cashTextView = (TextView) findViewById(R.id.cashTextView);
		cashTextView.setText("Cash: $" + String.valueOf(player.getField(Field.CASH)));
		TextView bankTextView = (TextView) findViewById(R.id.bankTextView);
		bankTextView.setText("Bank: $" + String.valueOf(player.getField(Field.BANKBALANCE)));
		TextView dayTextView = (TextView) findViewById(R.id.dayTextView);
		dayTextView.setText("Day: " + currentDay);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_day, menu);
		return true;
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
		stocks[0] = new Stock("Macrosoft Corp.", 35.0);
		stocks[1] = new Stock("Tweeter, Inc.", 60.0);
		stocks[2] = new Stock("Searcher, Inc.", 1100.0);
		stocks[3] = new Stock("Pear, Inc.", 550.0);
		stocks[4] = new Stock("ICM Corp.", 180.0);
		stocks[5] = new Stock("Wintel Corp.", 25.0);
		stocks[6] = new Stock("SmarterTech Corp.", 5.0);
		stocks[7] = new Stock("Cool Cats LLC", 100.0);
		stocks[8] = new Stock("Cooler Dogs, Inc.", 300.0);
		stocks[9] = new Stock("Greatest Purchase Corp.", 600.0);
		stocks[10] = new Stock("All-Mart Company", 875.0);
		stocks[11] = new Stock("Namikaze, Inc.", 450.0);
		stocks[12] = new Stock("Queen Industries", 2000.0);
		stocks[13] = new Stock("Daxam LLC", 1650.0);
		stocks[14] = new Stock("Kr Corp.", 1350.0);
		currentDay = 1;
	}

}
