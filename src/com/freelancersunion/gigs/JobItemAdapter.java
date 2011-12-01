package com.freelancersunion.gigs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class JobItemAdapter extends ArrayAdapter<Job> {
	
	private static final String TAG = "JobItemAdapter"; 
	
	public JobItemAdapter(Context context, int resource, int textViewResourceId) {
		super(context, resource, textViewResourceId);
		// TODO Auto-generated constructor stub
	}


	
	public View getView(int position, View convertView, ViewGroup parent){
		Log.i(TAG," in getView() ");
		Job job = getItem(position);
		if (convertView == null) {
			//no previous views, inflate views
			LayoutInflater inflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflator.inflate(R.layout.list_item, parent, false);
		}
				
		//update textviews in layout
		TextView title = (TextView)convertView.findViewById(R.id.job_title);
		title.setText(job.title);
		
		TextView location = (TextView)convertView.findViewById(R.id.job_location);
		location.setText("Location: " + job.location);
		
		TextView createdOn = (TextView)convertView.findViewById(R.id.job_createdon);
		createdOn.setText("Created: " + job.createdOnToString());
		
		return convertView;
	}

}
