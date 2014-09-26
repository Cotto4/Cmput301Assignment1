package com.example.cmput301todoapplication;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.gson.Gson;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ArchivedActivity extends AbstractActivity implements DialogFragmentListener{

	public ItemArrayAdapter adapter;
	public AccessData databaseAccess;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.setContext(this);
        databaseAccess = new AccessData();
        updateList();
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks
        int id = item.getItemId();
        if (id == R.id.summary_button) {
        	summaryDialog(this.findViewById(MODE_PRIVATE));
        }
        if (id == R.id.add_item_button) {
        	addItemDialog(this.findViewById(MODE_PRIVATE));
        }
        if (id == R.id.seeAll) {
            Intent intent = new Intent(ArchivedActivity.this, MainActivity.class);
            startActivity(intent);      
            finish();
        }
        if (id == R.id.email_button) {
        	emailDialog(this.findViewById(MODE_PRIVATE));

        }
        return super.onOptionsItemSelected(item);
    }
	
    public void updateList()
    {
    	final ListView listView = (ListView) findViewById(R.id.itemListView);
        final ArrayList<toDo> items = new ArrayList<toDo>();
        SharedPreferences savedItems = getSharedPreferences("Items", Context.MODE_PRIVATE);
        
        Gson gson = new Gson();
        Map<String,?> entries = savedItems.getAll();
        Set<String> keys = entries.keySet();
        
        for (String key : keys) {
            String json = savedItems.getString(key, "");
            toDo item = gson.fromJson(json, toDo.class);
            if (item != null && item.getArchived() == true) {
            	items.add(item);
            }
        }
        
        setAdapter(listView, items);

    }
    
    public void setAdapter(ListView listview, final ArrayList<toDo> items) {
        adapter = new ItemArrayAdapter(this,
        		R.layout.todo_information, items);
    			listview.setAdapter(adapter);

    listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent,
			View view, int position, long id) {
			// Remove or archive item
            AlertDialog.Builder dialog = new AlertDialog.Builder(ArchivedActivity.this);
            dialog.setMessage("Delete or unarchive this item?");
            final toDo itemToRemove = adapter.getItem(position);

            dialog.setNegativeButton("Cancel", null);
            if(itemToRemove.getArchived() == false) {
            dialog.setNeutralButton("Archive", new AlertDialog.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					itemToRemove.setArchived(!itemToRemove.getArchived());
					databaseAccess.modifyObject(App.getContext(), itemToRemove);
					updateList();
					Toast.makeText(App.getContext(), "Archived " + itemToRemove.getText(), Toast.LENGTH_LONG).show();
				}
            
            });
            }
            else {
	            dialog.setNeutralButton("Unarchive", new AlertDialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						itemToRemove.setArchived(!itemToRemove.getArchived());
						databaseAccess.modifyObject(App.getContext(), itemToRemove);
						updateList();
						Toast.makeText(App.getContext(), "Unarchived " + itemToRemove.getText(), Toast.LENGTH_LONG).show();
					}
	            
	            });
            }
            dialog.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {        
                items.remove(itemToRemove);
                databaseAccess.deleteObject(App.getContext(), itemToRemove);
                adapter.notifyDataSetChanged(); 
                Toast.makeText(App.getContext(), "Deleted " + itemToRemove.getText(), Toast.LENGTH_LONG).show();
             }
            });
             dialog.show();  
             return true;
        }
		

	});
    }

	@Override
	public void onReturnValue(String text) {
        Random rand = new Random();
        int id = rand.nextInt(100000);
		toDo item = new toDo(id, text);
		if (databaseAccess.saveObject(App.getContext(), item)) {
			//refresh list to show changes
			updateList();
			final ListView listView = (ListView) findViewById(R.id.itemListView);
			((BaseAdapter) listView.getAdapter()).notifyDataSetChanged(); 
			Toast.makeText(App.getContext(), "Created " + item.getText(), Toast.LENGTH_LONG).show();
		}	
	}
}
