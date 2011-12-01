package com.freelancersunion.gigs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class EventWebView extends Activity{
	private static final String TAG = "EventWebView";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_webview);
		//setup webview
		WebView mWebView = (WebView) findViewById(R.id.webview);
    	WebSettings webSettings = mWebView.getSettings();
    	webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(false);
        webSettings.setSupportZoom(false);
    	
        //set chrome client
        mWebView.setWebChromeClient(new WebChromeClient());	
        
        //get description from intent
        Bundle extras = getIntent().getExtras();
        String description =  extras.getString("description");
        Log.i(TAG,"Description: " + description);
        mWebView.loadData(description,"text/html", "ISO-8859-1");
	}

}