package com.meto.stockwars;

import android.os.Bundle;
import android.app.Activity;
//import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class GameDayHelpActivity extends Activity {

	Button ok;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_day_help);
		ok= (Button) findViewById(R.id.ok);
		ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				
			}
		});
	
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_day_help, menu);
		return true;
	}*/

}
