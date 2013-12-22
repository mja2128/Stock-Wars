package com.meto.stockwars;

import com.meto.stockwars.Player.Field;

import android.os.Bundle;
import android.app.Activity;
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
		//Intent bankIntent = new Intent(this, BankActivity.class);
		//startActivity(bankIntent);
		bank();
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
	
	private void bank()
	{
		LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		final View popupView = layoutInflater.inflate(R.layout.bankactivitypopup, null);  
	    final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,  LayoutParams.WRAP_CONTENT);
	    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
	    String cash = "Cash: $" + Float.toString(player.getField(Field.CASH));
	    String bank = "Bank: $" + Float.toString(player.getField(Field.BANKBALANCE));
	    String debt = "Debt: $" + Float.toString(player.getField(Field.DEBT));
	    TextView cashText = (TextView) popupView.findViewById(R.id.cashView);
	    cashText.setText(cash);
	    TextView bankText = (TextView) popupView.findViewById(R.id.bankView);
	    bankText.setText(bank);
	    TextView debtText = (TextView) popupView.findViewById(R.id.debtView);
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
	        player.subtractFromField( Field.CASH, Integer.parseInt(amount.getText().toString()));
	        player.subtractFromField(Field.DEBT, Integer.parseInt(amount.getText().toString()));
	        }
	}});
	        
	        
	        
	}
}
