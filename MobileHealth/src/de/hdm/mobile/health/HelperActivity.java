package de.hdm.mobile.health;

import de.hdm.mobile.health.fragment.AddFood;
import de.hdm.mobile.health.fragment.Disclaimer;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;


public class HelperActivity extends FragmentActivity  {
	private static final String PREF_FIRST_LAUNCH = "first";
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_helper);
		
		if (!firstTimeCheck()){

			FoodLogFragment d = new FoodLogFragment();
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, d).commit();
			//startActivity(new Intent(this, Disclaimer.class));
			/**
			 * Start the LogIn Workflow
			 * 
			 * @author Eric Schmidt
			 */
		    FragmentTransaction transaction = getFragmentManager().beginTransaction();
	        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	        transaction.replace(R.id.fragment_container, new Disclaimer(), "Disclaimer");
	        transaction.addToBackStack("Disclaimer");
	        transaction.commit();

		}else{

			FoodLogFragment d = new FoodLogFragment();
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, d).commit();
			//startActivity(new Intent(this, AddFood.class));
		   // finish();

			/**
			 * Remove the LogIn Navigation Buttons and call the first Fragment
			 * 
			 * @author Eric Schmidt
			 */
		    FragmentTransaction transaction = getFragmentManager().beginTransaction();
		    transaction.hide(getFragmentManager().findFragmentById(R.id.LogIn_Bottom));
	        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	        transaction.replace(R.id.fragment_container, new AddFood(), "AddFood");
	        transaction.addToBackStack(null);
	        transaction.commit();

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
