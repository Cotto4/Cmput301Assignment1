package com.example.cmput301todoapplication;

import android.app.Activity;
import android.app.DialogFragment;
import android.view.View;

public abstract class AbstractActivity extends Activity{
    
    protected void addItemDialog(View view) {
    	DialogFragment dialog = new AddItemDialog();
    	dialog.show(getFragmentManager(), INPUT_SERVICE);
    }

    protected void emailDialog(View view) {
    	DialogFragment dialog = new EmailItemDialog();
    	dialog.show(getFragmentManager(), INPUT_SERVICE);
    }
    
    protected void summaryDialog(View view) {
    	DialogFragment dialog = new SummaryDialog();
    	dialog.show(getFragmentManager(), INPUT_SERVICE);
    }
}
