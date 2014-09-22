package com.example.cmput301todoapplication;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ItemArrayAdapter extends ArrayAdapter<toDo>{

    HashMap<toDo, Integer> mIdMap = new HashMap<toDo, Integer>();
    private MainActivity databaseAccess;
    public ItemArrayAdapter(Context context, int textViewResourceId,
        List<toDo> objects) {
      super(context, textViewResourceId, objects);
      for (int i = 0; i < objects.size(); ++i) {
        mIdMap.put(objects.get(i), i);
      }
      databaseAccess = (MainActivity) context;
    }
    private class ViewHolder {
    	   TextView code;
    	   CheckBox name;
    	  }
    	 
    	  @SuppressLint("InflateParams") @Override
    	  public View getView(int position, View convertView, ViewGroup parent) {
    	 
    	   ViewHolder holder = null;
    	   Log.v("ConvertView", String.valueOf(position));
    	 
    	   if (convertView == null) {
    	   LayoutInflater vi = (LayoutInflater)getContext().getSystemService(
    	     Context.LAYOUT_INFLATER_SERVICE);
    	   convertView = vi.inflate(R.layout.todo_information, null);
    	 
    	   holder = new ViewHolder();
    	   holder.code = (TextView) convertView.findViewById(R.id.code);
    	   holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
    	   convertView.setTag(holder);

    	    holder.name.setOnClickListener( new View.OnClickListener() {  
    	     public void onClick(View v) {  
    	      CheckBox cb = (CheckBox) v ;  
    	      toDo item = (toDo) cb.getTag();
    	      item.setSelected(!item.getChecked());
    	      databaseAccess.saveObject(item);
    	     }  
    	    });  
    	   } 
    	   else {
    	    holder = (ViewHolder) convertView.getTag();
    	   }
    	 
    	   toDo item = getItem(position);
    	   holder.code.setText(item.getText());
    	   holder.name.setChecked(item.getChecked());
    	   holder.name.setTag(item);
    	   return convertView;
    	 
    	  }


	@Override
    public long getItemId(int position) {
      toDo item = getItem(position);
      return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
      return true;
    }


  }
