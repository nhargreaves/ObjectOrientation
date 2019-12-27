package sportsLeague;

import java.time.LocalDate;

public class Goalkeeper extends Players{
	private int cleanSheets;
	
	public Goalkeeper(String newName, LocalDate newDob, int newHeight) {
		super(newName, newDob, newHeight);
	}

	public int getCleanSheets() {
		return cleanSheets;
	}

	public void setCleanSheets(int cleanSheets) {
		this.cleanSheets = cleanSheets;
	}
	
	

}
