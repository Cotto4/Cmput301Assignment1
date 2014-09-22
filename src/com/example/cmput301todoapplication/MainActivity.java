package com.example.cmput301todoapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.gson.Gson;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;



public class MainActivity extends Activity implements DialogFragmentListener{

	private int[] objectIds;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.setContext(this);
        
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.add_item_button) {
        	addItemDialog(this.findViewById(MODE_PRIVATE));
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void addItemDialog(View view) {
    	DialogFragment dialog = new AddItemDialog();
    	dialog.show(getFragmentManager(), INPUT_SERVICE);
    }
    
    public void removeItemDialog(View view) {
    	DialogFragment dialog = new RemoveItemDialog();
    	dialog.show(getFragmentManager(), INPUT_SERVICE);
    }
    
    public boolean saveObject(toDo item) {
    	
    	SharedPreferences preferences = getPreferences(MODE_PRIVATE);
    	Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(item);
        prefsEditor.putString(Integer.toString(item.getId()), json);
        prefsEditor.commit();
    	return true;
    }
    
    public void updateList()
    {
    	final ListView listView = (ListView) findViewById(R.id.itemListView);
        final ArrayList<toDo> items = new ArrayList<toDo>();
        SharedPreferences savedItems = getPreferences(Context.MODE_PRIVATE);
        
        Gson gson = new Gson();
        Map<String,?> entries = savedItems.getAll();
        Set<String> keys = entries.keySet();
        
        for (String key : keys) {
        	if (key.equals(R.string.object_id_key)) {
        		String json = savedItems.getString(key, "");
                int[] ids = gson.fromJson(json, int[].class);
        		this.objectIds = ids;
        	}
        	else {
	            String json = savedItems.getString(key, "");
	            toDo item = gson.fromJson(json, toDo.class);
	            if (item != null) {
	            	items.add(item);
	            }
        	}
    }
        final ItemArrayAdapter adapter = new ItemArrayAdapter(this,
                R.layout.todo_information, items);
            listView.setAdapter(adapter);
            
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                    int position, long id) {
//                  final String item = (String) parent.getItemAtPosition(position);
//                  view.animate().setDuration(2000).alpha(0)
//                      .withEndAction(new Runnable() {
//                        @Override
//                        public void run() {
//                          list.remove(item);
//                          adapter.notifyDataSetChanged();
//                          view.setAlpha(1);
//                        }
//                      });
                }

              });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
					View view, int position, long id) {
					//removeItemDialog(findViewById(MODE_PRIVATE));
					//return false;
					// Remove Item
		            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
		            dialog.setTitle(R.string.dialog_remove_item);
		            dialog.setMessage("Are you sure you wish to delete this item?");
		            final toDo itemToRemove = adapter.getItem(position);

		            dialog.setNegativeButton("Cancel", null);
		            dialog.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {        
		                items.remove(itemToRemove);
		                adapter.notifyDataSetChanged(); 
		             }
		            });
		             dialog.show();
		             // Return true to consume the click event. In this case the
		             // onListItemClick listener is not called anymore.  
		             return true;
		        }
				

			});
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

	@Override
	public void onReturnValue(String text) {
		SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
		Editor prefsEditor = preferences.edit();
		Gson gson = new Gson();
		String json = gson.toJson(objectIds);
        prefsEditor.putString(Integer.toString(R.string.object_id_key), json);
        prefsEditor.commit();
        Random rand = new Random();
        int id = rand.nextInt(100000);
        //TODO check for id collisions
        while (Arrays.asList(objectIds).contains(id)) {
        	id = rand.nextInt(100000);
        }
      //objectIds[objectIds.length] = id;
		toDo item = new toDo(id, text);
		
		if (saveObject(item)) {
			//refresh fragment to show changes
			updateList();
			final ListView listView = (ListView) findViewById(R.id.itemListView);
			((BaseAdapter) listView.getAdapter()).notifyDataSetChanged(); 
			//finish();
			//startActivity(getIntent());
		}
		
		
	}
}


