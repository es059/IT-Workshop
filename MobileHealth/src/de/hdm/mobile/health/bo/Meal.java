package de.hdm.mobile.health.bo;

import java.util.Date;

import de.hdm.mobile.health.ListItem;

public class Meal implements ListItem {
	
	private Food food;
	private double amount;
	private Mealtype mealtype;
	private Date date;
	
	
	public Food getFood() {
		return food;
	}
	public void setFood(Food food) {
		this.food = food;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Mealtype getMealtype() {
		return mealtype;
	}
	public void setMealtype(Mealtype mealtype) {
		this.mealtype = mealtype;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public boolean isSection() {
		return false;
	}
	
	

}
