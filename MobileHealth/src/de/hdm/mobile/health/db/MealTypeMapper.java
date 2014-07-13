package de.hdm.mobile.health.db;

import java.io.IOException;

import de.hdm.mobile.health.bo.Food;
import de.hdm.mobile.health.bo.Mealtype;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Mapper-Klasse welche Mahlzeitarten aus der Datenbank auslesen kann. 
 * @author remi
 *
 */
public class MealTypeMapper {
	private DataBaseHelper myDBHelper;
	private String sql;
	private Context context;
	public MealTypeMapper(Context context){
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
	
	/**
	 * Methode zum Auslesen eines Mahlzeitart anhand einer eindeutigen ID
	 */
	public Mealtype getMealTypeById(int Id) {
		Mealtype mealtype = new Mealtype();
	    SQLiteDatabase db = this.myDBHelper.getReadableDatabase();
	    sql = "SELECT * FROM Mealtype WHERE Mealtype_Id = " + Id;
	    Cursor cursor = db.rawQuery(sql, null);
	    if (cursor.moveToFirst()){
	    	mealtype.setId(Id);
	    	mealtype.setName(cursor.getString(1));
		   
	    }
	    db.close();
	    return mealtype;
	}
	
	
}
