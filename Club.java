package sportsLeague;
//TODO: this structure assumes that everything is blank when entered and scores etc must be manually added
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class Club implements Comparable<Club>{
	String clubName;
	private int goalsScored, goalsAgainst, goalDifference, totalPoints;
	private Goalkeeper goalie;
	ArrayList<Players> squad = new ArrayList<Players>();

	//add player to squad of club
	//find out if a player is in the clubs squad
	//find the average age of the squad
	//find the average height of the squad
	
	/*
	 * Create a player
	 * Purpose: adds a player to the Players list.
	 */
	public Club(String newClubName) {
		this.clubName = newClubName;
		this.goalsScored = 0;
		this.goalsAgainst = 0;
		this.goalDifference = 0;
		this.totalPoints = 0;
		
	}
	

	//Get/Set club name
	public void setName(String newClubName) {
		this.clubName = newClubName;
	}
	public String getName() {
		return clubName;
	}
	
	public int getGoalsScored() {
		return goalsScored;
	}
	
	//add to goals scored
	public void incGoalsScored(int score) {
		this.goalsScored += score;
	}
	
	public int getGoalsAgainst() {
		return goalsAgainst;
	}
	
	//add to goals conceded
	public void incGoalsAgainst(int score) {
		this.goalsAgainst += score;
	}
	
	public void setDifference(int difference) {
		setGoalDifference(difference);
	}
	
	//get goals difference
	public int getDifference() {
		return goalDifference;
	}
	
	//add to points //should be 3 for win, 1 for draw, nothing for loss
	public void addPoints() {
		if (getDifference() > 0) { //if won
			totalPoints += 3;
		} else if (getDifference() == 0) {
			totalPoints += 1;
		}
	}
	
	//add a player to the squad
	public boolean addToSquad(Players player) {
		if (squad.contains(player)) {
			System.out.println("Error: Player is already in the squad.");
			return false;
		} else {
			squad.add(player);
			return true;
		}
	}
	
	public void setTotalPoints(int points) {
		this.totalPoints = points;
	}
	public int getTotalPoints() {
		return totalPoints;
	}
	//get all players in a squad: added for ease of testing
	public ArrayList<Players> getSquad() {
		return squad;
	}
	
	//check if a player is in the squad
	public boolean isInSquad(Players player) { //should this be a boolean, or return the player?	
		if (squad.contains(player)) {
			return true;
		} else {
			return false;
		}
	}
	
	//get avg player height
	public double getAvgHeight() {
		int total = 0;
		if(!squad.isEmpty()) {
			for (Players squad : squad) {
				total += squad.getHeight();
		    }
		    return total / squad.size();
		  }
		return -1; //if no players are in the squad
	}
	
	
	//get avg player age
	public double getAvgAge() {
		int total = 0;
		if(!squad.isEmpty()) {
			for (Players squad : squad) {
				//player dob is saved rather than age, so age must now be calculated
	            int age =  Period.between(squad.getDob(), LocalDate.now()).getYears();
				total += age;
		    }
		    return total / squad.size();
		  }
		return -1; //if no players are in the squad
	}	
	
	@Override
	public int compareTo(Club c) {
		int compare=((Club)c).getTotalPoints();
        return this.totalPoints-compare;
	}


	public Goalkeeper getGoalie() {
		return goalie;
	}


	public void setGoalie(Goalkeeper goalie) {
		this.goalie = goalie;
	}


	public int getGoalDifference() {
		return goalDifference;
	}


	public void setGoalDifference(int goalDifference) {
		this.goalDifference = goalDifference;
	}
	
}
