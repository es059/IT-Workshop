package de.hdm.mobile.health.fragment;

import de.hdm.mobile.health.R;
import de.hdm.mobile.health.R.array;
import de.hdm.mobile.health.R.id;
import de.hdm.mobile.health.R.layout;
import de.hdm.mobile.health.R.menu;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class LoginAdvanced extends Fragment {
	
	/**
	 * Assign the Layout to the Activity and fill the spinner with the activity levels
	 * 
	 * @author Eric Schmidt
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	    View view = inflater.inflate(R.layout.fragment_login_advanced,
	            container, false);
	    
		Spinner activityLevelSpinner = (Spinner) view.findViewById(R.id.ActivityLevel);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
		        R.array.activity_level, android.R.layout.simple_spinner_item);
		activityLevelSpinner.setAdapter(adapter);
		
	    return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.disclaimer, menu);
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
