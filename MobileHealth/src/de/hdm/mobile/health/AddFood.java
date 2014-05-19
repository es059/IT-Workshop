package de.hdm.mobile.health;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import de.hdm.mobile.health.bo.Food;
import de.hdm.mobile.health.db.FoodMapper;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class AddFood extends Activity{
	
	public String barcode;
	private EditText fat, protein, carb, cal, name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_food);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_food, menu);
		return true;
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
				    Toast toast = Toast.makeText(getApplicationContext(), 
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
			food.setBarcode(barcode);
			food.setCalories(Double.parseDouble(cal.getText().toString()));
			food.setCarbs(Double.parseDouble(carb.getText().toString()));
			food.setFat(Double.parseDouble(fat.getText().toString()));
			food.setName(name.getText().toString());
			food.setProtein(Double.parseDouble(protein.getText().toString()));
			FoodMapper fMapper = new FoodMapper(this);
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
			barcode = scanningResult.getContents();
			new BackGroundTask(AddFood.this).execute(barcode);

		}else{
		    Toast toast = Toast.makeText(getApplicationContext(), 
		        "No scan data received!", Toast.LENGTH_SHORT);
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
			fat = (EditText)findViewById(R.id.scan_fat);
			protein = (EditText)findViewById(R.id.scan_protein);
			carb = (EditText)findViewById(R.id.scan_carb);
			cal = (EditText)findViewById(R.id.scan_kalc);
			name = (EditText)findViewById(R.id.scan_name);
			
		    mDialog = new ProgressDialog(AddFood.this);
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
