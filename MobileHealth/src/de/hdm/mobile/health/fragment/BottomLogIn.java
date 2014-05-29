package de.hdm.mobile.health.fragment;

import de.hdm.mobile.health.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.Toast;
/**
 * This class handles the behavior of the 'Next Arrow' Button 
 * 
 * @author Eric Schmidt
 */
public class BottomLogIn extends Fragment implements OnClickListener{
	
	private Activity currentActivity = null;
	private Button next = null;
	private Button previous = null;
	
	/**
	 * Sets the Layout of the Fragment and references the direction Buttons to 
	 * the associated class variables and sets the ClickListener for them.
	 * 
	 * @author Eric Schmidt
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(de.hdm.mobile.health.R.layout.fragment_bottom_login,
				container,false);
		
		next = (Button) view.findViewById(R.id.bottom_next);
		previous = (Button) view.findViewById(R.id.bottom_previous);
		
		previous.setOnClickListener(this);
		next.setOnClickListener(this);
		return view;
		
	}

	/**
	 * Overrides the Method onAttach of the class Fragemnt and
	 * sets the reference of the class Variable currentActivity to
	 * the current Activity.
	 * 
	 * @author Eric Schmidt
	 */
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		currentActivity = activity;
	}
	
	/**
	 * Implements the Method onClick of the Interface 
	 * onClickListener. Implements the behavior of the 
	 * direction buttons according to the current Activity.
	 * 
	 * @param View the View which triggered the Event
	 * @author Eric Schmidt
	 */
	@Override
	public void onClick(View v) {
	    FragmentTransaction transaction = currentActivity.getFragmentManager().beginTransaction();
        	
		switch(v.getId()){
			case R.id.bottom_next:
				if( currentActivity.getFragmentManager().findFragmentByTag("Disclaimer").isVisible()){
					transaction.replace(R.id.fragment_container, new LogIn(), "LogIn");
				} else if (currentActivity.getFragmentManager().findFragmentByTag("LogIn").isVisible()){
					transaction.replace(R.id.fragment_container, new LoginAdvanced(), "LogInAdvanced");
				} else{
					transaction.replace(R.id.fragment_container, new AddFood(), "AddFood");
					/**
					 * Hide the Bottom Fragment and inform the user that a new Account was created
					 */
					transaction.remove(currentActivity.getFragmentManager().findFragmentById(R.id.LogIn_Bottom));
					Toast.makeText(getActivity(), "Account wurde angelegt", Toast.LENGTH_SHORT).show();
				}	
				break;
			case R.id.bottom_previous:
				if(currentActivity.getFragmentManager().findFragmentByTag("Disclaimer").isVisible()){
					
				} else if (currentActivity.getFragmentManager().findFragmentByTag("LogIn").isVisible()){
					transaction.replace(R.id.fragment_container, new Disclaimer(), "Disclaimer");
				} else{
					transaction.replace(R.id.fragment_container, new LogIn(), "LogIn");
				}
				break;
		}
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
        transaction.commit();
	
		
	}
}
