/**
 * A class representing a pop-up that lets users specify game length in number of days.
 */
package com.meto.stockwars;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Build;
import android.os.Bundle;

/**
 * @author METO Solutions
 *
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class NewGameDialogFragment extends DialogFragment
{
	public final static String EXTRA_GAME_LENGTH = "com.meto.stockwars.GAME_LENGTH";
	private int game_length;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] game_lengths = {"2", "30", "60", "90", "120"};
        builder.setTitle(R.string.choose_game_length)
        	   .setSingleChoiceItems(game_lengths, 0,
        			   new OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       game_length = Integer.parseInt(game_lengths[which]);
                   }
                   })
               .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   	Intent intent = new Intent(getActivity(), GameDayActivity.class);
               			intent.putExtra(EXTRA_GAME_LENGTH, game_length);
               			startActivity(intent);
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
