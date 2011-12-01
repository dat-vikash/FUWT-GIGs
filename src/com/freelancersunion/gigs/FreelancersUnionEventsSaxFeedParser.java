package com.freelancersunion.gigs;

import java.util.ArrayList;
import java.util.List;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Log;
import android.util.Xml;

public class FreelancersUnionEventsSaxFeedParser extends BaseFeedParser {
	private final String TAG = this.getClass().getName();
	protected FreelancersUnionEventsSaxFeedParser(String feedUrl) {
		super(feedUrl);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Message> parse() {
		//initialize varaibles
		final Message currentMessage = new Message();
		RootElement root = new RootElement("rss"); 
		final List<Message> messages = new ArrayList<Message>();
		Element channel = root.getChild("channel");
		Element item = channel.getChild(ITEM);
		item.setEndElementListener(new EndElementListener(){

			@Override
			public void end() {
				// TODO Auto-generated method stub
				messages.add(currentMessage.copy());				
			}			
		});
		item.getChild(TITLE).setEndTextElementListener(new EndTextElementListener(){
	            public void end(String body) {
	                currentMessage.setTitle(body);
	            }
	        });
		item.getChild(LINK).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentMessage.setLink(body);
            }
        });
		item.getChild(DESCRIPTION).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				Log.i(TAG,"DESCRIPTION: " + body);
				currentMessage.setDescription(body);
		   }
		});
		item.getChild(PUB_DATE).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentMessage.setDate(body);
            }
        });
		try {
            Xml.parse(this.getInputStream(), Xml.Encoding.ISO_8859_1, root.getContentHandler());
        } catch (Exception e) {
            Log.e(TAG,"Error parsing rss",e);
        }
        return messages;		
	}

}