package com.meto.stockwars;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ResultsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		Intent intent = getIntent();
		TextView cashResults = (TextView) findViewById(R.id.cashResultsText);
		cashResults.setText(intent.getStringExtra("com.meto.stockwars.CASH"));
		TextView bankResults = (TextView) findViewById(R.id.bankResultsText);
		bankResults.setText(intent.getStringExtra("com.meto.stockwars.BANK"));
		TextView debtResults = (TextView) findViewById(R.id.debtResultsText);
		debtResults.setText(intent.getStringExtra("com.meto.stockwars.DEBT"));
		TextView netResults = (TextView) findViewById(R.id.netResultsText);
		netResults.setText(intent.getStringExtra("com.meto.stockwars.NET"));
		TextView mostPurchasedResults = (TextView) findViewById(R.id.mostPurchasedResultsText);
		mostPurchasedResults.setText(intent.getStringExtra("com.meto.stockwars.MOST_PURCHASED"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results, menu);
		return true;
	}
	
	public void onNewGame(View v)
	{
		NewGameDialogFragment newGameFragment = new NewGameDialogFragment();
		newGameFragment.show(getFragmentManager(), "newgame");
	}
	
	public void onMainMenu(View v)
	{
		Intent intent = new Intent(this, MainMenuActivity.class);
		startActivity(intent);
		finish();
	}

}
