package com.example.cmput301todoapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

public class AccessData extends Activity{

    public boolean saveObject(toDo item) {
    	
    	SharedPreferences preferences = getPreferences(MODE_PRIVATE);
    	Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(item);
        prefsEditor.putString(Integer.toString(item.getId()), json);
        prefsEditor.commit();
    	return true;
    }
}
