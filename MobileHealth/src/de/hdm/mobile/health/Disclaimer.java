package de.hdm.mobile.health;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Disclaimer extends Fragment{
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.disclaimer, menu);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	    View view = inflater.inflate(R.layout.fragment_disclaimer,
	            container, false);
	    return view;
	}
	
	/**
	 * Load the disclaimer information text into the TextView
	 */
	@Override
	public void onResume(){
		super.onResume();
		TextView disclaimer = (TextView) getView().findViewById(R.id.Disclaimer);
		disclaimer.setText(R.string.disclaimer);
		disclaimer.setTextSize(10);
		disclaimer.setMovementMethod(new ScrollingMovementMethod());
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
