package com.example.cmput301todoapplication;

import android.app.Application;
import android.content.Context;

// Class to keep a global context, useful when using dialogs

//  Code was taken from 
// http://stackoverflow.com/questions/12320857/how-to-get-my-activity-context
// and is licensed under the Creative Commons Attribution Share Alike license.

public class App extends Application {
	
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context context) {
        mContext = context;
    }
}
