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
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Diese Klasse repräsentiert ein Dialog-Fragment welches aufgerufen wird wenn ein User eine Nahrung dem Tagebuch hinzufügen möchte. 
 * Der User kann mit Hilfe dieses Fragments ein Nahrungsmittel mit einer bestimmten Grammzahl seinem Tagebuch hinzufügen.
 * Dabei werden die Makronährstoffe anhand der angegebenen Grammzahl berechnet 
 * @author remi
 *
 */
@SuppressLint("ValidFragment")
public class FoodUpdateDialogFragment extends DialogFragment implements OnItemSelectedListener {
	
private  Context context;
private  Food food;
private int mt_Id;
private static  MealMapper mm;
private Spinner sMealtype;
private EditText etAmount;
private TextView tvKcalValue;
private TextView tvProteinValue;
private TextView tvCarbsValue;
private TextView tvFatValue;
private FoodLogFragment foodLogFragment;
private Meal meal;



	public static FoodUpdateDialogFragment newInstance(Context a, Meal m) {
		FoodUpdateDialogFragment exerciseAddDialogFragment = new FoodUpdateDialogFragment(a, m);
		mm = new MealMapper(a);
		return exerciseAddDialogFragment;

	}
	

	public FoodUpdateDialogFragment(Context a, Meal m) {
		super();
		this.context = a;
		this.meal = m;
		this.food = m.getFood();
		this.mt_Id = m.getMealtype().getId();
}
	
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialogfragment_food_add_to_log, null);

		alert.setTitle("Mahlzeit editieren");
		
		Spinner spinner = (Spinner) view.findViewById(R.id.spinner1);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
		        R.array.mealtyps, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
		spinner.setSelection(mt_Id);

		etAmount = (EditText) view.findViewById(R.id.etAmount);
		tvKcalValue = (TextView) view.findViewById(R.id.tvKcalValue);
		tvProteinValue = (TextView) view.findViewById(R.id.tvProteinValue);
		tvCarbsValue = (TextView) view.findViewById(R.id.tvCarbsValue);
		tvFatValue = (TextView) view.findViewById(R.id.tvFatValue);
		
		etAmount.setText(String.valueOf((int) meal.getAmount()));
		setValues();
		/**
		 * Bei der Eingabe in das Textfeld der Grammanzahl wird die Anzahl der Makronährstoffe berechnet und angezeigt. 
		 */
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
	        	 if(etAmount.getText().length() != 0){
	        	 Editable e =  etAmount.getText(); 
	        	
	        	 int amount = Integer.parseInt(e.toString());
	             tvKcalValue.setText(String.valueOf(Math.round((food.getCalories() * amount) / 100)));
	             tvProteinValue.setText(String.valueOf(Math.round((food.getProtein() * amount) / 100))+ " g");
	             tvCarbsValue.setText(String.valueOf(Math.round((food.getCarbs() * amount) / 100))+ " g");
	             tvFatValue.setText(String.valueOf(Math.round((food.getFat() * amount) / 100)) +" g");
	        	 }
	        	 else {
	        		 tvKcalValue.setText("0");
		             tvProteinValue.setText("0");
		             tvCarbsValue.setText("0");
		             tvFatValue.setText("0");
	        	 }
	          }
	  });
		
		foodLogFragment = (FoodLogFragment) getActivity().getFragmentManager().findFragmentByTag("FoodLogFragment");

		alert.setView(view);
		/**
		 * Beim Klick auf den Save-Button wird die Mahlzeit dem Ernährungstagebuch hinzugefügt und der User wird anschließend wieder auf sein Tagebuch geleitet. 
		 */
		alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			Meal m = new Meal();
			double amount = Double.parseDouble(etAmount.getText().toString());
			m.setiD(m.getiD());
			m.setAmount(amount);
			m.setFood(food);
			m.setDate(foodLogFragment.getCurrentDay());
			
			mm.addMeal(m, mt_Id);
			
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
	        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	        transaction.replace(R.id.fragment_container, new FoodLogFragment() , "FoodLogFragment");
	        transaction.addToBackStack(null);
	        transaction.commit();
		  }
		});

		alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
			  mm.deleteMeal(meal);
			  
			  FragmentTransaction transaction = getFragmentManager().beginTransaction();
		        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		        transaction.replace(R.id.fragment_container, new FoodLogFragment() , "FoodLogFragment");
		        transaction.addToBackStack(null);
		        transaction.commit();
		  }
		});

		return alert.show();
	}

/**
 * Legt fest welcher Mealtyp im Spinner ausgewählt wurde. 
 */
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
	mt_Id = arg2;		
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}
	
	public void setValues() {
		 if(etAmount.getText().length() != 0){
        	 int amount = (int) meal.getAmount();
             tvKcalValue.setText(String.valueOf(Math.round((food.getCalories() * amount) / 100)));
             tvProteinValue.setText(String.valueOf(Math.round((food.getProtein() * amount) / 100))+ " g");
             tvCarbsValue.setText(String.valueOf(Math.round((food.getCarbs() * amount) / 100))+ " g");
             tvFatValue.setText(String.valueOf(Math.round((food.getFat() * amount) / 100)) +" g");
        	 }
        	 else {
        		 tvKcalValue.setText("0");
	             tvProteinValue.setText("0");
	             tvCarbsValue.setText("0");
	             tvFatValue.setText("0");
        	 }
	}
}

