package de.hdm.mobile.health;

import de.hdm.mobile.health.adapter.CustomDrawerAdapter;
import de.hdm.mobile.health.db.UserMapper;
import de.hdm.mobile.health.fragment.AddFood;
import de.hdm.mobile.health.fragment.Disclaimer;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class HelperActivity extends Activity  {
	
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerListe;
    private ActionBarDrawerToggle mDrawerToggle;
    private MenueListe l = new MenueListe();
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private CustomDrawerAdapter adapter;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_helper);
		
		// Initializing

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerListe= (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);
        

		// Add Drawer Item to dataList
       

        adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item, l.getDataList());
        mDrawerListe.setAdapter(adapter);
        mDrawerListe.setOnItemClickListener(new DrawerItemClickListener());
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    R.drawable.ic_drawer, R.string.drawer_open,
                    R.string.drawer_close) {
              public void onDrawerClosed(View view) {
                    getActionBar().setTitle(mTitle);
                    invalidateOptionsMenu(); // creates call to
                                                              // onPrepareOptionsMenu()
              }

              public void onDrawerOpened(View drawerView) {
                    getActionBar().setTitle(mDrawerTitle);
                    invalidateOptionsMenu(); // creates call to
                                                              // onPrepareOptionsMenu()
              }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle); 
		
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
	

	/**
	 * Methods to handle the Navigation Drawer
	 * 
	 *
	 */
	public void SelectItem(int possition) { 
		switch(possition) {
			case 0:
	
				break;
			case 1: 
			
				break;
			case 2: 
			
				break;
			case 3: 
			
				break;
			case 4: 
			
				break;
		}
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	      super.onConfigurationChanged(newConfig);
	      // Pass any configuration change to the drawer toggles
	      mDrawerToggle.onConfigurationChanged(newConfig);
	}
	private class DrawerItemClickListener implements
    ListView.OnItemClickListener {
			
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
		          long id) {
		    SelectItem(position);
		
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    if (mDrawerToggle.onOptionsItemSelected(item)) {
	    	return true;
	    }
		return super.onOptionsItemSelected(item);
	}
	
}
