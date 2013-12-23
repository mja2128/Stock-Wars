/**
 * Custom Adapter for use with an array of Stocks. It will output Views that will include
 * the Stock name,
 */
package com.meto.stockwars;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.meto.stockwars.Player.Field;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
		final int pos = position;
		RelativeLayout row = new RelativeLayout(context);
		TextView stockNameTextView = new TextView(context);
		stockNameTextView.setText(stocks[position].getName());
		stockNameTextView.setClickable(true);
		stockNameTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View popupView = layoutInflater.inflate(R.layout.stockpopup, null);  
			    final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,  LayoutParams.WRAP_CONTENT);
			    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
			    String stockNameHistory = stocks[pos].getName() + " History";
			    TextView stockNameText = (TextView) popupView.findViewById(R.id.stockNameHistoryTitle);
			    stockNameText.setText(stockNameHistory);
			    TextView noBrokerText = (TextView) popupView.findViewById(R.id.noBrokerTextView);
			    if(!GameDayActivity.player.isStockBrokerEnabled())
			    {
			    	String noBrokerMsg = context.getString(R.string.no_broker_message);
				    noBrokerText.setText(noBrokerMsg);
				    noBrokerText.setVisibility(View.VISIBLE);
				    LinearLayout graphLayout = (LinearLayout) popupView.findViewById(R.id.priceHistoryGraph);
				    graphLayout.setVisibility(View.INVISIBLE);
			    }
			    else
			    {
			    	// add the current day's price to history so there isn't a 0 for the last element
			    	stocks[pos].addToPriceHistory(GameDayActivity.currentDay);
			    	GraphViewData[] graphData = new GraphViewData[GameDayActivity.currentDay];
			    	for(int i = 0; i < GameDayActivity.currentDay; i++)
			    	{
			    		graphData[i] = new GraphViewData(i+1, stocks[pos].getPriceHistory()[i+1]);
			    	}
			    	GraphViewSeries graphSeries = new GraphViewSeries(graphData);
			    	GraphView graphView = new LineGraphView(context, "Stock Price History");
			    	graphView.addSeries(graphSeries);
			    	graphView.setViewPort(1, 10);
			    	graphView.setScrollable(true);
			    	graphView.setScalable(false);
			    	LinearLayout graphLayout = (LinearLayout) popupView.findViewById(R.id.priceHistoryGraph);
			    	graphLayout.addView(graphView);
			    	graphLayout.setVisibility(View.VISIBLE);
			    	noBrokerText.setVisibility(View.INVISIBLE);
			    }
			    Button btnDismiss = (Button)popupView.findViewById(R.id.backStock);
			    btnDismiss.setOnClickListener(new Button.OnClickListener(){

			        @Override
			        public void onClick(View v) {
			        // TODO Auto-generated method stub
			        popupWindow.dismiss();
			}});
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
				// show the buy/sell dialog popup
				LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View popupView = layoutInflater.inflate(R.layout.buysellpopup, null);  
			    final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,  LayoutParams.WRAP_CONTENT);
			    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
			    String cash = "Cash: $" + Float.toString(GameDayActivity.player.getField(Field.CASH));
			    String sharesOwned = "Shares Owned: " + Integer.toString(GameDayActivity.player.getSharesOwned(stocks[pos].getName()));
			    String stockName = stocks[pos].getName();
			    String stockPrice = "Price: $" + Float.toString(stocks[pos].getPrice());
			    TextView cashBuySellText = (TextView) popupView.findViewById(R.id.cashBuySellView);
			    cashBuySellText.setText(cash);
			    TextView sharesOwnedText = (TextView) popupView.findViewById(R.id.sharesOwnedBuySellView);
			    sharesOwnedText.setText(sharesOwned);
			    TextView stockNameText = (TextView) popupView.findViewById(R.id.stockNameBuySellView);
			    stockNameText.setText(stockName);
			    TextView stockPriceText = (TextView) popupView.findViewById(R.id.stockPriceBuySellView);
			    stockPriceText.setText(stockPrice);
			    final EditText  amount = (EditText)popupView.findViewById(R.id.amountBuySellTextField);
			    Button btnDismiss = (Button)popupView.findViewById(R.id.backBuySell);
			    btnDismiss.setOnClickListener(new Button.OnClickListener(){

			        @Override
			        public void onClick(View v) {
			        // TODO Auto-generated method stub
			        popupWindow.dismiss();
			}});
			        
			        
			        Button buy = (Button)popupView.findViewById(R.id.buyButton);
			        buy.setOnClickListener(new Button.OnClickListener(){
			        @Override
			public void onClick(View v) {
			// TODO Auto-generated method stub
			        if(amount.getText() == null || amount.getText().toString().equalsIgnoreCase(""))
			        {
			        Toast toast = Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT);
			        toast.show();
			        }
			        else if(Integer.parseInt(amount.getText().toString())*stocks[pos].getPrice() > GameDayActivity.player.getField(Field.CASH))
			        {
			        Toast toast = Toast.makeText(context, "Isufficient Money in Cash", Toast.LENGTH_SHORT);
			        toast.show();
			        }
			        else if(Float.parseFloat(amount.getText().toString()) <= 0 || amount.getText().toString().contains("."))
			        {
			        	Toast toast = Toast.makeText(context, "Invalid Amount", Toast.LENGTH_SHORT);
			        	toast.show();
			        }
			        else{
			        	int currentShares = GameDayActivity.player.getSharesOwned(stocks[pos].getName());
			        	GameDayActivity.player.setSharesOwned(stocks[pos].getName(), currentShares+Integer.parseInt(amount.getText().toString()));
			        	GameDayActivity.player.subtractFromField(Field.CASH, Integer.parseInt(amount.getText().toString())*stocks[pos].getPrice());
			        	String cash = "Cash: $" + Float.toString(GameDayActivity.player.getField(Field.CASH));
					    String sharesOwned = "Shares Owned: " + Integer.toString(GameDayActivity.player.getSharesOwned(stocks[pos].getName()));
					    TextView cashBuySellText = (TextView) popupView.findViewById(R.id.cashBuySellView);
					    cashBuySellText.setText(cash);
					    TextView sharesOwnedText = (TextView) popupView.findViewById(R.id.sharesOwnedBuySellView);
					    sharesOwnedText.setText(sharesOwned);
			        }
			}});

			        
			        Button sell = (Button)popupView.findViewById(R.id.sellButton);
			        sell.setOnClickListener(new Button.OnClickListener(){
			        @Override
			public void onClick(View v) {
			// TODO Auto-generated method stub
			        if(amount.getText() == null || amount.getText().toString().equalsIgnoreCase(""))
			        {
			        Toast toast = Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT);
			        toast.show();
			        }
			        else if(Integer.parseInt(amount.getText().toString()) > GameDayActivity.player.getSharesOwned(stocks[pos].getName()))
			        {
			        Toast toast = Toast.makeText(context, "You do not own that many shares of this stock", Toast.LENGTH_SHORT);
			        toast.show();
			        }
			        else if(Float.parseFloat(amount.getText().toString()) <= 0 || amount.getText().toString().contains("."))
			        {
			        	Toast toast = Toast.makeText(context, "Invalid Amount", Toast.LENGTH_SHORT);
			        	toast.show();
			        }
			        else{
			        	int currentShares = GameDayActivity.player.getSharesOwned(stocks[pos].getName());
			        	GameDayActivity.player.setSharesOwned(stocks[pos].getName(), currentShares-Integer.parseInt(amount.getText().toString()));
			        	GameDayActivity.player.addToField(Field.CASH, Integer.parseInt(amount.getText().toString())*stocks[pos].getPrice());
			        	String cash = "Cash: $" + Float.toString(GameDayActivity.player.getField(Field.CASH));
					    String sharesOwned = "Shares Owned: " + Integer.toString(GameDayActivity.player.getSharesOwned(stocks[pos].getName()));
					    TextView cashBuySellText = (TextView) popupView.findViewById(R.id.cashBuySellView);
					    cashBuySellText.setText(cash);
					    TextView sharesOwnedText = (TextView) popupView.findViewById(R.id.sharesOwnedBuySellView);
					    sharesOwnedText.setText(sharesOwned);
			        }
			}});
			        
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
