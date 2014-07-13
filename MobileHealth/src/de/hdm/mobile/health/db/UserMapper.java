package de.hdm.mobile.health.db;

import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import de.hdm.mobile.health.bo.User;


/**
 * Mapper-Klasse welche Nutzer auslesen und hinzuf�gen kann 
 * @author remi
 *
 */
public class UserMapper {
	private DataBaseHelper myDBHelper;
	private String sql;
	
	public UserMapper(Context context){
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
	 * Add a new User to the Database
	 * 
	 * @param f Food to be added
	 * @author Eric Schmidt
	 */
	public void add(User n){
		SQLiteDatabase db = myDBHelper.getWritableDatabase();
		int id = 0;
		
		/**
		 * Select the highest id in table Food
		 */
		sql = "SELECT MAX(User_Id) FROM User";
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()){
			if (!cursor.isNull(0)){
				id = Integer.parseInt(cursor.getString(0));
				id++;
				n.setId(id);
			}
		}	
		sql = "INSERT INTO User (User_Id, LastName, Surname, Height, Weight, Gender,"
				+ "Age, Activitylevel) VALUES (" + id + ", '" + n.getLastName() 
				+ "', '" + n.getSurename() + "', " + n.getHeight() + ", " + n.getWeight()
				+ ", " + n.getGender()+ ", " + n.getAge() + ", " + n.getActivitylevel() + ")";
		db.execSQL(sql);
		db.close();
	}
	
	/**
	 * Check if a User exists
	 * 
	 * @return true if user exists
	 * @author Eric Schmidt
	 */
	public boolean userExist(){
		SQLiteDatabase db = myDBHelper.getWritableDatabase();
		sql = "SELECT * FROM User";
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()){
			return true;
		}else{
			return false;
		}	
	}
	/**
	 * Methode zum Auslesen des Nutzers. 
	 * @return
	 */
	public User getUser() {
		SQLiteDatabase db = myDBHelper.getWritableDatabase();
		sql = "SELECT * FROM User";
		User user = new User();
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()){
			user.setId(cursor.getInt(0));
			user.setLastName(cursor.getString(1));
			user.setSurename(cursor.getString(2));
			user.setHeight(cursor.getDouble(3));
			user.setWeight(cursor.getDouble(4));
			user.setGender(cursor.getDouble(5));
			user.setAge(cursor.getDouble(6));
			user.setActivitylevel(cursor.getDouble(7));
		}
			
		return user;
	}
}
