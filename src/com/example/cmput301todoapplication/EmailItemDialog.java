package com.example.cmput301todoapplication;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

public class EmailItemDialog extends DialogFragment{

	public AccessData databaseAccess;
	private ArrayList mSelectedItems;
	
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
    	databaseAccess = new AccessData();
    	mSelectedItems = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        EditText input = new EditText(App.getContext());
        input.setId(0);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        final EditText savedText = (EditText) input.findViewById(0);
        builder.setTitle(R.string.dialog_email_items)
                   .setMultiChoiceItems(databaseAccess.allItemsText(App.getContext()), null,
                      new DialogInterface.OnMultiChoiceClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which,
                       boolean isChecked) {
                   if (isChecked) {
                       // If the user checked the item, add it to the selected items
                       mSelectedItems.add(which);
                   } else if (mSelectedItems.contains(which)) {
                       // Else, if the item is already in the array, remove it 
                       mSelectedItems.remove(Integer.valueOf(which));
                   }
               }
           })
               .setPositiveButton(R.string.dialog_email, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // TODO Email items
                	   
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
