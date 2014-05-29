package de.hdm.mobile.health.db;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import de.hdm.mobile.health.ListItem;
import de.hdm.mobile.health.bo.Meal;
import de.hdm.mobile.health.bo.Mealtype;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class MealMapper {
	private DataBaseHelper myDBHelper;
	private String sql;
	private Context context;
	private FoodMapper foodMapper = new FoodMapper(context);
	private MealTypeMapper mealTypeMapper = new MealTypeMapper(context);
	
	
	public MealMapper(Context context){
		myDBHelper = new DataBaseHelper(context);
		this.context = context;
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
	
	
	public ArrayList<Meal> getMealsAday(Date date, int mealtype_Id) {
		ArrayList<Meal> mealsAday = new ArrayList<Meal>();
		
		SQLiteDatabase db = myDBHelper.getWritableDatabase();
		SimpleDateFormat sp = new SimpleDateFormat("dd.MM.yyyy");
		sql = "SELECT * FROM Meal WHERE Day='" + date + "' AND WHERE Mealtype_Id=" + mealtype_Id;
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()){
			do{
				Meal m = new Meal();
				m.setFood(foodMapper.getFoodById(cursor.getInt(1)));
				m.setAmount(cursor.getDouble(2));
				m.setMealtype(mealTypeMapper.getMealTypeById(cursor.getInt(3)));
				try {
					m.setDate(sp.parse(cursor.getString(4)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}while(cursor.moveToNext());
		}
		db.close();
		return mealsAday;
	}


	public void addMeal(Meal m) {
		SQLiteDatabase db = myDBHelper.getWritableDatabase();
		SimpleDateFormat sp = new SimpleDateFormat("dd.MM.yyyy");
		String date = sp.format(m.getDate());
			
		sql = "INSERT INTO Meal (Food_Id, Amount, Mealtype_Id, Day)"
				+ " VALUES ('" + m.getFood().getId() + "', " + m.getAmount()
				+ ", '" + m.getMealtype().getId() + "',  " + date + ")";
		db.execSQL(sql);
		db.close();
		
	}

	public ArrayList<Meal> getMealsAday(Date date) {
		ArrayList<Meal> mealsAday = new ArrayList<Meal>();
		
		SQLiteDatabase db = myDBHelper.getWritableDatabase();
		SimpleDateFormat sp = new SimpleDateFormat("dd.MM.yyyy");
		sql = "SELECT * FROM Meal WHERE Day='" + date + "'";
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()){
			do{
				Meal m = new Meal();
				m.setFood(foodMapper.getFoodById(cursor.getInt(1)));
				m.setAmount(cursor.getDouble(2));
				m.setMealtype(mealTypeMapper.getMealTypeById(cursor.getInt(3)));
				try {
					m.setDate(sp.parse(cursor.getString(4)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}while(cursor.moveToNext());
		}
		db.close();
		return mealsAday;
	}
	
}
