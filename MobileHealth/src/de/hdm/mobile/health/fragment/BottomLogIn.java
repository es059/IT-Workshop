package de.hdm.mobile.health.fragment;

import de.hdm.mobile.health.FoodLogFragment;
import de.hdm.mobile.health.R;
import de.hdm.mobile.health.bo.DailyAim;
import de.hdm.mobile.health.bo.User;
import de.hdm.mobile.health.db.DailyAimMapper;
import de.hdm.mobile.health.db.UserMapper;
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
	public Button getNext() {
		return next;
	}

	public Button getPrevious() {
		return previous;
	}

	private Button previous = null;
	private static User n = null;
	private DailyAimMapper daMapper;
	/**
	 * Initialize the Fragments
	 */
	LogIn l = new LogIn();
	LogInAdvanced la = new LogInAdvanced();
	Disclaimer d = new Disclaimer();
	
	
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
		
		daMapper = new DailyAimMapper(getActivity());
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
					transaction.replace(R.id.fragment_container, l, "LogIn");
				} else if (currentActivity.getFragmentManager().findFragmentByTag("LogIn").isVisible()){
					n = ((LogIn) currentActivity.getFragmentManager().findFragmentByTag("LogIn")).getUserInformation();
					transaction.replace(R.id.fragment_container, la, "LogInAdvanced");
				} else{
					n = ((LogInAdvanced)currentActivity.getFragmentManager().findFragmentByTag("LogInAdvanced")).getUserInformation(n);
					UserMapper uMapper = new UserMapper(getActivity());
					uMapper.add(n);
					
					// Festlegung des täglichen Ziels:
					
					User user = uMapper.getUser();
					double kcal = 0;
					if(daMapper.countAims() == 0) {
						if(user.getGender() == 1) {
						kcal = (66 + (13.7 * user.getWeight()) + (5*user.getHeight()) - (6.8 * user.getAge())); }
						else {
						kcal = (655 + (9.6 * user.getWeight()) + (1.8*user.getHeight()) - (4.7 * user.getAge())); }
						
					kcal = kcal * user.getActivitylevel();
					kcal = Math.round(kcal);}
					
					 double carbs = (kcal *  50/100) / 4.1;
			          carbs = Math.round(carbs *100);
			          carbs = carbs / 100;
					
					 double protein = (kcal * (30 /100) / 4.1);
			          protein = Math.round(protein *100);
			          protein = protein / 100;
			          
			          double fat = (kcal *  20/100) / 9.3;
			          fat = Math.round(fat *100);
			          fat = fat / 100;
			          
			          DailyAim da = new DailyAim();
						da.setCalories(kcal);
						da.setCarbs(carbs);
						da.setProtein(protein);
						da.setFat(fat);						
						daMapper.setDailyAim(da);
			          
			          
			
					transaction.replace(R.id.fragment_container, new FoodLogFragment(), "FoodLogFragment");
					Toast.makeText(getActivity(), "Account wurde angelegt: Willkommen " + n.getSurename() + " " + n.getLastName()
							, Toast.LENGTH_SHORT).show();
					/**
					 * Hide the Bottom Fragment and inform the user that a new Account was created
					 */
				}	
				break;
			case R.id.bottom_previous:
				if(currentActivity.getFragmentManager().findFragmentByTag("Disclaimer").isVisible()){
					
				} else if (currentActivity.getFragmentManager().findFragmentByTag("LogIn").isVisible()){
					transaction.replace(R.id.fragment_container, d, "Disclaimer");
				} else{

					transaction.replace(R.id.fragment_container, l, "LogIn");
				}
				break;
		}
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
        transaction.commit();
	
		
	}
}
