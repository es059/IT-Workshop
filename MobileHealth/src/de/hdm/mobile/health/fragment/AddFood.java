package de.hdm.mobile.health.fragment;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import de.hdm.mobile.health.R;
import de.hdm.mobile.health.bo.Food;
import de.hdm.mobile.health.db.FoodMapper;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class AddFood extends Fragment{

	public String barcodeString = "";
	private EditText fat, protein, carb, cal, name, barcode;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.add_food, container, false);
	    
		fat = (EditText)view.findViewById(R.id.scan_fat);
		protein = (EditText)view.findViewById(R.id.scan_protein);
		carb = (EditText)view.findViewById(R.id.scan_carb);
		cal = (EditText)view.findViewById(R.id.scan_kalc);
		name = (EditText)view.findViewById(R.id.scan_name);
		barcode = (EditText)view.findViewById(R.id.scan_barcode);
		
	    setHasOptionsMenu(true); 
		return view;	
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.add_food, menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id){
			case R.id.action_settings:
				IntentIntegrator scanIntegrator = new IntentIntegrator(this);
				scanIntegrator.initiateScan();
				break;
			case R.id.action_save:
				if (saveFood()){
				    Toast toast = Toast.makeText(getActivity(), 
					        "Neues Lebensmittel wurde angelegt", Toast.LENGTH_SHORT);
					toast.show();
				};
				break;
			}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Save the current entered food into the databse
	 * 
	 * @return true if success
	 * @author Eric Schmidt
	 */
	private Boolean saveFood() {
		// TODO Auto-generated method stub
		Food food = new Food();
		if (name.getText().toString() != ""){	
			food.setBarcode(barcodeString);
			if(cal.getText().toString().length() != 0)
			food.setCalories(Double.parseDouble(cal.getText().toString()));
			if(carb.getText().toString().length() != 0)
			food.setCarbs(Double.parseDouble(carb.getText().toString()));
			if(fat.getText().toString().length() != 0)
			food.setFat(Double.parseDouble(fat.getText().toString()));
			if(name.getText().toString().length() != 0)
			food.setName(name.getText().toString());
			if(protein.getText().toString().length() != 0)
			food.setProtein(Double.parseDouble(protein.getText().toString()));
			FoodMapper fMapper = new FoodMapper(getActivity());
			fMapper.add(food);
			return true;
		}
		return false;
	}

	/**
	 * Implements the actions which are to be done after the Scanning is done
	 * 
	 * @author Eric Schmidt
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult != null) {
			/**
			 * Parse the HTML page in class <code>BackGroundTask</code> of barcoo with the result and get the 
			 * attributes for the new grocery 
			 * 
			 */
			barcodeString = scanningResult.getContents();
		    if(barcodeString != null){
				new BackGroundTask(AddFood.this).execute(barcodeString);
				barcode.setText(barcodeString);
		    }else{
			    Toast toast = Toast.makeText(getActivity(), "Barcode scanner wurde beendet", Toast.LENGTH_SHORT);
			    toast.show();
		    }
		}else{
		    Toast toast = Toast.makeText(getActivity(), "No scan data received!", Toast.LENGTH_SHORT);
		    toast.show();
		}
	}

	/**
	 * Handels the HTML parsing in an Async Task
	 * 
	 * @author Eric Schmidt
	 */
	public class BackGroundTask extends AsyncTask<String, Void, Void> {
		private String fatString, proteinString, carbString, calString, nameString;
		private ProgressDialog mDialog;

		public BackGroundTask (AddFood activity){
		    mDialog = new ProgressDialog(AddFood.this.getActivity());
		    mDialog.setProgressStyle(ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
		    mDialog.setMessage("Lade Information");
		    mDialog.setCancelable(false);
		    mDialog.setCanceledOnTouchOutside(false);
		}

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        mDialog.show();
	    }

	    @Override
	    protected Void doInBackground(String... params) {

			try {
				/**
				 * Fetch Data from barcoo and set the corresponding TextViews with
				 * the according information
				 * 
				 * @author Eric Schmidt
				 */
				String url = "http://www.barcoo.com/" + params[0].toString() + "?source=pb/";
				Document doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
				//Document doc = Jsoup.connect("http://www.barcoo.com/" + params[0].toString() + "?source=pb/").get();
				nameString = doc.title();
				String[] tempSplit;
				String[] splitResult = nameString.split("-");
				nameString = splitResult[0];
				Elements nutritionTable = doc.select(".nutTbl");	
				nutritionTable = nutritionTable.select("tbody");
				for (Element row : nutritionTable.select("tr")) {
					 Elements tds = row.select("td");
					 switch(tds.get(0).text()){
						 case "Kohlenhydrate":
							 tempSplit = tds.get(1).text().split(" ");
							 carbString= tempSplit[0];
							 break;
						 case "Fett":
							 tempSplit = tds.get(1).text().split(" ");
							 fatString=tempSplit[0];
							 break;
						 case "Eiweiﬂ":
							 tempSplit = tds.get(1).text().split(" ");
							 proteinString=tempSplit[0];
							 break;
						 case "Kalorien":
							 tempSplit = tds.get(1).text().split(" ");
							 calString=tempSplit[0];
							 break;
					 }
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
	    }

	    @Override
	    protected void onPostExecute(Void result) {
	        super.onPostExecute(result);

	        cal.setText(calString);
	        protein.setText(proteinString);
	        carb.setText(carbString);
	        fat.setText(fatString);
	        name.setText(nameString);

	        if (mDialog.isShowing()) {
	        	mDialog.dismiss();
	        }

	    }

	}
}