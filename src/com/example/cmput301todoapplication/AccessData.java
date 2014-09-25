package com.example.cmput301todoapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

public class AccessData{

	public ArrayList<toDo> getAllItems(Context context) {
		final ArrayList<toDo> items = new ArrayList<toDo>();
		SharedPreferences savedItems = context.getSharedPreferences("Items",Context.MODE_PRIVATE);
        
        Gson gson = new Gson();
        Map<String,?> entries = savedItems.getAll();
        Set<String> keys = entries.keySet();
        
        for (String key : keys) {
        	if (key.equals(R.string.object_id_key)) {
        	}
        	else {
	            String json = savedItems.getString(key, "");
	            toDo item = gson.fromJson(json, toDo.class);
	            if (item != null) {
	            	items.add(item);
	            }
        	}
        }
		return items;
	}
	public CharSequence[] allItemsText(Context context) {
        final ArrayList<String> items = new ArrayList<String>();
        SharedPreferences savedItems = context.getSharedPreferences("Items",Context.MODE_PRIVATE);
        
        Gson gson = new Gson();
        Map<String,?> entries = savedItems.getAll();
        Set<String> keys = entries.keySet();
        
        for (String key : keys) {
        	if (key.equals(R.string.object_id_key)) {
        	}
        	else {
	            String json = savedItems.getString(key, "");
	            toDo item = gson.fromJson(json, toDo.class);
	            if (item != null) {
	            	items.add(item.getText());
	            }
        	}
        }
        final CharSequence[] itemText = items.toArray(new CharSequence[items.size()]);
        return itemText;
	}
	
    public void saveObject(Context context, toDo item) {
    	
    	SharedPreferences preferences = context.getSharedPreferences("Items",0);
    	Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(item);
        prefsEditor.putString(Integer.toString(item.getId()), json);
        prefsEditor.commit();
    }
    public void deleteObject(Context context, toDo item) {
    	SharedPreferences preferences = context.getSharedPreferences("Items",0);
    	Editor prefsEditor = preferences.edit();
    	prefsEditor.remove(Integer.toString(item.getId()));
    	prefsEditor.commit();
    }
    
    public void modifyObject(Context context, toDo item) {
    	SharedPreferences preferences = context.getSharedPreferences("Items",0);
    	Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(item);
        prefsEditor.remove(Integer.toString(item.getId()));
        prefsEditor.putString(Integer.toString(item.getId()), json);
        prefsEditor.commit();
    }
}
