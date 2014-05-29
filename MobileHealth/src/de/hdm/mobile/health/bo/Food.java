package de.hdm.mobile.health.bo;

public class Food {
	private String mName;
	private int mId;
	private String mBarcode;
	private double mKilojoule;
	private double mCalories;
	private double mProtein;
	private double mCarbs;
	private double mFat;

	public Food(){
		
	}
	
	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public  int getId() {
		return mId;
	}

	public void setId(int Id) {
		mId = Id;
	}

	public String getBarcode() {
		return mBarcode;
	}

	public void setBarcode(String mBarcode) {
		this.mBarcode = mBarcode;
	}

	public double getKilojoule() {
		return mKilojoule;
	}

	public void setKilojoule(double mKilojoule) {
		this.mKilojoule = mKilojoule;
	}

	public double getCalories() {
		return mCalories;
	}

	public void setCalories(double mCalories) {
		this.mCalories = mCalories;
	}

	public double getProtein() {
		return mProtein;
	}

	public void setProtein(double mProtein) {
		this.mProtein = mProtein;
	}

	public double getCarbs() {
		return mCarbs;
	}

	public void setCarbs(double mCarbs) {
		this.mCarbs = mCarbs;
	}

	public double getFat() {
		return mFat;
	}

	public void setFat(double mFat) {
		this.mFat = mFat;
	}
}
