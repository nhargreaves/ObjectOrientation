package sportsLeague;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class League{
	private String leagueName;
	ArrayList<Club> leagueClubs = new ArrayList<Club>();
	ArrayList<Match> fixtures = new ArrayList<Match>();

	public League(String newLeagueName) {
		this.leagueName = newLeagueName;
	}
	
	//get the team with the top score: go by points then goal difference
	public Club getTopTeam() {
		ArrayList<Club> topTeams = new ArrayList<Club>();
		int maxScore = leagueClubs.get(0).getTotalPoints();
		topTeams.add(leagueClubs.get(0));
		
		for (Club c : leagueClubs) {
			if (c.getTotalPoints() > maxScore) {
				topTeams.clear();
				topTeams.add(c);
				maxScore = c.getTotalPoints();
			} else if (c.getTotalPoints() == maxScore) {
				topTeams.add(c);
			}
		}

		Club topClub = null; 
		maxScore = 0;
		if (topTeams.size() > 1) {
			for (Club c : topTeams) {
				if (c.getDifference() >= maxScore) {
					topClub = c;
					maxScore = c.getDifference();
				}
			}
		} else {
			topClub = topTeams.get(0);	
		}
		
		return topClub;
	}
	//for testing: 'record a result in the league'
	public void getFixtures() {
		for (Match ml : fixtures) {
			System.out.println(ml.getPlayed());
		}
	}
	
	//get/set name
	public String getName() {
		return leagueName;
	}
	
	//print out league table
	@Override
	public String toString() {
		int pos = 1;
		
		//comparable to sort league by points
		Collections.sort(leagueClubs);
		
		//position, club, goalsfor, goalsagainst, points, difference
		String leaguePrint = "POS    CLUB     GF   GA   P   DIFF";
		for (Club c : leagueClubs) { 
			leaguePrint = leaguePrint + "\n" + pos + "      " + c.getName() + "   " + c.getGoalsScored() + "    " + c.getGoalsAgainst() + "    " +  c.getTotalPoints() + "   " + c.getDifference();
			pos++;
		}
		
		return leaguePrint;
	}
	
	public void setName(String newName) {
		leagueName = newName;
	}
	
	//add a club to the league
	public void addToLeague(Club club) {
		leagueClubs.add(club);
	}
	
	//add a fixture to the league
	public boolean addFixture(Match match) {
		boolean found1 = false;
		boolean found2 = false;

		//check if these clubs are present in the league
		for (Club c : leagueClubs) {
			if (match.getClub1().getName().equals(c.getName())) {
				found1 = true;
			} else if (match.getClub2().getName().equals(c.getName())) {
				found2 = true;
			}
		}
		if (found1 == true && found2 == true) {
			fixtures.add(match);
			return true;
		} else {
			return false;
		}
	}

	public Players getTopScorer() {
		Players topScorer = null;
		Players testPlay = new Players("NULL", null, 0);
		topScorer = testPlay;
		for (Club c : leagueClubs) {
			for (Players p : c.getSquad()) {
				if (p.getGoals() > topScorer.getGoals()) {
					topScorer = p;
				}
			}
		}
		return topScorer;
	}
	
	//return goalkeeper who has the most 'clean sheets' 
	public Goalkeeper getTopGoalie() {
		Goalkeeper topGoalie = null;
		Goalkeeper testGoal = new Goalkeeper("NULL", null, 45);
		testGoal.setCleanSheets(-1);
		
		topGoalie = testGoal;
		for (Club c : leagueClubs) {
			if (c.getGoalie().getCleanSheets() > topGoalie.getCleanSheets()) {
				topGoalie = c.getGoalie();
			}
		}
		return topGoalie;
	}
	
	

}
