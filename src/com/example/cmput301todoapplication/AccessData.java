package com.example.cmput301todoapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

public class AccessData{

	
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
