package de.hdm.mobile.health;



import java.util.ArrayList;
import java.util.Date;

import de.hdm.mobile.health.adapter.MealListAdapter;
import de.hdm.mobile.health.bo.DailyAim;
import de.hdm.mobile.health.bo.Meal;
import de.hdm.mobile.health.bo.Mealtype;
import de.hdm.mobile.health.db.DailyAimMapper;
import de.hdm.mobile.health.db.MealMapper;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class FoodLogFragment extends Fragment {

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
private MealMapper mealMapper = new MealMapper(getActivity());
private Date currentDay;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_food_log, container, false);
		
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
		

		// get DailyAim
		DailyAimMapper dailyAimMapper =  new DailyAimMapper(getActivity());
		dailyAim = dailyAimMapper.getDailyAim();
		
		// Set DailyAim to its TextViews
		
		targetProtein.setText((int)dailyAim.getProtein());
		targetCarbs.setText((int) dailyAim.getCarbs());
		targetFat.setText((int) dailyAim.getFat());
		targetKcal.setText((int) dailyAim.getCalories());
		
		// get a List of all meals from the current day
		// TODO anpassung des Dateformates
		ArrayList<Meal> mealsAday = new ArrayList<Meal>();
		currentDay = new Date();
		mealsAday = mealMapper.getMealsAday(currentDay);
		ArrayList<ListItem> mealList = new ArrayList<>();
		
		Mealtype m = new Mealtype();
		m.setName("Frühstück");
		mealList.add(m);
		
		//Liste der heutigen Nahrungsmittel für Frühstück
		mealList.addAll(mealMapper.getMealsAday(currentDay, 0));
		
		m.setName("Mittagessen");
		mealList.add(m);
		
		//Liste der heutigen Nahrungsmittel für Mittagessen usw
		mealList.addAll(mealMapper.getMealsAday(currentDay, 1));
		
		m.setName("Abendessen");
		mealList.add(m);
		
		//Liste der heutigen Nahrungsmittel für Mittagessen usw
		mealList.addAll(mealMapper.getMealsAday(currentDay, 2));
		
		m.setName("Snacks");
		mealList.add(m);
		
		//Liste der heutigen Nahrungsmittel für Mittagessen usw
		mealList.addAll(mealMapper.getMealsAday(currentDay, 3));
		
		
		
		MealListAdapter adapter = new MealListAdapter(getActivity(), mealList);
		foodList.setAdapter(adapter);
		
		
		// put the accumulated values into the TextViews
		if(!mealsAday.isEmpty()) 
		insertCurrentValues(mealsAday);
		
		
		return rootView;
		
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	public void insertCurrentValues(ArrayList<Meal> mealsAday) {
		double kcalSum = 0;
		double proteinSum = 0;
		double fatSum = 0;
		double carbsSum = 0;
	
		for(int i = 0; i < mealsAday.size(); i++) {
			kcalSum += (mealsAday.get(i).getAmount() * mealsAday.get(i).getFood().getCalories())/100;
			proteinSum += (mealsAday.get(i).getAmount() * mealsAday.get(i).getFood().getProtein())/100;
			fatSum += (mealsAday.get(i).getAmount() * mealsAday.get(i).getFood().getFat())/100;
			carbsSum += (mealsAday.get(i).getAmount() * mealsAday.get(i).getFood().getCarbs())/100;
		}
		
		currentKcal.setText((int)kcalSum);
		currentProtein.setText((int)proteinSum);
		currentFat.setText((int)fatSum);
		currentCarbs.setText((int) carbsSum);
		
		double remainingKcal = dailyAim.getCalories() - kcalSum;
		double remainingProtein = dailyAim.getProtein() - proteinSum;
		double remainingFat = dailyAim.getFat() - fatSum;
		double remainingCarbs = dailyAim.getCarbs() - carbsSum;
		
		// Set the text color to red if the value is < 0; set 
		
		if(remainingKcal < 0) this.remainingKcal.setTextColor(getResources().getColor(R.color.red)); else this.remainingKcal.setTextColor(getResources().getColor(R.color.green));
		if(remainingProtein < 0) this.remainingProtein.setTextColor(getResources().getColor(R.color.red)); else this.remainingProtein.setTextColor(getResources().getColor(R.color.green));
		if(remainingFat < 0) this.remainingFat.setTextColor(getResources().getColor(R.color.red)); else this.remainingFat.setTextColor(getResources().getColor(R.color.green));
		if(remainingCarbs < 0) this.remainingCarbs.setTextColor(getResources().getColor(R.color.red)); else this.remainingCarbs.setTextColor(getResources().getColor(R.color.green));
		
		
		this.remainingKcal.setText((int) remainingKcal);
		this.remainingProtein.setText((int) remainingProtein);
		this.remainingFat.setText((int) remainingFat);
		this.remainingCarbs.setText((int) remainingCarbs);
		
	}
}