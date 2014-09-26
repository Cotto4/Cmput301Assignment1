package com.example.cmput301todoapplication;


public class toDo {
	
	private int Id;
	private boolean Archived;
	private String Text;
	private boolean Checked;
	
	public toDo(int id, String infoText) {
		Id = id;
		Text = infoText;
		Archived = false;
		Checked = false;
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
	
	public void setArchived(boolean archived) {
		Archived = archived;
	}


	public void setText(String text) {
		Text = text;
	}


	public void setChecked(boolean checked) {
		Checked = checked;
	}


	@Override
	public String toString() {
		return this.Text;
		
	}
	

}
