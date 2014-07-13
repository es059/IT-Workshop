package de.hdm.mobile.health;

import java.text.DecimalFormat;

import de.hdm.mobile.health.bo.DailyAim;
import de.hdm.mobile.health.bo.User;
import de.hdm.mobile.health.db.DailyAimMapper;
import de.hdm.mobile.health.db.UserMapper;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Diese Klasse stellt die GUI zu Verwaltung des täglichen Nährstoffziels dar. 
 * @author remi
 *
 */
public class AimConfigFragment extends Fragment {
	
private TextView tv_carbs_per;
private TextView tv_protein_per;
private TextView tv_fat_per;

private TextView tv_per_sum;
private TextView tv_Hinweiss;

private EditText et_carbs_per;
private EditText et_protein_per;
private EditText et_fat_per;

private EditText et_calories_target;
private Button button;
private DailyAimMapper daMapper;

private MenuItem save;

private User user;
private UserMapper uMapper;



	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.aim_config, menu);
		save = menu.getItem(0);
		/**
		 * Es wird überprüft ob der User die Daten speichern kann. Dies ist erst möglich wenn ein Kaloreienwert angegeben wird und das die Summe der prozentualen Nährstoffverteilung = 100 ist. 
		 */
		 if(et_calories_target.getText().toString().length() != 0 && (Integer.parseInt(et_carbs_per.getText().toString()) + Integer.parseInt(et_protein_per.getText().toString()) + Integer.parseInt(et_fat_per.getText().toString())) == 100) {
    		 save.setVisible(true);
    	 }
		 else {
			 save.setVisible(false);
		 }
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_aimconfig, container, false);
		setHasOptionsMenu(true);
		daMapper = new DailyAimMapper(getActivity());
		/**
		 * initialisierung der GUI Elemente
		 */
		tv_carbs_per = (TextView) rootView.findViewById(R.id.tv_carbs_per);
		tv_protein_per = (TextView) rootView.findViewById(R.id.tv_protein_per);
		tv_fat_per = (TextView) rootView.findViewById(R.id.tv_fat_per);
		
		tv_per_sum = (TextView) rootView.findViewById(R.id.tv_per_sum);
		tv_Hinweiss = (TextView) rootView.findViewById(R.id.tv_Hinweiss);
		
		et_carbs_per = (EditText) rootView.findViewById(R.id.et_carbs_per);
		et_protein_per = (EditText) rootView.findViewById(R.id.et_protein_per);
		
		et_fat_per = (EditText) rootView.findViewById(R.id.et_fat_per);
		et_calories_target = (EditText) rootView.findViewById(R.id.et_calories_target);
		
		uMapper = new UserMapper(getActivity());
		user = uMapper.getUser();
		DailyAim da = daMapper.getDailyAim();
		
		double kcal = 0;
		kcal = da.getCalories();
		kcal = Math.round(kcal);
		
		
		et_calories_target.setText(String.valueOf(kcal));
		et_carbs_per.setText("50");
		et_fat_per.setText("20");
		et_protein_per.setText("30");
		
		/**
		 * Berechnung des Anteil an Kohlenhydraten anhand der Gesamtkalorienanzahl und der prozentualen Nährstoffverteilung
		 */
        if(et_carbs_per.getText().toString().length() != 0 && et_calories_target.getText().toString().length() != 0) {
	        double target_calories =  Double.parseDouble(et_calories_target.getText().toString());
	        double carbs = (target_calories *  Integer.parseInt(et_carbs_per.getText().toString())/100) / 4.1;
	        carbs = Math.round(carbs *100);
	        carbs = carbs / 100;
	        tv_carbs_per.setText(String.valueOf(carbs)+ " g"); }
        
        /**
		 * Berechnung des Anteil an Proteine anhand der Gesamtkalorienanzahl und der prozentualen Nährstoffverteilung
		 */
        if(et_protein_per.getText().toString().length() != 0 && et_calories_target.getText().toString().length() != 0) {
        	 double target_calories =  Double.parseDouble(et_calories_target.getText().toString());
	          double protein = (target_calories *  Integer.parseInt(et_protein_per.getText().toString())/100) / 4.1;
	          protein = Math.round(protein *100);
	          protein = protein / 100;
	          tv_protein_per.setText(String.valueOf(protein) + " g"); }
        /**
		 * Berechnung des Anteil an Fett anhand der Gesamtkalorienanzahl und der prozentualen Nährstoffverteilung
		 */
        if(et_fat_per.getText().toString().length() != 0 && et_calories_target.getText().toString().length() != 0){
        	  double target_calories =  Double.parseDouble(et_calories_target.getText().toString());
	          double fat = (target_calories *  Integer.parseInt(et_fat_per.getText().toString())/100) / 9.3;
	          fat = Math.round(fat *100);
	          fat = fat / 100;
	          tv_fat_per.setText(String.valueOf(fat) +" g"); 
        
        /**
         * Solange keine Gesamtkalorienanzahl eingetragen wurde, können die Textfelder für die prozentuale Verteilung nicht bearbeitet werden
         */
        if(et_calories_target.getText().toString().length() == 0) {
        	
        	et_carbs_per.setEnabled(false);
        	et_protein_per.setEnabled(false);
        	et_fat_per.setEnabled(false);
        }
			
		}
		/**
		 * Wenn etwas in das Textfeld der Gesamtkalorien eingetragen wird, können die weiteren Textfelder bearbeitet werden. 
		 */
		et_calories_target.addTextChangedListener(new TextWatcher() 
		  {
	          
	          public void beforeTextChanged(CharSequence s, int start, int count, int after)
	          {
	                    
	          }
	 
	          public void onTextChanged(CharSequence s, int start, int before, int count)
	          {
	        	 
	          }

	          public void afterTextChanged(Editable s)
	          {
	            if(et_calories_target.getText().toString().length() != 0) {
	            	et_carbs_per.setEnabled(true);
	            	et_protein_per.setEnabled(true);
	            	et_fat_per.setEnabled(true);
	            	
	            	if(et_carbs_per.getText().toString().length() != 0 && et_calories_target.getText().toString().length() != 0) {
	  	              double target_calories =  Double.parseDouble(et_calories_target.getText().toString());
	  		          double carbs = (target_calories *  Integer.parseInt(et_carbs_per.getText().toString())/100) / 4.1;
	  		          carbs = Math.round(carbs *100);
	  		          carbs = carbs / 100;
	  		          tv_carbs_per.setText(String.valueOf(carbs)+ " g"); }
	            	
	            	 if(et_protein_per.getText().toString().length() != 0 && et_calories_target.getText().toString().length() != 0) {
		            	  double target_calories =  Double.parseDouble(et_calories_target.getText().toString());
				          double protein = (target_calories *  Integer.parseInt(et_protein_per.getText().toString())/100) / 4.1;
				          protein = Math.round(protein *100);
				          protein = protein / 100;
				          tv_protein_per.setText(String.valueOf(protein) + " g"); }
	            	 
	            	 if(et_fat_per.getText().toString().length() != 0 && et_calories_target.getText().toString().length() != 0){
		            	  double target_calories =  Double.parseDouble(et_calories_target.getText().toString());
				          double fat = (target_calories *  Integer.parseInt(et_fat_per.getText().toString())/100) / 9.3;
				          fat = Math.round(fat *100);
				          fat = fat / 100;
				          tv_fat_per.setText(String.valueOf(fat) +" g"); }
	            	 
	            	
	            }
	          }
	  });
		
		
		 /**
		 * Berechnung des Anteil an Kohlenhydrate anhand der Gesamtkalorienanzahl und der prozentualen Nährstoffverteilung wenn ein Wert in das betroffene Textfeld geschrieben wird.
		 * Außerdem werden die prozentualen Werte komuliert und überprüft ob sie = 100 sind. Falls dies der Fall ist, kann der User die Daten speichern
		 *  - falls nicht wird der User darauf hingewiesen dass die Summe aller prozentualen Angaben = 100 sein müssen.
		 */
		
		et_carbs_per.addTextChangedListener(new TextWatcher() 
		  {
	          
	          public void beforeTextChanged(CharSequence s, int start, int count, int after)
	          {
	                    
	          }
	 
	          public void onTextChanged(CharSequence s, int start, int before, int count)
	          {
	        	 
	          }

	          public void afterTextChanged(Editable s)
	          {
	              int sum = 0;
	              if(et_carbs_per.getText().toString().length() != 0 && et_calories_target.getText().toString().length() != 0) {
	              double target_calories =  Double.parseDouble(et_calories_target.getText().toString());
		          double carbs = (target_calories *  Integer.parseInt(et_carbs_per.getText().toString())/100) / 4.1;
		          carbs = Math.round(carbs *100);
		          carbs = carbs / 100;
		          tv_carbs_per.setText(String.valueOf(carbs)+ " g"); }
	              if(et_carbs_per.getText().toString().length() != 0){
	              sum += Integer.parseInt(s.toString());
	              if(et_protein_per.getText().toString().length() != 0) 
	              sum += Integer.parseInt(et_protein_per.getText().toString());
	              if(et_fat_per.getText().toString().length() != 0) 
	              sum += Integer.parseInt(et_fat_per.getText().toString()); }
	              else  tv_carbs_per.setText("0 g");
	              
	              tv_per_sum.setText(String.valueOf(sum) + "%");
	              if(sum != 100 ) {
	            	  tv_Hinweiss.setText("Die Makronährstoffe müssen gleich 100% sein");
	            	  tv_per_sum.setTextColor(getResources().getColor(R.color.red));
	            	  save.setVisible(false);
	              }
	              else {
	            	  tv_Hinweiss.setText("");
	            	  tv_per_sum.setTextColor(getResources().getColor(R.color.green));
	            	  save.setVisible(true);
	              }
	          }
	  });
		
		 /**
		 * Berechnung des Anteil an Proteine anhand der Gesamtkalorienanzahl und der prozentualen Nährstoffverteilung wenn ein Wert in das betroffene Textfeld geschrieben wird.
		 * Außerdem werden die prozentualen Werte komuliert und überprüft ob sie = 100 sind. Falls dies der Fall ist, kann der User die Daten speichern
		 *  - falls nicht wird der User darauf hingewiesen dass die Summe aller prozentualen Angaben = 100 sein müssen.
		 */
		
		et_protein_per.addTextChangedListener(new TextWatcher() 
				  {
			          
			          public void beforeTextChanged(CharSequence s, int start, int count, int after)
			          {
			                    
			          }
			 
			          public void onTextChanged(CharSequence s, int start, int before, int count)
			          {
			        	 
			          }

			          public void afterTextChanged(Editable s)
			          {
			        	  int sum = 0;
			              if(et_carbs_per.getText().toString().length() != 0) 
			              sum += Integer.parseInt(et_carbs_per.getText().toString());
			              if(et_protein_per.getText().toString().length() != 0 && et_calories_target.getText().toString().length() != 0) {
			            	  double target_calories =  Double.parseDouble(et_calories_target.getText().toString());
					          double protein = (target_calories *  Integer.parseInt(et_protein_per.getText().toString())/100) / 4.1;
					          protein = Math.round(protein *100);
					          protein = protein / 100;
					          tv_protein_per.setText(String.valueOf(protein) + " g"); }
			              if(et_protein_per.getText().toString().length() != 0){
			              sum += Integer.parseInt(et_protein_per.getText().toString());
			              if(et_fat_per.getText().toString().length() != 0) 
			              sum += Integer.parseInt(et_fat_per.getText().toString());}
			              else tv_protein_per.setText("0 g");
			              
			              tv_per_sum.setText(String.valueOf(sum) + "%");
			              
			              if(sum != 100 ) {
			            	  tv_Hinweiss.setText("Die Makronährstoffe müssen gleich 100% sein");
			            	  tv_per_sum.setTextColor(getResources().getColor(R.color.red));
			            	  save.setVisible(false);
			            	  
			              }
			              else {
			            	  tv_Hinweiss.setText("");
			            	  tv_per_sum.setTextColor(getResources().getColor(R.color.green));
			            	  save.setVisible(true);
			            	  
			              }
			       
			          }
			  });
		
		 /**
		 * Berechnung des Anteil an Fett anhand der Gesamtkalorienanzahl und der prozentualen Nährstoffverteilung wenn ein Wert in das betroffene Textfeld geschrieben wird.
		 * Außerdem werden die prozentualen Werte komuliert und überprüft ob sie = 100 sind. Falls dies der Fall ist, kann der User die Daten speichern
		 *  - falls nicht wird der User darauf hingewiesen dass die Summe aller prozentualen Angaben = 100 sein müssen.
		 */
		
		et_fat_per.addTextChangedListener(new TextWatcher() 
				  {
			          
			          public void beforeTextChanged(CharSequence s, int start, int count, int after)
			          {
			                    
			          }
			 
			          public void onTextChanged(CharSequence s, int start, int before, int count)
			          {
			        	 
			          }

			          public void afterTextChanged(Editable s)
			          {
			        	  int sum = 0;
			              if(et_carbs_per.getText().toString().length() != 0) 
			              sum += Integer.parseInt(et_carbs_per.getText().toString());
			              if(et_protein_per.getText().toString().length() != 0) 
			              sum += Integer.parseInt(et_protein_per.getText().toString());
			              if(et_fat_per.getText().toString().length() != 0 && et_calories_target.getText().toString().length() != 0){
			            	  double target_calories =  Double.parseDouble(et_calories_target.getText().toString());
					          double fat = (target_calories *  Integer.parseInt(et_fat_per.getText().toString())/100) / 9.3;
					          fat = Math.round(fat *100);
					          fat = fat / 100;
					          tv_fat_per.setText(String.valueOf(fat) +" g"); }
			              if(et_fat_per.getText().toString().length() != 0) 
			              sum += Integer.parseInt(et_fat_per.getText().toString());
			              else tv_fat_per.setText("0 g");
			              
			              tv_per_sum.setText(String.valueOf(sum) + "%");
			              if(sum != 100 ) {
			            	  tv_Hinweiss.setText("Die Makronährstoffe müssen gleich 100% sein");
			            	  tv_per_sum.setTextColor(getResources().getColor(R.color.red));
			            	  save.setVisible(false);
			            	 
			              }
			              else {
			            	  tv_Hinweiss.setText("");
			            	  tv_per_sum.setTextColor(getResources().getColor(R.color.green));
			            	  save.setVisible(true);
			            	 
			              }
			          }
			  });	
		
		return rootView;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.save:
			/**
			 * Menü Item wird nur beim Start des Fragments geladen wenn etwas in den betroffenen Textfeldern steht. 
			 * Beim Klick auf das Item wird das Nährwertziel gespeichert.
			 */
			if(et_carbs_per.getText().toString().length() != 0 && et_protein_per.getText().toString().length() != 0 && et_fat_per.getText().toString().length() != 0 && et_calories_target.getText().toString().length() != 0 ) {
				DailyAim da = new DailyAim();
				da.setCalories(Double.parseDouble(et_calories_target.getText().toString()));
				String[] split =  tv_carbs_per.getText().toString().split(" ");
				da.setCarbs(Double.parseDouble(split[0]));
				split =  tv_protein_per.getText().toString().split(" ");
				da.setProtein(Double.parseDouble(split[0]));
				split =  tv_fat_per.getText().toString().split(" ");
				da.setFat(Double.parseDouble(split[0]));
				
				daMapper.setDailyAim(da);
				Toast.makeText(getActivity(), "Neues Ziel wurde eingespeichert!", Toast.LENGTH_LONG).show();
			}
			else Toast.makeText(getActivity(), "Das Ziel konnte nicht gespeichert werden!!! :(", Toast.LENGTH_LONG).show();
			
			
			return true; }
		return super.onOptionsItemSelected(item);
	}
	
	

}
