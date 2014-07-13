package de.hdm.mobile.health.bo;


/**
 * Ein Objekt dieser Klasse repräsentiert einen Nutzer. 
 * @author remi
 *
 */
public class User {
	private String mLastName;
	private int mId;
	private String mSurename;
	private double mHeight;
	private double mWeight;
	private double mGender;
	private double mAge;
	private double mActivitylevel;

	public User(){
		
	}

	public String getLastName() {
		return mLastName;
	}

	public void setLastName(String mLastName) {
		this.mLastName = mLastName;
	}

	public String getSurename() {
		return mSurename;
	}

	public void setSurename(String mSurename) {
		this.mSurename = mSurename;
	}

	public int getId() {
		return mId;
	}

	public void setId(int mId) {
		this.mId = mId;
	}

	public double getHeight() {
		return mHeight;
	}

	public void setHeight(double mHeight) {
		this.mHeight = mHeight;
	}

	public double getWeight() {
		return mWeight;
	}

	public void setWeight(double mWeight) {
		this.mWeight = mWeight;
	}

	public double getGender() {
		return mGender;
	}

	public void setGender(double mGender) {
		this.mGender = mGender;
	}

	public double getAge() {
		return mAge;
	}

	public void setAge(double mAge) {
		this.mAge = mAge;
	}

	public double getActivitylevel() {
		return mActivitylevel;
	}

	public void setActivitylevel(double mActivitylevel) {
		this.mActivitylevel = mActivitylevel;
	}
	
	
}
