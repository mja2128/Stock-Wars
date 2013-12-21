/**
 * Simple adapter to populate a ListView with simple integers as strings using TextViews
 */
package com.meto.stockwars;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author GaMeBoY
 *
 */
public class IntegerAdapter extends BaseAdapter {
	private int[] ints;
	private Context context;

	/**
	 * 
	 */
	public IntegerAdapter(int[] nums, Context context) {
		ints = nums;
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return ints.length;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Integer getItem(int position) {
		return ints[position];
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView intText = new TextView(context);
		intText.setText(ints[position]);
		return intText;
	}

}
