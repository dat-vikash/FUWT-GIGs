package com.freelancersunion.gigs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public class Job {
	//initialize class variables
	private final static String TAG="Job";
	public final int jobID;
	public final String categoryName;
	public final String company;
	public final String url;
	public final String title;
	public final String location;
	public final String description;
	public final Date createdOn;
	public final Date endsOn;
	public final int daysOld;
	public final String type;
	
	public Job(int id, String category, String company, String url, String title, String location, String description,
			String created, String ends, int days_old, String type) throws ParseException{
		this.jobID = id;
		this.categoryName = category;
		this.company = company;
		this.url = url;
		this.title = title;
		this.location = location;
		this.description = description;
		this.createdOn = determineCreatedOnDate(created) ;
		this.endsOn = determineEndOnDate(ends);
		this.daysOld = days_old;
		this.type = type;
		
	}
	
	private static Date determineCreatedOnDate(String representation) throws ParseException{
		/* The created on date is set weird. Need to strip away bad escape sequences, then use
		 * DateFormat to format the date.
		 * Returns a Date object.
		 */
		SimpleDateFormat formatter;
		representation = representation.replace('\\',' ');		
		formatter = new SimpleDateFormat("MM/dd/yyyy");
		
		return (Date)formatter.parse(representation);
	}
	
	private static Date determineEndOnDate(String representation) throws ParseException{
		/* The created on date is set weird. Need to strip away bad escape sequences, then use
		 * DateFormat to format the date.
		 * Returns a Date object.
		 */
		SimpleDateFormat formatter;
		String[] tmp = representation.split(" ");		
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		return (Date)formatter.parse(tmp[0]);
	}
	
	public String toString(){
		//toString method is used in arrayadapter to filter based on the text returned
		return this.categoryName + " " + this.company + " " + this.url + " " + this.title + " " + this.location + " " +
		this.description;
	}
	
	public String createdOnToString(){
		//returns a string representation of the createdOn date
		return ((this.createdOn.getMonth()+1) + "-" +this.createdOn.getDate() + "-" +(this.createdOn.getYear()+1900));
	}

}
