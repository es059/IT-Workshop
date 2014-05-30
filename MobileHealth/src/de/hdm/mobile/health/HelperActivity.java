package de.hdm.mobile.health;

import de.hdm.mobile.health.db.UserMapper;
import de.hdm.mobile.health.fragment.AddFood;
import de.hdm.mobile.health.fragment.Disclaimer;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class HelperActivity extends FragmentActivity  {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_helper);
		
		if (!firstTimeCheck()){
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
		UserMapper uMapper = new UserMapper(this);
		return uMapper.userExist();
	}
}
