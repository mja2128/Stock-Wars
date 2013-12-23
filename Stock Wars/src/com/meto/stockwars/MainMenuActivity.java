package com.meto.stockwars;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	public void onNewGame(View view)
	{
		NewGameDialogFragment newGameFragment = new NewGameDialogFragment();
		newGameFragment.show(getFragmentManager(), "newgame");
	}
	
	public void onHelp(View view)
	{
		Intent intent = new Intent(this, MainMenuHelpActivity.class);
		startActivity(intent);
	}

}
