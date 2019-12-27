package sportsLeague;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/*
1. Create a java class to represent players.
 */

public class Players {
	private String playerName;
	private LocalDate dob;
	private int height; //height in inches (?)
	private int goals;
	
	//overrided toString enables easier formatting
	 @Override
	    public String toString() { 
	        return String.format(playerName + ", " + dob + ", " + height); 
	    } 
	 
	//this is a constructor: it sets the initial values
	public Players(String newName, LocalDate newDob, int newHeight) {
		this.playerName = newName;
		this.dob = newDob;
		this.height = newHeight;
		this.goals = 0; //player has not scored a goal yet
		
	}

	//get/set name
	public String getName() {
		return playerName;
	}
	public void setName(String newName) {
		this.playerName = newName;
	}
	
	//Get/set dob
	public void setDob(LocalDate newDob) {
		this.dob = newDob;
	}
	public LocalDate getDob() {
		return dob;
	}
	
	//Get/Set height
	public void setHeight(int newHeight) {
		height = newHeight;
	}
	public int getHeight() {
		return height;
	}
	
	//Add to the player's goal tally 
	public void addGoals(int newGoals) { 
		goals += newGoals;
	}
	
	//get player's goal tally
	public int getGoals() {
		return goals;
	}
	
	
}
