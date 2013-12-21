 package com.meto.stockwars;

import android.os.Bundle;
import android.app.Activity;
//import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
;
public class BankActivity extends Activity {

	Button withdraw,deposit,back;
	TextView balance;
	EditText editText1;
	String amnt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bank);
		withdraw= (Button) findViewById(R.id.withdraw);
		deposit= (Button) findViewById(R.id.deposit);
		back= (Button) findViewById(R.id.back);
		editText1 =(EditText) findViewById(R.id.editText1);
		balance= (TextView) findViewById(R.id.balance);
		//amnt=editText1.toString();
		deposit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				
			}
		});
		withdraw.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		back.setOnClickListener(new View.OnClickListener() {
			
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
		getMenuInflater().inflate(R.menu.bank, menu);
		return true;
	}*/

}
