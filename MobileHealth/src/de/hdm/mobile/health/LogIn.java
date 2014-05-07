package de.hdm.mobile.health;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.preference.PreferenceManager;
/**
 * This class represents the LogIn GUI. The User has the choice to
 * login via his Google Account or create a local user.
 * 
 * @author Eric Schmidt
 */
public class LogIn extends Activity {
	private static final String PREF_FIRST_LAUNCH = "first";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);

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
