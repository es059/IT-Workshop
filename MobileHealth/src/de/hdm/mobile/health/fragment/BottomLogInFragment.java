package de.hdm.mobile.health.fragment;

import de.hdm.mobile.health.Disclaimer;
import de.hdm.mobile.health.LogIn;
import de.hdm.mobile.health.LoginAdvanced;
import de.hdm.mobile.health.R;
import de.hdm.mobile.health.AddFood;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
/**
 * This class handles the behavior of the 'Next Arrow' Button 
 * 
 * @author Eric Schmidt
 */
public class BottomLogInFragment extends Fragment implements OnClickListener{
	
	private Activity currentActivity = null;
	private Button next = null;
	private Button previous = null;
	
	/**
	 * Sets the Layout of the Fragment and references the direction Buttons to 
	 * the associated class variables and sets the ClickListener for them.
	 * Sets the Visibility of the previous Button according to the currentActivity.
	 * 
	 * @author Eric Schmidt
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(de.hdm.mobile.health.R.layout.bottom_login_fragment,
				container,false);
		
		next = (Button) view.findViewById(R.id.bottom_next);
		previous = (Button) view.findViewById(R.id.bottom_previous);
		
		if(currentActivity instanceof Disclaimer){
			previous.setVisibility(View.INVISIBLE);
		} else{
			previous.setVisibility(View.VISIBLE);
		}
		
		previous.setOnClickListener(this);
		next.setOnClickListener(this);
		return view;
		
	}
	
	/**
	 * Set the Text of the next Button according to the
	 * current Activity
	 * 
	 * @author Eric Schmidt
	 */
	@Override
	public void onResume(){
		super.onResume();

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
		Intent intent = new Intent();
		Bundle bundleanimation = null;
		bundleanimation = ActivityOptions.makeCustomAnimation(getActivity(), 
				R.anim.slide_in_left,R.anim.slide_over_left).toBundle();	
		switch(v.getId()){
			case R.id.bottom_next:
				if(currentActivity instanceof Disclaimer){
					intent.setClass(getActivity(), LogIn.class);
					
				} else if (currentActivity instanceof LogIn){
					intent.setClass(getActivity(), LoginAdvanced.class);
				} else{
					intent.setClass(getActivity(), AddFood.class);
					Toast.makeText(getActivity(), "Account wurde angelegt", Toast.LENGTH_SHORT).show();
				}
				startActivity(intent,bundleanimation);
				break;
			case R.id.bottom_previous:
				if(currentActivity instanceof Disclaimer){
					
				} else if (currentActivity instanceof LogIn){
					intent.setClass(getActivity(), Disclaimer.class);
				} else{
					intent.setClass(getActivity(), LogIn.class);
				}
				startActivity(intent,bundleanimation);
				break;
		}
	
		
	}
}
