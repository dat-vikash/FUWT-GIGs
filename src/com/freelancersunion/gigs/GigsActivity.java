package com.freelancersunion.gigs;

import java.text.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class GigsActivity extends ListActivity {
	//initialize variables for app
	private static final String TAG = "GigsActivity";
	private  ArrayAdapter<Job> jobAdapter;
	private EditText searchBox; 
	private Button clearSearchBox;
	
	/** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.gigs);
    	jobAdapter = new JobItemAdapter(this.getApplicationContext(),R.layout.list_item, R.id.job_slug); 
    	//setup UI components
    	searchBox = (EditText) findViewById(R.id.SearchBox);
    	searchBox.addTextChangedListener(new SearchBoxWatcher());
    	clearSearchBox = (Button) findViewById(R.id.clear_search_button);
    	clearSearchBox.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchBox.setText("");
			}
		});
    	
    	//retrieve jobs from RESTful service    	
        JSONObject myJobs;
        try {
        	//convert json string resposne into JSON object
			myJobs = new JSONObject(FURestClient.connect());
			//jobtracker service returns an array of hashes
			JSONArray jobs = myJobs.getJSONArray("Results");
			Log.i(TAG,"jobs length: " + jobs.length());
			
			
			JSONObject jobData;
			
			for(int i=0;i < jobs.length(); i++ )
			{
				jobData = jobs.getJSONObject(i);
				jobAdapter.add(new Job(jobData.getInt("id"),jobData.getString("category_name"),jobData.getString("company"),
						jobData.getString("url"),jobData.getString("title"),jobData.getString("location"),jobData.getString("description"),
						jobData.getString("created_on"),jobData.getString("closed_on"),jobData.getInt("days_old"), jobData.getString("type_name")));
			}
	        
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(TAG,"Error generating jobs from json resposne: ",e);
		}catch (ParseException e){
			Log.e(TAG,"Error generating jobs from json resposne: ",e);
		}
		Log.i(TAG,"jobsList length: " + jobAdapter.getCount() );
		
		//setup adapter
		
		setListAdapter(jobAdapter);
    	
    }
    
    //set up clicklistener
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	Log.i(TAG,"OnListItemClick() item clicked: " + Integer.toString(position));
    	
    	//show dialog with job information
		Dialog jobDialog = new Dialog(this);
		jobDialog.setContentView(R.layout.job_post_dialog);
		jobDialog.setTitle("Job Description");
		jobDialog.setCancelable(true);
		//set up dialog views
		TextView jobPostTitle = (TextView) jobDialog.findViewById(R.id.job_post_dialog_title);
		TextView jobPostCompany = (TextView) jobDialog.findViewById(R.id.job_post_dialog_company);
		TextView jobPostLocation = (TextView) jobDialog.findViewById(R.id.job_post_dialog_location);
		TextView jobPostDescription = (TextView) jobDialog.findViewById(R.id.job_post_dialog_description);
		

		
		jobPostTitle.setText(jobAdapter.getItem(position).title);
		jobPostCompany.setText("Comany: "+jobAdapter.getItem(position).company);
		jobPostLocation.setText("Location: "+ jobAdapter.getItem(position).location);
		jobPostDescription.setText("Description: " +jobAdapter.getItem(position).description);
		jobPostDescription.setMovementMethod(new ScrollingMovementMethod()); 
		
		//TODO:setup button
//		Button button = (Button) dialog.findViewById(R.id.Button01);
//        button.setOnClickListener(new OnClickListener() {
//        @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
		
		jobDialog.show();
    }
    
    private class SearchBoxWatcher implements TextWatcher {
//    	Context parent;
//    	public SearchBoxWatcher(Context parent)
//    	{
//    		this.parent = parent;
//    	}
    		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			/* Attempt for realtime searching. When text is entered into search box, we will find jobs that fit 
			 * the search parameters. Currently searching all job attributes. May want to narrow this down in future.
			 */

			Log.i(TAG,"Search parameter: " + s); 
			jobAdapter.getFilter().filter(s.toString().trim());

		}
    	
    }
    
    
}