package de.hdm.mobile.health.bo;

/**
 * Objekte dieser Klasse repräsentieren ein tägliches Nährwerteziel. 
 * @author remi
 *
 */
public class DailyAim {

	private double calories;
	private double kilojoule;
	private double protein;
	private double fat;
	private double carbs;
	public double getCalories() {
		return calories;
	}
	public void setCalories(double calories) {
		this.calories = calories;
	}
	public double getKilojoule() {
		return kilojoule;
	}
	public void setKilojoule(double kilojoule) {
		this.kilojoule = kilojoule;
	}
	public double getProtein() {
		return protein;
	}
	public void setProtein(double protein) {
		this.protein = protein;
	}
	public double getFat() {
		return fat;
	}
	public void setFat(double fat) {
		this.fat = fat;
	}
	public double getCarbs() {
		return carbs;
	}
	public void setCarbs(double carbs) {
		this.carbs = carbs;
	}
	

}
