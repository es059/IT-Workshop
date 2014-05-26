package de.hdm.mobile.health;

import java.util.ArrayList;

import de.hdm.mobile.health.adapter.FoodListAdapter;
import de.hdm.mobile.health.bo.Food;
import de.hdm.mobile.health.db.FoodMapper;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Build;

public class FoodSearch extends ActionBarActivity {
	
	private EditText search;
	private ListView foodListView; 
	private FoodListAdapter foodAdapter;
	private ArrayList<Food> foodList;
	private FoodMapper foodMapper;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_search);
		foodList  = new ArrayList<Food>();
		foodList.add( new Food("test"));
		Food f1 = new Food("brot");
		foodList.add(f1);
		search = (EditText) findViewById(R.id.foodSearch_subject);
		foodListView = (ListView) findViewById(R.id.add_foodList);
		foodAdapter = new FoodListAdapter(this, R.layout.listview_food, foodList);
		
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
	        	  foodList  =   foodMapper.searchKeyString(String.valueOf(s));
	        	  foodAdapter.addAll(foodList);
	        	  foodAdapter.notifyDataSetChanged();
	        	  foodListView.invalidateViews();
	        	  System.out.println(s);
	          }
	  });

		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.food_search, menu);
		return true;
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
