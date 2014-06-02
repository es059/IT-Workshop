package de.hdm.mobile.health.db;

import java.io.IOException;

import de.hdm.mobile.health.bo.DailyAim;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DailyAimMapper {
	private DataBaseHelper myDBHelper;
	private String sql;
	
	public DailyAimMapper(Context context){
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
 * Method to get the daily Aim of the User 
 */
	public DailyAim getDailyAim() {
		DailyAim dailyAim = new DailyAim();
		
		SQLiteDatabase db = myDBHelper.getWritableDatabase();
		
		sql = "SELECT * FROM DailyAim";
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()){
			do{
				dailyAim.setCalories(cursor.getDouble(1));
				dailyAim.setKilojoule(cursor.getDouble(2));
				dailyAim.setProtein(cursor.getDouble(3));
				dailyAim.setFat(cursor.getDouble(4));
				dailyAim.setCarbs(cursor.getDouble(5));
				
			}while(cursor.moveToNext());
		}
		db.close();
		return dailyAim;
	}
	
	public void setDailyAim(DailyAim da) {
		SQLiteDatabase db = myDBHelper.getWritableDatabase();
		sql = "INSERT OR REPLACE INTO DailyAim (Calories, Proteine, Fat, Carbs) VALUES ("+ da.getCalories()+ "," + da.getProtein() +","+ da.getFat()+ ", "+ da.getCarbs() +")";
		db.execSQL(sql);
		db.close();
		
		
	}
}
