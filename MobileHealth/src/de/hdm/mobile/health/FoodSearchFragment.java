package de.hdm.mobile.health;

import java.util.ArrayList;








import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import de.hdm.mobile.health.adapter.FoodListAdapter;
import de.hdm.mobile.health.bo.Food;
import de.hdm.mobile.health.bo.Mealtype;
import de.hdm.mobile.health.db.FoodMapper;
import de.hdm.mobile.health.fragment.ActionBarDatePickerFragment;
import de.hdm.mobile.health.fragment.ActionBarSearchBarFragment;
import de.hdm.mobile.health.fragment.AddFood;


/**
 * Mit Hilfe dieser Klasse kann der User nach Nahrungsmittel suchen und seinem Tagebuch Nahrungsmittel hinzufügen. 
 * @author remi
 *
 */
public class FoodSearchFragment extends Fragment implements OnItemClickListener {
	private ListView foodListView; 
	private FoodListAdapter foodAdapter;
	private ArrayList<Food> foodList;
	private FoodMapper foodMapper;
	private Mealtype mealtype;
	private int mealtype_Id;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.activity_food_search, container, false);
		foodMapper = new FoodMapper(getActivity());
		foodList  = new ArrayList<Food>();
		foodList = foodMapper.getAll();
		foodListView = (ListView) rootView.findViewById(R.id.add_foodList);
		// set ListAdapter
		setListAdapter(foodList);
		foodListView.setOnItemClickListener(this);
		this.setHasOptionsMenu(true);
	
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.add_searchBar, new ActionBarSearchBarFragment(), "Disclaimer");
        transaction.addToBackStack(null);
        transaction.commit();
		
		return rootView;
	}
	
	public void setListAdapter(ArrayList<Food> list) {
		
		foodAdapter = new FoodListAdapter(getActivity(), R.layout.listview_food, list);
		foodListView.setAdapter(foodAdapter);
		
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.food_search, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
/**
 * Über das +-Item im Menü kann ein neues Nahrungsmittel hinzugefügt werden. Dafür öffnet sich ein neues Fragment. 
 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.add:
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
	        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	        transaction.replace(R.id.fragment_container, new AddFood() , "AddFood");
	        transaction.addToBackStack(null);
	        transaction.commit();
			
			return true; }
		return super.onOptionsItemSelected(item);
	}
	/**
	 * Beim Klick auf ein Nahrungsmittel öffnet sich das DialogFragment zum Hinzufügen des Nahrungsmittel dem Tagebuch.
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Food food = new Food();
		food = (Food) foodListView.getItemAtPosition(arg2);
		DialogFragment dialogFragment = FoodClickDialogFragment.newInstance(getActivity(), food, mealtype_Id);
		dialogFragment.show(this.getFragmentManager(), "Open Exercise Settings on Long Click");
		
	}
	
	public void setMealtype(Mealtype mealtype) {
		this.mealtype = mealtype;
	}
}
