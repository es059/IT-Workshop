package de.hdm.mobile.health.fragment;

import de.hdm.mobile.health.R;
import de.hdm.mobile.health.bo.User;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

public class LogInAdvanced extends Fragment {
	
	private RadioButton mMale, mFemale;
	private Spinner mActivitylevel;
	
	
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
	    
	    mActivitylevel = (Spinner) view.findViewById(R.id.ActivityLevel);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
		        R.array.activity_level, android.R.layout.simple_spinner_item);
		mActivitylevel.setAdapter(adapter);
		/**
		 * Reference the other View-Fields
		 */
		mMale = (RadioButton) view.findViewById(R.id.Gender_male);
		mFemale = (RadioButton) view.findViewById(R.id.Gender_female);
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
	
	/**
	 * Use the User-Object from the LogIn-Fragment to complete it with
	 * the Information given in this fragment.
	 * 
	 * @return User the finished User-Object
	 * @param n the current User-Object
	 * @author Eric Schmidt 
	 */
	public User getUserInformation(User n){
		/**
		 * Determines if the User is male (Value 1 in Database)
		 * or female (Value 2 in Database)
		 */
		if (mMale.isChecked()){
			n.setGender(1);
		}else if (mFemale.isChecked()){
			n.setGender(2);
		}
		/**
		 * Determines which activity level was chosen.
		 * 
		 * Gering = 1
		 * Normal = 2
		 * Hoch = 3
		 * Sehr Hoch = 4
		 */
		switch(mActivitylevel.getSelectedItem().toString()){
			case "Gering":
				n.setActivitylevel(1);
				break;
			case "Normal":
				n.setActivitylevel(2);
				break;
			case "Hoch":
				n.setActivitylevel(3);
				break;
			case "Sehr Hoch":
				n.setActivitylevel(4);
				break;
		}
		
		return n;
	}
}
