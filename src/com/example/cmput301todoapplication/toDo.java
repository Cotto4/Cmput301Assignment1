package com.example.cmput301todoapplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;

public class toDo {
	
	private int Id;
	private boolean Archived;
	private String Text;
	private boolean Checked;
	private Date DateCreated;
	
	public toDo(int id, String infoText) {
		Id = id;
		Text = infoText;
		Archived = false;
		Checked = false;
		DateCreated = new Date();
	}
	
	public String getDateCreated() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(this.DateCreated);
	}
	
	public int getId() {
		return Id;
	}
	public boolean getArchived() {
		return Archived;
	}
	
	public boolean getChecked() {
		return Checked;
	}
	
	public String getText() {
		return Text;
	}
	
	@Override
	public String toString() {
		return this.Text;
		
	}
	
	public void setSelected(boolean selected) {
		this.Checked = selected;
	}
	
	public boolean removeItem() {
		
		return false;
		
	}
}
