package de.hdm.mobile.health;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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

public class Scanning extends Activity{
	
	private EditText formatTxt, contentTxt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanning);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scanning, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();
			new BackGroundTask(Scanning.this).execute("42143529");
		}
		return super.onOptionsItemSelected(item);
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
			new BackGroundTask(Scanning.this).execute(scanningResult.getContents());

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
		private EditText fat, protein, carb, cal, name;
		private String fatString, proteinString, carbString, calString, nameString;
		private ProgressDialog mDialog;
		
		public BackGroundTask (Scanning activity){
			fat = (EditText)findViewById(R.id.scan_fat);
			protein = (EditText)findViewById(R.id.scan_protein);
			carb = (EditText)findViewById(R.id.scan_carb);
			cal = (EditText)findViewById(R.id.scan_kalc);
			name = (EditText)findViewById(R.id.scan_name);
			
		    mDialog = new ProgressDialog(Scanning.this);
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
				Document doc = Jsoup.connect("http://www.barcoo.com/" + params[0].toString() + "?source=pb/").get();
				nameString = doc.title();
				String[] splitResult = nameString.split("-");
				nameString = splitResult[0];
				Elements nutritionTable = doc.select(".nutTbl");	
				nutritionTable = nutritionTable.select("tbody");
				for (Element row : nutritionTable.select("tr")) {
					 Elements tds = row.select("td");
					 switch(tds.get(0).text()){
						 case "Kohlenhydrate":
							 carbString=tds.get(1).text();
							 break;
						 case "Fett":
							 fatString=tds.get(1).text();
							 break;
						 case "Eiweiﬂ":
							 proteinString=tds.get(1).text();
							 break;
						 case "Kalorien":
							 calString=tds.get(1).text();
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
