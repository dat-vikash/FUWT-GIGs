package com.freelancersunion.gigs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventsItemAdapter extends ArrayAdapter<Message> {
	
	private static final String TAG = "EventsItemAdapter";
		
	public EventsItemAdapter(Context context, int resource, int textViewResourceId) {
		super(context, resource, textViewResourceId);
		// TODO Auto-generated constructor stub
	}


	
	public View getView(int position, View convertView, ViewGroup parent){
		Log.i(TAG," in getView() ");
		Message event = getItem(position);
		if (convertView == null) {
			//no previous views, inflate views
			LayoutInflater inflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflator.inflate(R.layout.events_list_item, parent, false);
		}
				
		//update textviews in layout
		TextView title = (TextView)convertView.findViewById(R.id.event_title);
		title.setText(event.getTitle());
		
		TextView link = (TextView)convertView.findViewById(R.id.event_link);
		link.setText("Link: " + event.getLink().toString());
		
		TextView date = (TextView)convertView.findViewById(R.id.event_date);
		date.setText("Date: " + event.getDate().toString());
		
		return convertView;
	}

}

