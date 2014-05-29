package de.hdm.mobile.health;

import java.text.SimpleDateFormat;
import java.util.Date;
import de.hdm.mobile.health.bo.Food;
import de.hdm.mobile.health.bo.Meal;
import de.hdm.mobile.health.bo.Mealtype;
import de.hdm.mobile.health.db.MealMapper;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class FoodClickDialogFragment extends DialogFragment {
	
private  Context context;
private  Food food;
private Mealtype mt;
private static  MealMapper mm;
private EditText etAmount;
private TextView tvKcalValue;
private TextView tvProteinValue;
private TextView tvCarbsValue;
private TextView tvFat;


	public static FoodClickDialogFragment newInstance(Context a, Food f, Mealtype mt) {
		FoodClickDialogFragment exerciseAddDialogFragment = new FoodClickDialogFragment(a, f, mt);
		
		mm = new MealMapper(a);
		return exerciseAddDialogFragment;

	}
	

	public FoodClickDialogFragment(Context a, Food f, Mealtype mt) {
		super();
		this.context = a;
		this.food = f;
		this.mt = mt;
}
	
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialogfragment_food_add_to_log, null);

		alert.setTitle("Mahlzeit hinzufügen");

		etAmount = (EditText) view.findViewById(R.id.etAmount);
		tvKcalValue = (TextView) view.findViewById(R.id.tvKcalValue);
		tvProteinValue = (TextView) view.findViewById(R.id.tvProteinValue);
		tvCarbsValue = (TextView) view.findViewById(R.id.tvCarbsValue);
		tvFat = (TextView) view.findViewById(R.id.tvFat);
		
		etAmount.addTextChangedListener(new TextWatcher() 
		  {
	          
	          public void beforeTextChanged(CharSequence s, int start, int count, int after)
	          {
	                    
	          }
	 
	          public void onTextChanged(CharSequence s, int start, int before, int count)
	          {
	        	 
	          }

	          public void afterTextChanged(Editable s)
	          {
	        	 Editable e =  etAmount.getText();
	        	 int amount = Integer.parseInt(e.toString());
	             tvKcalValue.setText((int)(food.getCalories() * amount) / 100);
	             tvProteinValue.setText((int)(food.getProtein() * amount) / 100);
	             tvCarbsValue.setText((int) (food.getCarbs() * amount) / 100);
	             tvFat.setText((int)(food.getFat() * amount) / 100);
		
	          }
	  });
		
		

		alert.setView(view);

		alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			Meal m = new Meal();
			double amount = Double.parseDouble(etAmount.getText().toString());
			m.setAmount(amount);
			m.setFood(food);
			m.setDate(new Date());
			m.setMealtype(mt);
			mm.addMeal(m);
		  }
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		return alert.show();
	}
}

