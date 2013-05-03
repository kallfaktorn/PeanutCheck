package com.openfooddb.peanut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Utils {

	public static String executeHttpGet(String cis) throws Exception {

		BufferedReader in = null;

		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(cis);

			HttpResponse response = client.execute(request);
			in = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));

			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");

			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}

			in.close();
	    	String page = sb.toString();
	    	
	    	return page;

		} finally {

    		if (in != null) {
	        
    			try {
	            	in.close();
	        	} catch (IOException e) {
	        		e.printStackTrace();
	        	}
    		}
		}
	}
}
