package de.hdm.mobile.health.db;

import java.io.IOException;
import java.util.ArrayList;

import de.hdm.mobile.health.bo.Food;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FoodMapper {
	
	private DataBaseHelper myDBHelper;
	private String sql;
	private int mgID;
	
	public FoodMapper(Context context){
		myDBHelper = new DataBaseHelper(context);
		try {	 
	       	myDBHelper.createDataBase();
	 	} catch (IOException ioe) {
	 		throw new Error("Unable to create database");
	 	}
	 	try {
	 		myDBHelper.openDataBase();
	 	}catch(SQLException sqle){
	 		throw sqle;
	 	}
	}
	
	/**
	 * Add a new Food to the Database
	 * 
	 * @param f Food to be added
	 * @author Eric Schmidt
	 */
	public void add(Food f){
		SQLiteDatabase db = myDBHelper.getWritableDatabase();
		int id = 0;
		
		/**
		 * Select the highest id in table Food
		 */
		sql = "SELECT MAX(Food_Id) FROM Food";
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()){
			if (!cursor.isNull(0)){
				id = Integer.parseInt(cursor.getString(0));
				id++;
				f.setId(id);
			}
		}	
		sql = "INSERT INTO Food (Barcode, Food_Id, Name, Kilojoule, Calories,"
				+ " Protein, Carbs, Fat ) VALUES ('" + f.getBarcode() + "', " + id 
				+ ", '" + f.getName() + "', " + f.getKilojoule() + ", " + f.getCalories()
				+ ", " + f.getProtein() + ", " + f.getCarbs() + ", " + f.getFat() + ")";
		db.execSQL(sql);
		db.close();
	}
}

