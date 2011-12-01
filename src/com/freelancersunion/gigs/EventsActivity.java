package com.freelancersunion.gigs;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventsActivity extends ListActivity{
	//initialize variables for activity
	private final String TAG = this.getClass().getName();
	private  ArrayAdapter<Message> eventAdapter;
	private Intent intent;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.events);
		eventAdapter = new EventsItemAdapter(this.getApplicationContext(),R.layout.events_list_item, R.id.event_slug); 
				
		//retrieve events from RSS feed		
        FreelancersUnionEventsSaxFeedParser myEventsParser = new FreelancersUnionEventsSaxFeedParser("http://www.freelancersunion.org/events/events.rss");
        List<Message> mlist = myEventsParser.parse();
        Log.i(TAG,"retrieved messages from FreelancersUnionEventsSaxFeedParser: " + mlist.size());
        //Not sure if this is correct, probably better way to populate ArrayAdapter with list items
        for(Message m: mlist){
        	Log.i(TAG,"Message title: " + m.getTitle());
        	Log.i(TAG,"Message description : " + m.getDescription().length());
        	eventAdapter.add(m);
        }        
        Log.i(TAG,"populated messages into ArrayAdapter: " + eventAdapter.getCount());
        
        //set the adapter
        setListAdapter(eventAdapter);
	}
	
	//set up clicklistener
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	Log.i(TAG,"OnListItemClick() item clicked: " + Integer.toString(position));
    	
    	//resuse intent to pass to webivew activity
    	intent = new Intent(this.getApplicationContext(),EventWebView.class);
    	
    	//load intent with event data
    	Log.i(TAG,"before intent (description): " + eventAdapter.getItem(position).getDescription().length());
    	intent.putExtra("description", eventAdapter.getItem(position).getDescription());    	
        startActivity(intent);
             
    }

}