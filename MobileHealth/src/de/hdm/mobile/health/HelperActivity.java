package de.hdm.mobile.health;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;

public class HelperActivity extends FragmentActivity  {
	private static final String PREF_FIRST_LAUNCH = "first";
	
	@Override
	public void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
		if (!firstTimeCheck()){
			FoodLogFragment d = new FoodLogFragment();
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, d).commit();
			//startActivity(new Intent(this, Disclaimer.class));
		}else{
			FoodLogFragment d = new FoodLogFragment();
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, d).commit();
			//startActivity(new Intent(this, AddFood.class));
		   // finish();
		}
	}
	/**
	 * Check Shared Preferences if the user had opened the Application before
	 * 
	 * @return false if not launched for the first time
	 * @author Eric Schmidt
	 */
	private boolean firstTimeCheck(){
		return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PREF_FIRST_LAUNCH, true);
	}
}
