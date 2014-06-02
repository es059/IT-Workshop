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
import android.widget.Button;
import android.widget.EditText;
/**
 * This class represents the LogIn GUI. The User has the choice to
 * login via his Google Account or create a local user.
 * 
 * @author Eric Schmidt
 */
public class LogIn extends Fragment{
	
	private EditText mLastName ;
	private EditText mSurename;
	private EditText mHeight;
	private EditText mWeight;
	private EditText mAge;
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	    view = inflater.inflate(R.layout.fragment_login,container, false);
	    
		/**
		 * Reversing the invisibility done in the <code>Disclaimer</code> Fragment
		 *
	     * @author Eric Schmidt
	     */
		Button previous = (Button) getActivity().findViewById(R.id.bottom_previous);
		previous.setVisibility(View.VISIBLE);
	    
		/**
		 * Reference the EditText-Fields
		 */
		mSurename = (EditText) view.findViewById(R.id.Surname);
		mLastName = (EditText) view.findViewById(R.id.Lastname);
		mAge = (EditText) view.findViewById(R.id.Age);
		mHeight = (EditText) view.findViewById(R.id.Height);
		mWeight = (EditText) view.findViewById(R.id.Weight);
		
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
	 * Create a User-Object with the Information given from 
	 * the Textlabels
	 * 
	 * @author Eric Schmidt
	 */
	public User getUserInformation(){
		User n = new User();
		n.setSurename(mSurename.getText().toString());
		n.setLastName(mLastName.getText().toString());
		if (mWeight.getText().toString() == ""){
			n.setWeight(Double.parseDouble(mWeight.getText().toString()));
		}
		if (mHeight.getText().toString() == ""){
			n.setHeight(Double.parseDouble(mHeight.getText().toString()));
		}
		if (mAge.getText().toString() == ""){
			n.setAge(Double.parseDouble(mAge.getText().toString()));
		}
		return n;
	}
}
