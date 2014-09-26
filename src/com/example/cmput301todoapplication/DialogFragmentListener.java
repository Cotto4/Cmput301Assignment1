package com.example.cmput301todoapplication;

//Interface used exclusively by an Activity requiring information
//from a dialog box. This method is implemented to pass the text
//between the two, which enables the activity to do with it as
//it pleases

public interface DialogFragmentListener {
	public void onReturnValue(String text);
}
