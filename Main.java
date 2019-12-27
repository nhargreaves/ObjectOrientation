package sportsLeague;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, URISyntaxException, ParseException {

		ArrayList<Players> playerList = new ArrayList<Players>();
		String mainPath = Paths.get(Main.class.getResource("/").toURI()).toString();
		String fullPath = mainPath + "/res/data";
		Scanner input = new Scanner(new File(fullPath));
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");

		String name = "";
		Date dob = null;
		LocalDate dobLocal = null;
		int height = -1;
		String[] dobSplit;
		
		//skip through, respecting that each line is a different field of information
		int i = 0;
		while (input.hasNextLine()) // input.hasNextLine())
		{
			switch (i) {
			case 0: // data is name
				name = input.nextLine();
				i++;
				break;
			case 1: 
				//have to convert this to LocalDate to make later calculations on it simpler
				dob = sdf.parse(input.nextLine());
				Instant instant = Instant.ofEpochMilli(dob.getTime());
				LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
				dobLocal = localDateTime.toLocalDate();

				i++;
				break;
			case 2: // data is height
				height = Integer.parseInt(input.nextLine());

				Players newPlayer = new Players(name, dobLocal, height);
				playerList.add(newPlayer);
				i = 0;
				break;
			}
		}

		// 1. a group of brand new players must be created from the data file input. add
		// these to a list for future reference
		System.out.println("1. Import players from the text file. List of players: ");
		for (Players pl : playerList) {
			System.out.println(pl);
		}
		System.out.println();

		// 2. Barry Eta has changed his name to Stewart Eta.

		System.out.println("2. Barry Eta has changed his name to Stewart Eta. Change his name.");
		for (Players pl : playerList) {
			if (pl.getName().equals("Barry Eta")) {
				pl.setName("Stewart Eta");
				System.out.println("Barry White is now " + pl.getName());
				break;
			}
		}
		System.out.println();

		// 3. these players all got hired: add two new clubs from fixed data add the
		// players objects to the club squads
		System.out.println("\n3. These players got hired. Add two new clubs, and add the players to these.");
		Club kakapo = new Club("Kakapo");
		Club ravens = new Club("Ravens");

		int count = 0;
		for (Players pl : playerList) {
			if (count % 2 == 0) {
				kakapo.addToSquad(pl);
			} else {
				ravens.addToSquad(pl);
			}
			count++;
		}

		System.out.println("Kakapo squad:");
		System.out.println(kakapo.getSquad());

		System.out.println("Ravens squad:");
		System.out.println(ravens.getSquad());

		// 4. the clubs want to play a match. Schedule a match and add this to fixtures. Use 11 players for each team, including a goalkeeper.
		System.out.println("\n4. The clubs want to play a match. Schedule a match and add this to the fixtures. Use 11 players for each team, including one goalkeeper.");
		ArrayList<Players> kakapoTeam = new ArrayList<Players>();
		ArrayList<Players> ravensTeam = new ArrayList<Players>();
		
		LocalDate kbdate = LocalDate.of(1990, Month.JANUARY, 1);
		LocalDate rbdate = LocalDate.of(1991, Month.MARCH, 12);
		Goalkeeper kGoalkeeper = new Goalkeeper("Stuart Goal", kbdate, 56);
		Goalkeeper rGoalkeeper = new Goalkeeper("Billy Goal", rbdate, 56);
		
		count = 0;
		for (Players pl : kakapo.getSquad()) {
			if (count < 10) {
				kakapoTeam.add(pl);
			} else {
				break;
			}
			count++;
		}
		kakapo.setGoalie(kGoalkeeper);
		
		count = 0;
		for (Players pl : ravens.getSquad()) {
			if (count < 10) {
				ravensTeam.add(pl);
			} else {
				break;
			}
			count++;
		}
		ravens.setGoalie(rGoalkeeper);

		//size wont show the goalkeeper, so increment it by one
		System.out.println("\nThere are " + (kakapoTeam.size() + 1) + " players in the " + kakapo.getName() + " team:");
		for (Players pl : kakapoTeam) {
			System.out.println(pl.getName());
		}
		System.out.println("\nThere are " + (ravensTeam.size() + 1) + " players in the " + ravens.getName() + " team:");
		for (Players pl : ravensTeam) {
			System.out.println(pl.getName());
		}

		Date matchTime = new Date();
		Match newMatch = new Match(matchTime, "St.James Stadium", kakapo, ravens, kakapoTeam, ravensTeam);
		
		League newLeague = new League("One");
		newLeague.addToLeague(kakapo);
		newLeague.addToLeague(ravens);
		
		if (newLeague.addFixture(newMatch)) {
			System.out.println("Fixture successfully added.");
		} else {
			System.out.println("Error: match clubs must both already be present within the league.");
		}
		
		System.out.println("Match scheduled for " + newMatch);

		// 5. The match has been completed. Output the final result.
		Random random = new Random();
		System.out.println("\n5. Final result:");
		// must check if any players in each team already have previous goals (to remove
		// from final result)
		int origKGoals = 0;
		int origRGoals = 0;

		for (Players pl : playerList) {
			if (pl.getGoals() > 0) {
				for (Players kT : kakapoTeam) {
					if (kT.getName().equals(pl.getName())) {
						origKGoals += pl.getGoals();
						break;
					} else {
						for (Players rT : ravensTeam) {
							if (rT.getName().equals(pl.getName())) {
								origRGoals += pl.getGoals();
								break;
							}

						}
					}
				}
			}
		}

		// randomise some values for the goals
		for (Players pl : playerList) {
			int x = random.nextInt(4);
			if (x == 1) {

				for (Players pr : playerList) {
					if (pr.getName() == pl.getName()) {
						pl.addGoals(1);
					}
				}
			} else if (x == 2) {
				for (Players pr : playerList) {
					if (pr.getName() == pl.getName()) {
						pl.addGoals(2);
					}
				}
			}
		}
		newMatch.addScores(playerList, origKGoals, origRGoals, kakapo, ravens);

		System.out.println(newMatch.toResultString());
		//////////////// ADD A SET OF PLAYERS
		if (newMatch.getWinner() == kakapo) {
			System.out.println("Kakapo win.");
		} else if (newMatch.getWinner() == ravens) {
			System.out.println("Ravens win.");
		} else {
			System.out.println("Draw");
		}
		
		//6. Prove that the league fixtures have updated automatically in the matches due to referencing	
		System.out.println("\n6. Prove that the league fixtures have updated to show the played match.");
		System.out.println("Match played:\n" + newMatch.getPlayed());
		System.out.println("League updated: ");
		newLeague.getFixtures();
		
		//7. Who is the current top club?
		Club topClub = newLeague.getTopTeam();
		System.out.println("\n7. The top club is " + topClub.clubName + " with " + topClub.getTotalPoints() + " points and a " + topClub.getGoalDifference() + " goal difference.");
		
		//8. Print a league table
		System.out.println("\n8. Print a league table.");
		System.out.println(newLeague.toString());
		
		//9. Get the top scorer
		System.out.println("\n9. Top scorer is " + newLeague.getTopScorer().getName() + " with " + newLeague.getTopScorer().getGoals() + " goals.");
		
		// 10. Which group has the highest average height?
		System.out.println("\n10. Average heights:");
		System.out.println("Kakapo: " + kakapo.getAvgHeight());
		System.out.println("Ravens: " + ravens.getAvgHeight());
		if (kakapo.getAvgHeight() > ravens.getAvgHeight()) {
			System.out.println("Kakapos have the highest average height.");
		} else {
			System.out.println("Ravens have the highest average height.");
		}

		//11. Which group has the highest average age?
		System.out.println("\n11. Average age:");
		System.out.println("Kakapo: " + kakapo.getAvgAge());
		System.out.println("Ravens: " + ravens.getAvgAge());
		if (kakapo.getAvgAge() > ravens.getAvgAge()) {
			System.out.println("Kakapos have the highest average age.");
		} else {
			System.out.println("Ravens have the highest average age.");
		}
		
		//12. Which goalkeeper conceded the least goals? Find the top goalkeeper.
		System.out.println("\n12. Which goalkeeper conceded the least goals? Find the top goalkeeper.");
		System.out.println("The top goalkeeper is " + newLeague.getTopGoalie().getName() + " with " + newLeague.getTopGoalie().getCleanSheets() + " clean sheets.");
	
		//13. Add a match between the ravens and a new club. Do not add the new club to the league.
		System.out.println("\n13. Add a match between the ravens and a new club. Do not add the new club to the league.");
		Club blackbirds = new Club("Blackbirds");
		Match invalidMatch = new Match(matchTime, "St.James Stadium", blackbirds, ravens, kakapoTeam, ravensTeam);
		if (newLeague.addFixture(invalidMatch)) {
			System.out.println("Fixture successfully added.");
		} else {
			System.out.println("Error: match clubs must both already be present within the league.");
		}

	}

}
