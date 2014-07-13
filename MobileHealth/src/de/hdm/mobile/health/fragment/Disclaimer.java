package de.hdm.mobile.health.fragment;

import de.hdm.mobile.health.FoodLogFragment;
import de.hdm.mobile.health.R;
import de.hdm.mobile.health.R.id;
import de.hdm.mobile.health.R.layout;
import de.hdm.mobile.health.R.menu;
import de.hdm.mobile.health.R.string;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Klasse, welche den Disclaimer beim ersten Start der App darstellt.
 * @author remi
 *
 */
public class Disclaimer extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.fragment_disclaimer,
	            container, false);
	    
	    FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.LogIn_Container, new BottomLogIn(), "BottomLogIn");
        transaction.addToBackStack(null);
        transaction.commit();
       
	    return view;
	}
	
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.disclaimer, menu);
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
		
		BottomLogIn bottomLogIn = (BottomLogIn) getActivity().getFragmentManager().findFragmentByTag("BottomLogIn");
		    
		    /**
		     * Set the Visibility of the previous button to invisible since there is no previous fragment
		     * 
		     * @author Eric Schmidt
		     */
	        
	       
	    bottomLogIn.getPrevious().setVisibility(View.INVISIBLE);
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
