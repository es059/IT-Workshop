package de.hdm.mobile.health.fragment;

import de.hdm.mobile.health.R;
import de.hdm.mobile.health.R.id;
import de.hdm.mobile.health.R.layout;
import de.hdm.mobile.health.R.menu;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
/**
 * This class represents the LogIn GUI. The User has the choice to
 * login via his Google Account or create a local user.
 * 
 * @author Eric Schmidt
 */
public class LogIn extends Fragment{

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	    View view = inflater.inflate(R.layout.fragment_login,container, false);
	    
		/**
		 * Reversing the invisibility done in the <code>Disclaimer</code> Fragment
		 *
	     * @author Eric Schmidt
	     */
		Button previous = (Button) getActivity().findViewById(R.id.bottom_previous);
		previous.setVisibility(View.VISIBLE);
	    
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

}
