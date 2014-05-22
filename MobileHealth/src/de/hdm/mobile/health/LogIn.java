package de.hdm.mobile.health;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.preference.PreferenceManager;
/**
 * This class represents the LogIn GUI. The User has the choice to
 * login via his Google Account or create a local user.
 * 
 * @author Eric Schmidt
 */
public class LogIn extends Activity{
	private static final String PREF_FIRST_LAUNCH = "first";
	
	/**
	 * Assign the Layout to the Activity and fill the spinner with the activity levels
	 * 
	 * @author Eric Schmidt
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_login);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
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
	
	
	/**
	 * Storing in Shared Preferences if the Application is starts it for the first time
	 * 
	 * @param false if not first time
	 */
	protected void storeSharedPref(Boolean firstTime){
		SharedPreferences isLogged = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = isLogged.edit();
		editor.putBoolean(PREF_FIRST_LAUNCH, firstTime);
		editor.commit();
	}
}
