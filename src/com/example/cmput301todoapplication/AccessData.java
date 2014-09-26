package com.example.cmput301todoapplication;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

// Data access class. Within this app, the data is stored in the SharedPreferences
// area. This class gives access to these preferences, with a variety of methods 
// for the items stored within. Each class wanting to make use of the preferences
// should instantiate an instance of this class

// Multiple methods in this class use code taken from 
// http://stackoverflow.com/questions/17401799/how-to-know-how-many-shared-preference-in-shared-preferences-in-android
// to aid in pulling the preferences and referring to each individual item.
// This code is licensed under the Creative Commons Attribution Share Alike license.

public class AccessData{

	// get all toDo items
	public ArrayList<toDo> getAllItems(Context context) {
		final ArrayList<toDo> items = new ArrayList<toDo>();
		SharedPreferences savedItems = context.getSharedPreferences("Items",Context.MODE_PRIVATE);
        
        Gson gson = new Gson();
        Map<String,?> entries = savedItems.getAll();
        Set<String> keys = entries.keySet();
        
        for (String key : keys) {
            String json = savedItems.getString(key, "");
            toDo item = gson.fromJson(json, toDo.class);
            if (item != null) {
            	items.add(item);
            }
        }
		return items;
	}
	
	// get the textbody from every toDo item
	public CharSequence[] allItemsText(Context context) {
        final ArrayList<String> items = new ArrayList<String>();
        SharedPreferences savedItems = context.getSharedPreferences("Items",Context.MODE_PRIVATE);
        
        Gson gson = new Gson();
        Map<String,?> entries = savedItems.getAll();
        Set<String> keys = entries.keySet();
        
        for (String key : keys) {
            String json = savedItems.getString(key, "");
            toDo item = gson.fromJson(json, toDo.class);
            if (item != null) {
            	items.add(item.getText());
            }
        }
        final CharSequence[] itemText = items.toArray(new CharSequence[items.size()]);
        return itemText;
	}
	
	// store a toDo object in SharedPreferences
	
	// Code in this method is taken from
	// http://stackoverflow.com/questions/7145606/how-android-sharedpreferences-save-store-object
	// and is licensed under the Creative Commons Attribution Share Alike license.
    public boolean saveObject(Context context, toDo item) {
    	try {
	    	SharedPreferences preferences = context.getSharedPreferences("Items",0);
	    	Editor prefsEditor = preferences.edit();
	        Gson gson = new Gson();
	        String json = gson.toJson(item);
	        prefsEditor.putString(Integer.toString(item.getId()), json);
	        prefsEditor.commit();
	        return true;
    	}
    	catch (Exception e){
    		return false;
    	}
    }
    
    // delete a toDo object
    public void deleteObject(Context context, toDo item) {
    	SharedPreferences preferences = context.getSharedPreferences("Items",0);
    	Editor prefsEditor = preferences.edit();
    	prefsEditor.remove(Integer.toString(item.getId()));
    	prefsEditor.commit();
    }
    
    // replace a toDo object with a modified version of itself
    public void modifyObject(Context context, toDo item) {
    	SharedPreferences preferences = context.getSharedPreferences("Items",0);
    	Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(item);
        String id = Integer.toString(item.getId());
        prefsEditor.remove(id);
        prefsEditor.putString(id, json);
        prefsEditor.commit();
    }
    
    // Summary method: used to get count
    public ArrayList<toDo> getUnarchivedItems(Context context) {
    	ArrayList<toDo> unArchItems = new ArrayList<toDo>();
    	ArrayList<toDo> items = getAllItems(context);
    	for (toDo item : items) {
    		if (item.Archived == false) {
    			unArchItems.add(item);
    		}
    	}
    	return unArchItems;
    }
    
  // Summary method: used to get count
    public ArrayList<toDo> getArchivedItems(Context context) {
    	ArrayList<toDo> archItems = new ArrayList<toDo>();
    	ArrayList<toDo> items = getAllItems(context);
    	for (toDo item : items) {
    		if (item.Archived == true) {
    			archItems.add(item);
    		}
    	}
    	return archItems;
    }
    
  // Summary method: used to get count
    public ArrayList<toDo> getUnarchivedChecked(Context context) {
    	ArrayList<toDo> unArchItems = new ArrayList<toDo>();
    	ArrayList<toDo> items = getUnarchivedItems(context);
    	for (toDo item : items) {
    		if (item.getChecked() == true) {
    			unArchItems.add(item);
    		}
    	}
    	return unArchItems;
    }
    
  // Summary method: used to get count
    public ArrayList<toDo> getArchivedChecked(Context context) {
    	ArrayList<toDo> archItems = new ArrayList<toDo>();
    	ArrayList<toDo> items = getArchivedItems(context);
    	for (toDo item : items) {
    		if (item.getChecked() == true) {
    			archItems.add(item);
    		}
    	}
    	return archItems;
    }
}
