package com.example.cmput301todoapplication;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

public class EmailItemDialog extends DialogFragment{

	public AccessData databaseAccess;
	private ArrayList<Integer> selectedItemsIndex;
	private ArrayList<toDo> allItems;
	
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
    	databaseAccess = new AccessData();
    	selectedItemsIndex = new ArrayList<Integer>();
    	allItems = databaseAccess.getAllItems(getActivity().getBaseContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_email_items)
                   .setMultiChoiceItems(databaseAccess.allItemsText(App.getContext()), null,
                      new DialogInterface.OnMultiChoiceClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which,
                       boolean isChecked) {
                   if (isChecked) {
                       // If the user checked the item, add it to the selected items
                       selectedItemsIndex.add(which);
                   } else if (selectedItemsIndex.contains(which)) {
                       // Else, if the item is already in the array, remove it 
                       selectedItemsIndex.remove(Integer.valueOf(which));
                   }
               }
           })
               .setPositiveButton(R.string.dialog_email, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   // Email items
                	   final ArrayList<toDo> selectedItems = new ArrayList<toDo>();
                	   for(Integer i : selectedItemsIndex) {
                		   selectedItems.add(allItems.get(i));
                	   }
                	   sendEmail(selectedItems);
                   }
               })
               .setNeutralButton(R.string.email_all, new DialogInterface.OnClickListener() {
            	   public void onClick(DialogInterface dialog, int id) {
            		   //Email all items
            		   sendEmail(allItems);
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
    
    public void sendEmail(ArrayList<toDo> selectedItems) {
     	Intent i = new Intent(Intent.ACTION_SEND);
    	i.setType("message/rfc822");
    	String emailText = "";
    	for (toDo item : selectedItems){
    		String checked = "[]";
    		if (item.getChecked() == true) {
    			checked = "[X]";
    		}
    		emailText = emailText + "\n" + checked + " " + item.getText();
    	}
    	i.putExtra(Intent.EXTRA_TEXT   , emailText);
    	try {
    	    startActivity(Intent.createChooser(i, "Send mail..."));
    	} catch (android.content.ActivityNotFoundException ex) {
    	    Toast.makeText(getActivity().getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
    	}
    }
	
}
