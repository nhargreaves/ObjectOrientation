package sportsLeague;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Match {

	private Date time;
	private Club club1, club2;
	private String place;
	private ArrayList<Players> players1, players2;
	private boolean played;

	//constructor to initialise matches
	public Match(Date newTime, String newPlace, Club newClub1, Club newClub2, ArrayList<Players> newPlayer1,
			ArrayList<Players> newPlayer2) {
		this.time = newTime;
		this.place = newPlace;
		this.club1 = newClub1;
		this.club2 = newClub2;
		this.players1 = newPlayer1;
		this.players2 = newPlayer2;
		this.played = false;
	}

	// get/set for time
	public Date getTime() {
		return time;
	}

	// get match time
	@Override
	public String toString() {
		return String.format(place + " at " + time + " between " + club1.getName() + " & " + club2.getName());
	}

	public boolean getPlayed() {
		return played;
	}
	
	public void setTime(Date newTime) {
		time = newTime;
	}

	// get/set for place
	public void setPlace(String newPlace) {
		place = newPlace;
	}

	public String getPlace() {
		return place;
	}

	// get/set for club names
	public void setClubs(Club newClub1, Club newClub2) {
		club1 = newClub1;
		club2 = newClub2;
	}

	public Club getClub1() {
		return club1;
	}

	public Club getClub2() {
		return club2;
	}

	// get the player scores and add them to retrieve the final score
	//this holds the bulk of the logic for the score system
	public void addScores(ArrayList<Players> playerList, int oldCount1, int oldCount2, Club club1, Club club2) {
		int club1Score = 0;
		int club2Score = 0;
		
		//go through playerlist to find players with goals
		if (!playerList.isEmpty()) { //only run if players have been entered
			for (Players pl : playerList) {
				// count goals and output difference
				if (pl.getGoals() > 0 && players1.contains(pl)) { //only increase if player is in club
					club1Score++;
				} else if (pl.getGoals() > 0 && players2.contains(pl)) {
					club2Score++;
				}
			}
			
			//make sure you remove the goals they had before
			club1.incGoalsScored(club1Score - oldCount1);
			club2.incGoalsScored(club2Score - oldCount2);
			
			//if only one team scored 0, increment the other goalies clean sheets
			if ((club1Score - oldCount1) == 0 && (club2Score - oldCount2) > 0) {
				club2.getGoalie().setCleanSheets(club1.getGoalie().getCleanSheets() + 1);
			} else if ((club2Score - oldCount2) == 0 && (club1Score - oldCount1) > 0) {
				club1.getGoalie().setCleanSheets(club2.getGoalie().getCleanSheets() + 1);
			}

			club1.incGoalsAgainst(club2Score - oldCount2);
			club2.incGoalsAgainst(club1Score - oldCount1);
			
			club1.setGoalDifference(club1.getGoalsScored() - club1.getGoalsAgainst());
			club2.setGoalDifference(club2.getGoalsAgainst() - club2.getGoalsAgainst());	

			//handle points as appropriate
			if ((club1Score - oldCount1) > (club2Score - oldCount2)) {
				club1.setTotalPoints(club1.getTotalPoints() + 3);
			} else if ((club1Score - oldCount1) < (club2Score - oldCount2)) {
				club2.setTotalPoints(club2.getTotalPoints() + 3);
			} else { // draw
				club1.setTotalPoints(club1.getTotalPoints() + 1);
				club2.setTotalPoints(club2.getTotalPoints() + 1);
			}
		}
		//ensure match is marked as played
		played = true;
	}

	//output results
	public String toResultString() {
		return club1.getName() + " " + club1.getGoalsScored() + " - " + club2.getGoalsScored() + " " + club2.getName();
	}

	//declare winner
	public Club getWinner() {
		if (club1.getGoalsScored() > club2.getGoalsScored()) {
			return club1;
		} else if (club2.getGoalsScored() > club1.getGoalsScored()) {
			return club2;
		} else {
			return null;
		}
	}
	
}