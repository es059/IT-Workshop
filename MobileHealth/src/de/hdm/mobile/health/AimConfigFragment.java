package de.hdm.mobile.health;

import java.text.DecimalFormat;

import de.hdm.mobile.health.bo.DailyAim;
import de.hdm.mobile.health.db.DailyAimMapper;
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

public class AimConfigFragment extends Fragment {
	
private TextView tv_carbs_per;
private TextView tv_protein_per;
private TextView tv_fat_per;

private TextView tv_Hinweiss;

private EditText et_carbs_per;
private EditText et_protein_per;
private EditText et_fat_per;

private EditText et_calories_target;
private Button button;
private DailyAimMapper daMapper;



	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.aim_config, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_aimconfig, container, false);
		
		daMapper = new DailyAimMapper(getActivity());
		
		tv_carbs_per = (TextView) rootView.findViewById(R.id.tv_carbs_per);
		tv_protein_per = (TextView) rootView.findViewById(R.id.tv_protein_per);
		tv_fat_per = (TextView) rootView.findViewById(R.id.tv_fat_per);
		
		tv_Hinweiss = (TextView) rootView.findViewById(R.id.tv_Hinweiss);
		
		et_carbs_per = (EditText) rootView.findViewById(R.id.et_carbs_per);
		et_protein_per = (EditText) rootView.findViewById(R.id.et_protein_per);
		et_fat_per = (EditText) rootView.findViewById(R.id.et_fat_per);
		
		et_calories_target = (EditText) rootView.findViewById(R.id.et_calories_target);
		
		button = (Button) rootView.findViewById(R.id.button1);
		
		// TextWatcher
		
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
	              int target_calories =  Integer.parseInt(et_calories_target.getText().toString());
		          double carbs = (target_calories *  Integer.parseInt(et_carbs_per.getText().toString())/100) / 4.1;
		          carbs = Math.round(carbs *100);
		          carbs = carbs / 100;
		          tv_carbs_per.setText(String.valueOf(carbs)+ " g"); }
	              
	              sum += Integer.parseInt(s.toString());
	              if(et_protein_per.getText().toString().length() != 0) 
	              sum += Integer.parseInt(et_protein_per.getText().toString());
	              if(et_fat_per.getText().toString().length() != 0) 
	              sum += Integer.parseInt(et_fat_per.getText().toString());
	              
	              if(sum != 100 ) {
	            	  tv_Hinweiss.setText("Die Makronährstoffe müssen gleich 100% sein");
	            	  button.setVisibility(View.INVISIBLE);
	              }
	              else {
	            	  tv_Hinweiss.setText("");
	            	  button.setVisibility(View.VISIBLE);
	              }
	          }
	  });
		
		// TextWatcher
		
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
				              int target_calories =  Integer.parseInt(et_calories_target.getText().toString());
					          double protein = (target_calories *  Integer.parseInt(et_protein_per.getText().toString())/100) / 4.1;
					          protein = Math.round(protein *100);
					          protein = protein / 100;
					          tv_protein_per.setText(String.valueOf(protein) + " g"); }
			              sum += Integer.parseInt(et_protein_per.getText().toString());
			              if(et_fat_per.getText().toString().length() != 0) 
			              sum += Integer.parseInt(et_fat_per.getText().toString());
			              
			              if(sum != 100 ) {
			            	  tv_Hinweiss.setText("Die Makronährstoffe müssen gleich 100% sein");
			            	  button.setVisibility(View.INVISIBLE);
			              }
			              else {
			            	  tv_Hinweiss.setText("");
			            	  button.setVisibility(View.VISIBLE);
			              }
			       
			          }
			  });
		
	// TextWatcher
		
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
				              int target_calories =  Integer.parseInt(et_calories_target.getText().toString());
					          double fat = (target_calories *  Integer.parseInt(et_fat_per.getText().toString())/100) / 9.3;
					          fat = Math.round(fat *100);
					          fat = fat / 100;
					          tv_fat_per.setText(String.valueOf(fat) +" g"); }
			              sum += Integer.parseInt(et_fat_per.getText().toString());
			              
			              if(sum != 100 ) {
			            	  tv_Hinweiss.setText("Die Makronährstoffe müssen gleich 100% sein");
			            	  button.setVisibility(View.INVISIBLE);
			              }
			              else {
			            	  tv_Hinweiss.setText("");
			            	  button.setVisibility(View.VISIBLE);
			              }
			          }
			  });
		
		
		setHasOptionsMenu(true);
		
		
		
		
		return rootView;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.save:
			
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
				Toast.makeText(getActivity(), "asdasasd!!! =)", Toast.LENGTH_LONG).show();
			}
			else Toast.makeText(getActivity(), "this is my Toast message!!! =)", Toast.LENGTH_LONG).show();
			
			
			return true; }
		return super.onOptionsItemSelected(item);
	}
	
	

}
