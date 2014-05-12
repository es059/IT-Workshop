package de.hdm.mobile.health;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Disclaimer extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disclaimer);
	}
	
	/**
	 * Load the disclaimer information text into the TextView
	 */
	@Override
	protected void onResume(){
		super.onResume();
		TextView disclaimer = (TextView) findViewById(R.id.Disclaimer);
		disclaimer.setText(R.string.disclaimer);
		disclaimer.setTextSize(10);
		disclaimer.setMovementMethod(new ScrollingMovementMethod());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.disclaimer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
