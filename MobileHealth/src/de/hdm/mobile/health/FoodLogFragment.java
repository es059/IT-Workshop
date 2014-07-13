package de.hdm.mobile.health;



import java.util.ArrayList;
import java.util.Date;

import de.hdm.mobile.health.adapter.MealListAdapter;
import de.hdm.mobile.health.bo.DailyAim;
import de.hdm.mobile.health.bo.Meal;
import de.hdm.mobile.health.bo.Mealtype;
import de.hdm.mobile.health.db.DailyAimMapper;
import de.hdm.mobile.health.db.MealMapper;
import de.hdm.mobile.health.fragment.ActionBarDatePickerFragment;
import de.hdm.mobile.health.fragment.Disclaimer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

/**
 * Diese Klasse stellt das Ernährungstagebuch dar. Es besteht aus einer Tabelle welche SOll, Ist und Verbleibende Werte anzeigt 
 * und aus einer Liste welche alle Nahrungsmittel anzeigt die an diesem Tag konsumiert wurden.
 * @author remi
 *
 */
public class FoodLogFragment extends Fragment implements OnItemClickListener {

private TextView currentProtein;
private TextView currentCarbs;
private TextView currentFat;
private TextView currentKcal;
private TextView targetProtein;
private TextView targetCarbs;
private TextView targetFat;
private TextView targetKcal;
private TextView remainingProtein;
private TextView remainingCarbs;
private TextView remainingFat;
private TextView remainingKcal;
private ListView foodList;
private DailyAim dailyAim;
private MealMapper mealMapper; 
private Date currentDay;
private MealListAdapter adapter; 


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	//
        mealMapper = new MealMapper(getActivity());
		View rootView = inflater.inflate(R.layout.activity_food_log, container, false);
		/**
		 * Initisaliserung aller GUI-ELemente
		 */
		currentProtein = (TextView) rootView.findViewById(R.id.currenProtein);
		currentCarbs = (TextView) rootView.findViewById(R.id.currentCarbs);
		currentFat = (TextView) rootView.findViewById(R.id.currentFat);
		currentKcal = (TextView) rootView.findViewById(R.id.currentKcal);
		
		targetProtein = (TextView) rootView.findViewById(R.id.targetProtein);
		targetCarbs = (TextView) rootView.findViewById(R.id.targetCarbs);
		targetFat = (TextView) rootView.findViewById(R.id.targetFat);
		targetKcal = (TextView) rootView.findViewById(R.id.targetKcal);
		
		remainingProtein = (TextView) rootView.findViewById(R.id.remainingProtein);
		remainingCarbs = (TextView) rootView.findViewById(R.id.remainingCarbs);
		remainingFat = (TextView) rootView.findViewById(R.id.remainingFat);
		remainingKcal = (TextView) rootView.findViewById(R.id.remainingKcal);
		
		foodList = (ListView) rootView.findViewById(R.id.foodList);
		foodList.setOnItemClickListener(this);
		

		insertDailyAim();
		
		// get a List of all meals from the current day
		ArrayList<Meal> mealsAday = new ArrayList<Meal>();
		currentDay = new Date();
		mealsAday = mealMapper.getMealsAday(currentDay);
		
		
		insertListValues();
		
		
		double kcalSum = 0;
		double proteinSum = 0;
		double fatSum = 0;
		double carbsSum = 0;
		
		currentKcal.setText("0");
		currentProtein.setText("0");
		currentFat.setText("0");
		currentCarbs.setText("0");
		/**
		 * Vrtbleibende Werte werden berechnet.
		 */
		double remainingKcal = Math.round(dailyAim.getCalories() - kcalSum);
		double remainingProtein = Math.round(dailyAim.getProtein() - proteinSum);
		double remainingFat = Math.round(dailyAim.getFat() - fatSum);
		double remainingCarbs = Math.round(dailyAim.getCarbs() - carbsSum);
		/**
		 * Farbe der Schrift wird bestimmt. negativer Wert  = rote Schrift ; positiver Wert = grüne Schrift
		 */
		if(remainingKcal < 0) this.remainingKcal.setTextColor(getResources().getColor(R.color.red)); else this.remainingKcal.setTextColor(getResources().getColor(R.color.green));
		if(remainingProtein < 0) this.remainingProtein.setTextColor(getResources().getColor(R.color.red)); else this.remainingProtein.setTextColor(getResources().getColor(R.color.green));
		if(remainingFat < 0) this.remainingFat.setTextColor(getResources().getColor(R.color.red)); else this.remainingFat.setTextColor(getResources().getColor(R.color.green));
		if(remainingCarbs < 0) this.remainingCarbs.setTextColor(getResources().getColor(R.color.red)); else this.remainingCarbs.setTextColor(getResources().getColor(R.color.green));
		/**
		 * Werte werden den Labels zugewiesen
		 */
		this.remainingKcal.setText(String.valueOf(Math.round(remainingKcal)));
		this.remainingProtein.setText(String.valueOf(Math.round(remainingProtein)));
		this.remainingFat.setText(String.valueOf(Math.round(remainingFat)));
		this.remainingCarbs.setText(String.valueOf(Math.round(remainingCarbs)));
		
		
		
		
		// put the accumulated values into the TextViews
		if(!mealsAday.isEmpty()) 
		insertCurrentValues(mealsAday);
		
