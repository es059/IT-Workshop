package de.hdm.mobile.health.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.hdm.mobile.health.ListItem;
import de.hdm.mobile.health.ListViewFood;
import de.hdm.mobile.health.ListViewLog;
import de.hdm.mobile.health.R;
import de.hdm.mobile.health.bo.Food;
import de.hdm.mobile.health.bo.Meal;
import de.hdm.mobile.health.bo.Mealtype;

public class MealListAdapter extends ArrayAdapter<ListItem> {
	
	private Context context;
	private ArrayList<ListItem> items;
	private LayoutInflater vi;

	public MealListAdapter(Context context, ArrayList<ListItem> objects) {
		super(context,0, objects);
		this.context = context;
		this.items = objects;
		vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	 public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		final ListItem i = items.get(position);
		if (i != null){
			if (i.isSection()){
				Mealtype mealtype = (Mealtype) i;
				v = vi.inflate(R.layout.listview_header, null);
				v.setOnClickListener(null);
				v.setOnLongClickListener(null);
				v.setLongClickable(false);

				final TextView sectionView = (TextView) v.findViewById(R.id.listview_header);
				sectionView.setText(mealtype.getName());
			}else{
				Meal meal = (Meal) i;
				v = vi.inflate(R.layout.foodlist_item, null);
				final TextView 	tvProduct = (TextView) v.findViewById(R.id.tvProduct);
				final TextView 	tvAmount = (TextView) v.findViewById(R.id.tvAmount);
				final TextView 	tvKcal = (TextView) v.findViewById(R.id.tvKcal);

				tvProduct.setText(meal.getFood().getName());
				tvAmount.setText(String.valueOf(meal.getAmount()));
				tvKcal.setText(String.valueOf((meal.getAmount() * meal.getFood().getCalories()) / 100));
				
			}
		}
		return v;
	}
	 
}