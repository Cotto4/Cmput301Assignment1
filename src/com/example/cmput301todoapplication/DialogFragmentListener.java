package com.example.cmput301todoapplication;

// Interface used exclusively by an Activity requiring information
// from a dialog box. This method is implemented to pass the text
// between the two, which enables the activity to do with it as
// it pleases.

// This code/idea was taken from
// http://stackoverflow.com/questions/13435118/robust-way-to-pass-value-back-from-dialog-to-activity-on-android
// by user "antew"
// and is licensed under the Creative Commons Attribution Share Alike license.

public interface DialogFragmentListener {
	public void onReturnValue(String text);
}
