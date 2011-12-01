/* This connects to Freelancers Union RESTful services, specificly the gigs api.
 * @author: Vikash Dat (vdat@freelancersunion.org)  
 */
package com.freelancersunion.gigs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.util.Log;

public class FURestClient {
	//declare class constants
	private static final String REST_URL = "https://be.freelancersunion.org/gigs/api/api.php?action=getJobs&days_behind=30&response=json&type=1&category=0&count=100&random=1";
	private static final String TAG = "FURestClient";
	
	public static String connect(){
		/* This function connects to a given rest service based on the default RESTful url and expects a JSON response. 
		 * It returns a string representation of the parsed JSON.
		 */
		return connect(REST_URL);
	}
	
	public static String connect(String url) {
		/* This function connects to a given rest service and expects a JSON response. 
		 * It parses the JSON response then returns a string representation.
		 */
		
		//create an HttpClient instance
		HttpClient httpclient = new DefaultHttpClient();
		
		//create a request instance
		HttpGet httpget;
		
		//create a response instance
		HttpResponse response;
		
		//create a method return instance
		String jsonString = "";
		
		try {
			 //prepare request instance
			httpget = new HttpGet(url);
			response = httpclient.execute(httpget);
			
			//examine response status
			//Only concerned about 200
			//log all others
			if (response.getStatusLine().getStatusCode() == 200) {
				// Connected successfully, get response content
				
				//get entity from response
				HttpEntity entity = response.getEntity();
				
				if (entity != null){
					// get content stream
					InputStream instream = entity.getContent();
					
					jsonString = convertStreamToString(instream);
			
				}
				
			} else {
				Log.e(TAG,"Unable to load rest service: " + response.getStatusLine().toString());
			}
		}catch(IllegalArgumentException e){
			Log.e(TAG,"Improper URL passed to connect().",e);
			return "ERROR: " + e.getMessage(); //return error string
		} catch (ClientProtocolException e) {
			Log.e(TAG,"Http protocol error in connect().",e);
			return "ERROR: " + e.getMessage(); //return error string
		} catch (IOException e) {
			Log.e(TAG,"Possible error or aborted connection in connect().",e);
			return "ERROR: " + e.getMessage(); //return error string
		}
		
		return jsonString;
	}
	
	private static String convertStreamToString(InputStream is) {  
        /* 
         * To convert the InputStream to String we use the BufferedReader.readLine() 
         * method. We iterate until the BufferedReader returns null which means 
         * there's no more data to read. Each line will appended to a StringBuilder 
         * and returned as String. 
         */  
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));  
        StringBuilder sb = new StringBuilder();  
  
        String line = null;  
        try {  
            while ((line = reader.readLine()) != null) {  
                sb.append(line + "\n");  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                is.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
        //freelancers gigs api is defective, does not return correct json response
        //reformat string response to confirm to expected json response
        //return sb.toString();
        return  "{\"Results\":" + sb.substring(11, sb.length()-2) + "}\n" ;
    }  
	
	
	

}
