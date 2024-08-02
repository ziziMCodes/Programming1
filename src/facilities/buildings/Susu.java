package facilities.buildings;

import java.util.Random;
/**
 * Represents a SUSU (Students' Union) club in the university.
 * Extends the BuildingAbstract class and provides methods for student participation,
 * competition, league upgrades, and retrieving the league level.
 */
public class Susu extends BuildingAbstract {
    private int league;
    String name;
    /**
     * Constructs a SUSU club with the specified name.
     *
     * @param name The name of the SUSU club.
     */
    public Susu(String name) {
            super(name, 10, 12, 150, "SUSU");
            this.name = name;
            league = 10;
    }
    /**
     * This method has a line commented out for the output to look more clean. Please uncomment if you would like.
     * @param students The number of students in the club
     * @return students*10 which will be added onto the reputation of the university
     */
    public void joinStudents(int students) {
        //System.out.println(students + " students are in the " + this.name + " SUSU club");
    }
    /**
     * Simulates a competition for the SUSU club.
     *
     * @return True if the club wins the competition, false otherwise.
     */

    public boolean compete() {
        double chanceOfWinning = 0.8;
        Random random = new Random();
        double randomValue = random.nextDouble();

        if (randomValue < chanceOfWinning) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * Upgrades the league level of the SUSU club based on competition outcomes.
     *
     * @return True if the league is successfully upgraded, false otherwise.
     */

    public boolean upgradeLeague() {
        boolean won = compete();
        if (won && league > 1) {
            this.league--;
            System.out.println("The club " + this.name + " has moved up in the league to league: " + this.league);
            return true;
        } else if (won && league == 1) {
            System.out.println("Club " + this.name + " is already in the highest league");
            return false;  // Returning false as the club is already in the highest league
        } else if (!won && this.league >= 1 && this.league < 10) {
            this.league++;
            return true;  // Returning true as the club moved down in the league
        } else {
            return false;  // Returning false if no league change occurred
        }
    }



    public int getLeagueLevel(){
        return this.league;
    }

}

