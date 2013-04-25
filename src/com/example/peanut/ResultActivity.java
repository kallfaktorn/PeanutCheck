package com.example.peanut;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.juicepick.R;

public class ResultActivity extends Activity  {
	
	JSONObject foodStuffJSON = null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        
        if(!isConnected())
        {
        	displayFailView();
        }
        else
        {
        	Intent intent = getIntent();
            String barCode = intent.getStringExtra("BARCODE");
            String webPage = "http://openfooddb.com/food_stuffs/bar_code/";
        	webPage += barCode;
        	
        	String output = "Den här produkten kunde inte hittas i databasen.";
        	
            try {
    			String foodStuff = Utils.executeHttpGet(webPage);
    			
    			if(foodStuff.equals("null\n"))
    			{
    				displayProblemView();
    			}
    			else
    			{
    				foodStuffJSON = new JSONObject(foodStuff);
    				
    				boolean has_peanuts = false;
    				has_peanuts =  foodStuff.contains("peanut") || foodStuff.contains("jordnöt");
    				
    				if(has_peanuts == true)
    				{
    					displayWarningView();
    					//output = "Se upp! Den här produkten kan innehålla jordnötter";
    				}
    				else
    				{
    					displayOkView();
    					//output = "Den här produkten skall inte innehålla jordnötter";
    				}
    			}
    			
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }
        
        LinearLayout layout_scan_again = (LinearLayout) findViewById(R.id.layout_scan_again);
        layout_scan_again.setVisibility(View.VISIBLE);
        
        Button button_scan= (Button) findViewById(R.id.button_scan);
        button_scan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            	Intent mainIntent = new Intent(ResultActivity.this, MainActivity.class);
        		startActivity(mainIntent);
            }
        });
        
        //displayProblemView();
        //displayOkView();
        //displayFailView();
        //displayWarningView();
	}
	
	private boolean isConnected()
	{
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		
		if (netInfo != null && netInfo.isConnected() && netInfo.isAvailable()) {
		   
			return true;
		}
		
		else return false;
	}
	
	private void displayScanAgainButton(String url)
	{
		
	}
	
	private void displayProblemView()
	{
		TextView text_title = (TextView) findViewById(R.id.title);
		text_title.setTextColor(getResources().getColor(R.color.black));
		text_title.setText(R.string.title_problem);
		
		LinearLayout layout_title = (LinearLayout) findViewById(R.id.layout_title);
		layout_title.setVisibility(View.VISIBLE);
		
		TextView text_general = (TextView) findViewById(R.id.general);
		text_general.setText(R.string.text_product_not_found);
		text_general.setVisibility(View.VISIBLE);
		
		LinearLayout layout_general = (LinearLayout) findViewById(R.id.layout_general);
		layout_general.setVisibility(View.VISIBLE);
		
		TextView text_full = (TextView) findViewById(R.id.text_more_info);
		Linkify.addLinks(text_full, Linkify.ALL);
		text_full.setVisibility(View.VISIBLE);
	}

	private void displayFailView()
	{
		TextView text_title = (TextView) findViewById(R.id.title);
		text_title.setTextColor(getResources().getColor(R.color.red));
		text_title.setText(R.string.title_error);
		
		LinearLayout layout_title = (LinearLayout) findViewById(R.id.layout_title);
		layout_title.setVisibility(View.VISIBLE);
		
		TextView text_general = (TextView) findViewById(R.id.general);
		text_general.setGravity(Gravity.CENTER_HORIZONTAL);
		text_general.setText(R.string.text_no_internet);
		
		LinearLayout layout_general = (LinearLayout) findViewById(R.id.layout_general);
		layout_general.setVisibility(View.VISIBLE);
		
		TextView text_check_connection = (TextView) findViewById(R.id.text_check_connection);
		text_check_connection.setVisibility(View.VISIBLE);
	}

	private void displayOkView()
	{
		TextView text_title = (TextView) findViewById(R.id.title);
		text_title.setTextColor(getResources().getColor(R.color.green));
		text_title.setText(R.string.title_ok);
		
		LinearLayout layout_title = (LinearLayout) findViewById(R.id.layout_title);
		layout_title.setVisibility(View.VISIBLE);
		
		LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
			    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

	    lparams.gravity = Gravity.CENTER_HORIZONTAL;

		LinearLayout layout_general = (LinearLayout) findViewById(R.id.layout_general);
		layout_general.setLayoutParams(lparams);

		TextView text_general = (TextView) findViewById(R.id.general);
		text_general.setGravity(Gravity.CENTER_HORIZONTAL);
		text_general.setText(R.string.text_no_peanuts);
		
		layout_general.setVisibility(View.VISIBLE);

		TextView text_click_here = (TextView) findViewById(R.id.click_here);
		text_click_here.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            	browseFoodStuff();
            }
        });

		LinearLayout layout_read_more = (LinearLayout) findViewById(R.id.layout_read_more);
		layout_read_more.setVisibility(View.VISIBLE);
	}

	private void displayWarningView()
	{
		TextView text_title = (TextView) findViewById(R.id.title);
		text_title.setTextColor(getResources().getColor(R.color.orange));
		text_title.setText(R.string.title_warning);
		
		LinearLayout layout_title = (LinearLayout) findViewById(R.id.layout_title);
		layout_title.setVisibility(View.VISIBLE);
		
		TextView text_general = (TextView) findViewById(R.id.general);
		text_general.setGravity(Gravity.CENTER_HORIZONTAL);
		text_general.setText(R.string.text_peanuts);
		
		LinearLayout layout_general = (LinearLayout) findViewById(R.id.layout_general);
		layout_general.setVisibility(View.VISIBLE);
		
		TextView text_click_here = (TextView) findViewById(R.id.click_here);
		text_click_here.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            	browseFoodStuff();
            }
        });

		LinearLayout layout_read_more = (LinearLayout) findViewById(R.id.layout_read_more);
		layout_read_more.setVisibility(View.VISIBLE);
	}
	
	private void browseFoodStuff()
	{
		try {
			String foodStuffId = foodStuffJSON.getString("id");
			
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.openfooddb.com/food_stuffs/" + foodStuffId));
        	startActivity(browserIntent);
        	
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}