		/**
		 * Anzeige des Fragments zur Navigation der Tage
		 */
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.overview_dateTimePicker, new ActionBarDatePickerFragment(), "ActionBarDatePicker");
        transaction.addToBackStack(null);
        transaction.commit();
        
        setHasOptionsMenu(true);
		
		return rootView;
		
	}
	/**
	 * Methode welche das tägliche Nährwertziel aus der Datenbank list und in die Tabelle einträgt. 
	 */
	private void insertDailyAim() {
				// get DailyAim
				DailyAimMapper dailyAimMapper =  new DailyAimMapper(getActivity());
				dailyAim = dailyAimMapper.getDailyAim();
				
				// Set DailyAim to its TextViews
				//
				targetProtein.setText(String.valueOf(Math.round(dailyAim.getProtein())));
				targetCarbs.setText(String.valueOf(Math.round(dailyAim.getCarbs())));
				targetFat.setText(String.valueOf(Math.round(dailyAim.getFat())));
				targetKcal.setText(String.valueOf(Math.round(dailyAim.getCalories())));
		
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.food_log, menu);
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 
				switch(item.getItemId()){
				case R.id.menu_add:
					FragmentTransaction transaction = getFragmentManager().beginTransaction();
			        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			        transaction.replace(R.id.fragment_container, new FoodSearchFragment() , "FoodSearchFragment");
			        transaction.addToBackStack(null);
			        transaction.commit();
					
					return true; }
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Methode welche alle Ist Werte in die Tabelle einträgt und daraufhin die Werte in der Zeile "Verbleibend" berechnet und einträgt. 
	 * @param mealsAday eine ArrayList welche alle konsumierten Nahrungsmittel eines Tages enthält. 
	 */
	public void insertCurrentValues(ArrayList<Meal> mealsAday) {
		
		double kcalSum = 0;
		double proteinSum = 0;
		double fatSum = 0;
		double carbsSum = 0;
		if(!mealsAday.isEmpty()) {
		for(int i = 0; i < mealsAday.size(); i++) {
			kcalSum += (mealsAday.get(i).getAmount() * mealsAday.get(i).getFood().getCalories())/100;
			proteinSum += (mealsAday.get(i).getAmount() * mealsAday.get(i).getFood().getProtein())/100;
			fatSum += (mealsAday.get(i).getAmount() * mealsAday.get(i).getFood().getFat())/100;
			carbsSum += (mealsAday.get(i).getAmount() * mealsAday.get(i).getFood().getCarbs())/100;
		}
		}
		currentKcal.setText(String.valueOf(Math.round(kcalSum)));
		currentProtein.setText(String.valueOf(Math.round(proteinSum)));
		currentFat.setText(String.valueOf(Math.round(fatSum)));
		currentCarbs.setText(String.valueOf(Math.round(carbsSum)));
		
		double remainingKcal = Math.round(dailyAim.getCalories() - kcalSum);
		double remainingProtein = Math.round(dailyAim.getProtein() - proteinSum);
		double remainingFat = Math.round(dailyAim.getFat() - fatSum);
		double remainingCarbs = Math.round(dailyAim.getCarbs() - carbsSum);
		
		// Set the text color to red if the value is < 0; set 
		
		if(remainingKcal < 0) this.remainingKcal.setTextColor(getResources().getColor(R.color.red)); else this.remainingKcal.setTextColor(getResources().getColor(R.color.green));
		if(remainingProtein < 0) this.remainingProtein.setTextColor(getResources().getColor(R.color.red)); else this.remainingProtein.setTextColor(getResources().getColor(R.color.green));
		if(remainingFat < 0) this.remainingFat.setTextColor(getResources().getColor(R.color.red)); else this.remainingFat.setTextColor(getResources().getColor(R.color.green));
		if(remainingCarbs < 0) this.remainingCarbs.setTextColor(getResources().getColor(R.color.red)); else this.remainingCarbs.setTextColor(getResources().getColor(R.color.green));
		
		
		this.remainingKcal.setText(String.valueOf(Math.round(remainingKcal)));
		this.remainingProtein.setText(String.valueOf(Math.round(remainingProtein)));
		this.remainingFat.setText(String.valueOf(Math.round(remainingFat)));
		this.remainingCarbs.setText(String.valueOf(Math.round(remainingCarbs)));
		
	}
	/** 
	 * Methode welche die Liste mit den konsumierten Nahrungsmittel eines Tages befüllt. 
	 */
	public void insertListValues() {
		
		
		ArrayList<ListItem> mealList = new ArrayList<ListItem>();
		
		Mealtype m = new Mealtype();
		m.setName("Frühstück");
		m.setId(0);
		mealList.add(m);
		
		//Liste der heutigen Nahrungsmittel für Frühstück
		mealList.addAll(mealMapper.getMealsAday(currentDay, 0));
		
		m = new Mealtype();
		m.setName("Mittagessen");
		m.setId(1);
		mealList.add(m);
		
		//Liste der heutigen Nahrungsmittel für Mittagessen usw
		mealList.addAll(mealMapper.getMealsAday(currentDay, 1));
		
		m = new Mealtype();
		m.setName("Abendessen");
		m.setId(2);
		mealList.add(m);
		
		//Liste der heutigen Nahrungsmittel für Mittagessen usw
		mealList.addAll(mealMapper.getMealsAday(currentDay, 2));
		
		m = new Mealtype();
		m.setName("Snacks");
		m.setId(3);
		mealList.add(m);
		
		//Liste der heutigen Nahrungsmittel für Mittagessen usw
		mealList.addAll(mealMapper.getMealsAday(currentDay, 3));
		
		adapter = new MealListAdapter(getActivity(), mealList, getActivity());
		foodList.setAdapter(adapter);
	}
	public Date getCurrentDay() {
		return currentDay;
	}
	public void setCurrentDay(Date currentDay) {
		this.currentDay = currentDay;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Meal mealItem = (Meal) arg0.getItemAtPosition(arg2);
		
		DialogFragment dialogFragment = FoodUpdateDialogFragment.newInstance(getActivity(), mealItem);
		dialogFragment.show(this.getFragmentManager(), "Open Exercise Settings on Long Click");
		
		
		
		
	}
}