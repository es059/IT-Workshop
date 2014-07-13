package de.hdm.mobile.health.adapter;

import java.util.List;

import de.hdm.mobile.health.ListViewFood;
import de.hdm.mobile.health.bo.Food;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
/**
 * Adapter für die Listview "FoodList" des Fragments FoodSearchFragments
 * @author remi
 *
 */
public class FoodListAdapter extends ArrayAdapter<Food> {
	public FoodListAdapter(Context context, int textViewResourceId, List<Food> objects) {
		super(context, textViewResourceId, objects);
	}
	
	@Override
	 public View getView(int position, View convertView, ViewGroup parent) {
		 Food food = getItem(position);
		 ListViewFood listViewFood = null;
		 if(convertView != null){
			 listViewFood = (ListViewFood) convertView;
		 }
		 else{
			 listViewFood = new ListViewFood(getContext());
		 }
		 
		 listViewFood.setFood(food);
		 return listViewFood;
	 }
}
