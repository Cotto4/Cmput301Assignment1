package com.example.cmput301todoapplication;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

// Display a summary of the app statistics

// This dialog was created using resources located at
// http://developer.android.com/guide/topics/ui/dialogs.html
// and licensed under the Creative Commons Attribution 2.5 license
public class SummaryDialog extends DialogFragment{

	private AccessData databaseAccess;
	
	@SuppressLint("InflateParams") public Dialog onCreateDialog(Bundle savedInstanceState) {
		databaseAccess = new AccessData();
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View v = inflater.inflate(R.layout.summary, null);
		
        setAllItems(v);
        setAllCheckedItems(v);
        setAllUncheckedItems(v);
        setAllArchivedItems(v);
        setAllArchivedChecked(v);
        setAllArchivedUnchecked(v);
		
		builder.setView(v);
        builder.setTitle(R.string.summary)
        .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   	// Exit summary
                   }
               });

		return builder.create();
		
	}
	
	private void setAllItems(View v) {
		TextView allItems = (TextView) v.findViewById(R.id.todoitems);
		ArrayList<toDo> items = databaseAccess.getUnarchivedItems(App.getContext());
		allItems.setText("To Do Items: " + String.valueOf(items.size()));
	}
	
	private void setAllCheckedItems(View v) {
		TextView allItems = (TextView) v.findViewById(R.id.todochecked);
		ArrayList<toDo> items = databaseAccess.getUnarchivedChecked(App.getContext());
		allItems.setText("Checked: " + String.valueOf(items.size()));
	}
	
	private void setAllUncheckedItems(View v) {
		TextView allItems = (TextView) v.findViewById(R.id.todounchecked);
		ArrayList<toDo> items = databaseAccess.getUnarchivedChecked(App.getContext());
		ArrayList<toDo> totalItems = databaseAccess.getUnarchivedItems(App.getContext());
		String num = String.valueOf(totalItems.size() - items.size());
		allItems.setText("Unchecked: " + num);
	}
	
	private void setAllArchivedItems(View v) {
		TextView allItems = (TextView) v.findViewById(R.id.archiveitems);
		ArrayList<toDo> items = databaseAccess.getArchivedItems(App.getContext());
		allItems.setText("Archived: " + String.valueOf(items.size()));
	}
	
	private void setAllArchivedChecked(View v) {
		TextView allItems = (TextView) v.findViewById(R.id.archivedchecked);
		ArrayList<toDo> items = databaseAccess.getArchivedChecked(App.getContext());
		allItems.setText("Checked: " + String.valueOf(items.size()));
	}
	
	private void setAllArchivedUnchecked(View v) {
		TextView allItems = (TextView) v.findViewById(R.id.archivedunchecked);
		ArrayList<toDo> items = databaseAccess.getArchivedChecked(App.getContext());
		ArrayList<toDo> totalItems = databaseAccess.getArchivedItems(App.getContext());
		String num = String.valueOf(totalItems.size() - items.size());
		allItems.setText("Unchecked: " + num);
	}

	
}
