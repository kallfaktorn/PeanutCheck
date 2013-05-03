package com.openfooddb.peanut;

import com.openfooddb.peanut.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {
	
	private Intent resumeIntent = null;

	@Override
 	public void onCreate(Bundle savedInstanceState) {
  		super.onCreate(savedInstanceState);

  		//setContentView(R.layout.activity_main);

  		// TODO: webPage = executeHttpGet
  		// *     make jsonObject
  		// *     take care of nutrition values

  		//String webPage = "http://openfooddb.com/food_stuffs/bar_code/";

  		//JSONObject(String json)

  		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
		intent.putExtra("SAVE_HISTORY", false);

		startActivityForResult(intent, 0);

  		String contents = null;

  		//Intent resultIntent = new Intent(MainActivity.this, ResultActivity.class);
		//resultIntent.putExtra("BARCODE", contents);
		//startActivity(resultIntent);
 	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {

				// Handle successful scan
				String contents = intent.getStringExtra("SCAN_RESULT");

				Intent resultIntent = new Intent(MainActivity.this, ResultActivity.class);
				resultIntent.putExtra("BARCODE", contents);
				startActivity(resultIntent);

			} else if (resultCode == RESULT_CANCELED) {
				resumeIntent = intent;
				moveTaskToBack(true);
			}
		} else {
		}
	}

//	@Override
//	protected void onResume() {
//	    super.onResume();
//	    
//	    Intent intent = getIntent();
//	    finish();
//	    startActivity(intent);
//	}
}