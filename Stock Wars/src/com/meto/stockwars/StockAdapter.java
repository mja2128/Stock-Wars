/**
 * Custom Adapter for use with an array of Stocks. It will output Views that will include
 * the Stock name,
 */
package com.meto.stockwars;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author GaMeBoY
 *
 */
public class StockAdapter extends BaseAdapter {
	private Stock[] stocks;
	private Context context;

	/**
	 * 
	 */
	public StockAdapter(Stock[] stocks, Context context) {
		this.stocks = stocks;
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return this.stocks.length;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Stock getItem(int position) {
		return this.stocks[position];
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RelativeLayout row = new RelativeLayout(context);
		TextView stockNameTextView = new TextView(context);
		stockNameTextView.setText(stocks[position].getName());
		stockNameTextView.setClickable(true);
		stockNameTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // show the stock dialog popup when that is done
            	// if the user has stock broker enabled, show the price history chart
            	// if not, inform the user of the stock broker option
            }
        });
		row.addView(stockNameTextView);
		// use hash code to ensure each of these has a unique id, we need it later
		stockNameTextView.setId(stockNameTextView.hashCode());
		TextView sharesOwnedTextView = new TextView(context);
		row.addView(sharesOwnedTextView);
		RelativeLayout.LayoutParams sharesOwnedTextViewParams = (RelativeLayout.LayoutParams)sharesOwnedTextView.getLayoutParams();
		sharesOwnedTextViewParams.addRule(RelativeLayout.RIGHT_OF, stockNameTextView.getId());
		sharesOwnedTextView.setLayoutParams(sharesOwnedTextViewParams);
		int sharesOwned = GameDayActivity.player.getSharesOwned(stocks[position].getName());
		sharesOwnedTextView.setText("(" + sharesOwned + ")");
		sharesOwnedTextView.setPadding(10, 0, 0, 0);
		Button priceButton = new Button(context);
		row.addView(priceButton);
		priceButton.setText("$"+String.valueOf(stocks[position].getPrice()));
		RelativeLayout.LayoutParams priceButtonParams = (RelativeLayout.LayoutParams)priceButton.getLayoutParams();
		priceButtonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		priceButton.setLayoutParams(priceButtonParams);
		priceButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// show the buy/sell dialog popup when that is done
			}
		});
		return row;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}
