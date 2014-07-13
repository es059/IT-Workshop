package de.hdm.mobile.health;

import de.hdm.mobile.health.bo.Meal;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Klasse welche Elemente der Listview "FoodList" des Fragments FoodLogFragment repräsentieren.
 * @author remi
 *
 */
public class ListViewLog extends RelativeLayout {

	 private TextView tvProduct;
	 private TextView tvAmount;
	 private TextView tvKcal;
	
	public ListViewLog(Context context) {
		super(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		View listViewFood = inflater.inflate(R.layout.foodlist_item, null);
		tvProduct = (TextView) listViewFood.findViewById(R.id.tvProduct);
		tvAmount = (TextView) listViewFood.findViewById(R.id.tvAmount);
		tvKcal = (TextView) listViewFood.findViewById(R.id.tvKcal);
 		addView(listViewFood);
	}
	
	public void setMeal(Meal meal) {
		tvProduct.setText(meal.getFood().getName());
		tvAmount.setText((int) meal.getAmount());
		tvKcal.setText((int) ((meal.getAmount() * meal.getFood().getCalories()) / 100) + " kcal");
	}

}
