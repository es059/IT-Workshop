package de.hdm.mobile.health;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

public class FoodSearchFragment extends Fragment implements OnItemClickListener {
	
	private EditText search;
	private ListView foodListView; 
	private FoodListAdapter foodAdapter;
	private ArrayList<Food> foodList;
	private FoodMapper foodMapper;
	private Mealtype mealtype;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_food_search, container, false);
		foodList  = new ArrayList<Food>();
		foodList = foodMapper.getAll();
		search = (EditText) rootView.findViewById(R.id.foodSearch_subject);
		foodListView = (ListView) rootView.findViewById(R.id.add_foodList);
		foodAdapter = new FoodListAdapter(getActivity(), R.layout.listview_food, foodList);
		foodListView.setAdapter(foodAdapter);
		
		search.addTextChangedListener(new TextWatcher() 
		  {
	          
	          public void beforeTextChanged(CharSequence s, int start, int count, int after)
	          {
	                    
	          }
	 
	          public void onTextChanged(CharSequence s, int start, int before, int count)
	          {
	        	 
	          }

	          public void afterTextChanged(Editable s)
	          {
	              
	        	  foodAdapter.clear();
	        	  foodList = foodMapper.searchKeyString(String.valueOf(s));
	        	  foodAdapter.addAll(foodList);
	        	  foodAdapter.notifyDataSetChanged();
	        	  foodListView.invalidateViews();
	        	  System.out.println(s);
	          }
	  });
		
		return rootView;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Food food = new Food();
		food = (Food) foodListView.getItemAtPosition(arg2);
		DialogFragment dialogFragment = FoodClickDialogFragment.newInstance(getActivity(), food, mealtype);
		dialogFragment.show(this.getFragmentManager(), "Open Exercise Settings on Long Click");
		
	}
	
	public void setMealtype(Mealtype mealtype) {
		this.mealtype = mealtype;
	}
}
