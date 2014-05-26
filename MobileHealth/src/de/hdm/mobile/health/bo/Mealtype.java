package de.hdm.mobile.health.bo;

import de.hdm.mobile.health.ListItem;

public class Mealtype implements ListItem {
	
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean isSection() {
		
		return true;
	}
	
	
	
}
