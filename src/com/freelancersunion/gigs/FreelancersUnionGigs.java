package com.freelancersunion.gigs;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;



public class FreelancersUnionGigs extends TabActivity {
	private static final String TAG = "FreelancersUnionGigs";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.i(TAG,"in onCreate");
		
		Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab
	    
	    // Create an Intent to launch each Activity for the tab (to be reused)
	    //Gigs Tab
	    intent = new Intent().setClass(this, GigsActivity.class);	    
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec(res.getString(R.string.label_gigs)).setIndicator(res.getString(R.string.label_gigs),
	                      res.getDrawable(R.drawable.tab_gigs))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    //Events Tab
	    intent = new Intent().setClass(this, EventsActivity.class);	    
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec(res.getString(R.string.label_events)).setIndicator(res.getString(R.string.label_events),
	                      res.getDrawable(R.drawable.tab_events))
	                  .setContent(intent);
	    tabHost.addTab(spec);
		
	    tabHost.setCurrentTab(0);
	}
	
    
    
}