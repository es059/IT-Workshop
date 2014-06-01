package de.hdm.mobile.health.db;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdm.mobile.health.bo.Food;
import de.hdm.mobile.health.bo.Meal;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FoodMapper {
	
	private DataBaseHelper myDBHelper;
	private String sql;
	
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

	public ArrayList<Food> searchKeyString(String valueOf) {
		SQLiteDatabase db = myDBHelper.getWritableDatabase();
	    ArrayList<Food> foodList = new ArrayList<Food>();
	 



	     sql =  "SELECT * FROM Food WHERE Name LIKE '%" + valueOf + "%'";
	     Cursor cursor = db.rawQuery(sql, null);
	     if (cursor.moveToFirst()) {
	            do {
	            	Food food = new Food();
					food.setBarcode(cursor.getString(0));
					food.setId(cursor.getInt(1));
					food.setName(cursor.getString(2));
					food.setKilojoule(cursor.getDouble(3));
					food.setCalories(cursor.getDouble(4));
					food.setProtein(cursor.getDouble(5));
					food.setCarbs(cursor.getDouble(6));
					food.setFat(cursor.getDouble(7));
					foodList.add(food);

	            } while (cursor.moveToNext());
	        }

	        cursor.close();


	    return foodList;
	}
	
	public Food getFoodById(int Id) {
		  	Food food = new Food();
		    SQLiteDatabase db = this.myDBHelper.getReadableDatabase();
		    sql = "SELECT * FROM Food WHERE Food_Id = " + Id;
		    Cursor cursor = db.rawQuery(sql, null);
		    if (cursor.moveToFirst()){
			   food.setBarcode(cursor.getString(0));
			   food.setName(cursor.getString(2));
			   food.setKilojoule(cursor.getDouble(3));
			   food.setCalories(cursor.getDouble(4));
			   food.setProtein(cursor.getDouble(5));
			   food.setCarbs(cursor.getDouble(6));
			   food.setFat(cursor.getDouble(7));
		    }
		    db.close();
		    return food;
	}

	public ArrayList<Food> getAll() {
		ArrayList<Food> foodList = new ArrayList<Food>();
		SQLiteDatabase db = myDBHelper.getWritableDatabase();
		sql = "SELECT * FROM Food";
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()){
			do{
				Food food = new Food();
				 food.setBarcode(cursor.getString(0));
				 food.setId(cursor.getInt(1));
				   food.setName(cursor.getString(2));
				   food.setKilojoule(cursor.getDouble(3));
				   food.setCalories(cursor.getDouble(4));
				   food.setProtein(cursor.getDouble(5));
				   food.setCarbs(cursor.getDouble(6));
				   food.setFat(cursor.getDouble(7));
				   foodList.add(food);
				
			}while(cursor.moveToNext());
		}
		db.close();
		return foodList;
	}
}

