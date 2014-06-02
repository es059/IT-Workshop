package de.hdm.mobile.health;

import de.hdm.mobile.health.bo.Mealtype;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomListViewHeader extends RelativeLayout {
	
	private TextView sectionView;
	private Button button;
	private Mealtype mealtype;
	private Activity activity;

	public CustomListViewHeader(Context context, final Activity activity) {
		super(context);
		this.activity = activity;
		LayoutInflater inflater = LayoutInflater.from(context);
		View listViewExercise = inflater.inflate(R.layout.listview_header, null);
		//sectionView = (TextView) listViewExercise.findViewById(R.id.listview_header);
		
		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
			
				Bundle bl = new Bundle();
				bl.putInt("mealtype_Id", mealtype.getId());
				FoodSearchFragment frag = new FoodSearchFragment();
				frag.setArguments(bl);
				FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
			    transaction.hide(activity.getFragmentManager().findFragmentById(R.id.LogIn_Bottom));
		        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		        transaction.replace(R.id.fragment_container, frag, "ada");
		        transaction.addToBackStack(null);
		        
		        transaction.commit();
				
			}
			
		});
		addView(listViewExercise);
	}
	/**
	 * Set the Text of the TextViews within the Custom Layout using the parameter 
	 * Exercise
	 * 
	 * @param Exercise exercise
	 * @author Eric Schmidt
	 */
	public void setMealtype(Mealtype mealtype){
		//this.sectionView.setText(mealtype.getName());
		this.mealtype = mealtype;
		


	}

}
