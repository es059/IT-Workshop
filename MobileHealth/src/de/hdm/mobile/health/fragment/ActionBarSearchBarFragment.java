package de.hdm.mobile.health.fragment;


import java.util.ArrayList;

import de.hdm.mobile.health.FoodSearchFragment;
import de.hdm.mobile.health.R;
import de.hdm.mobile.health.bo.Food;
import de.hdm.mobile.health.db.FoodMapper;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
/**
 * Fragment welche eine Suchleiste realisiert. Sie dient dazu nach einem Nahrungsmittel im FoodSearchFragment zu suchen. 
 * @author remi
 *
 */
public class ActionBarSearchBarFragment  extends Fragment{
	private ImageButton searchButton;
	private EditText searchBar;
	private FoodSearchFragment foodSearchFragment;
	private FoodMapper foodMapper;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.actionbar_searchbar_fragment, container,false);
		foodMapper = new FoodMapper(getActivity());
		foodSearchFragment = (FoodSearchFragment) getActivity().getFragmentManager().findFragmentByTag("FoodSearchFragment");
		searchBar = (EditText) view.findViewById(R.id.foodSearch_subject);
		/**
		 * Bei einer Eingabe im Textfeld wird nach Nahrungsmittel mit der eingegebenen Zeichenkette gesucht und der Liste hinzugefügt. 
		 */
		searchBar.addTextChangedListener(new TextWatcher() 
		  {
	          
	          public void beforeTextChanged(CharSequence s, int start, int count, int after)
	          {
	                    
	          }
	 
	          public void onTextChanged(CharSequence s, int start, int before, int count)
	          {
	        	 
	          }

	          public void afterTextChanged(Editable s)
	          {
	        	 ArrayList<Food> foodList  = new ArrayList<Food>();
	        	  foodList = foodMapper.searchKeyString(String.valueOf(s));
	        	  foodSearchFragment.setListAdapter(foodList);
	        	
	        	
	          }
	  }); 
		return view;
		
	}
}
