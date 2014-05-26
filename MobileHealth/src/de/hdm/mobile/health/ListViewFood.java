package de.hdm.mobile.health;

import de.hdm.mobile.health.bo.Food;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListViewFood extends RelativeLayout
{
	 private TextView foodView;
	 
	 public ListViewFood(Context context) {
			super(context);
			LayoutInflater inflater = LayoutInflater.from(context);
			View listViewFood = inflater.inflate(R.layout.listview_food, null);
			foodView = (TextView) listViewFood.findViewById(R.id.food);
			addView(listViewFood);
		} 

	public void setFood(Food food) {
	
		this.foodView.setText(food.getName());		
	}

}
