package university;

/**
 * Represents a staff member in the university.
 */
public class Staff {
    private final String name; //The name of the staff
    private int skill; //The skill of the staff, this should be between 0 and 100
    private int yearsOfTeaching; //The number of years that the staff has  been employed by the university
    private int stamina; // A number between 0 and 100 representing the current stamina of the staff.
    private int newYearsOfTeaching;


    /**
     * initialises the name and skill member variables, according to the constructors parameters, while yearsOfTeaching and stamina are initialised to 0 and 100
     * @param name Name of the Staff
     * @param skill Skill of the Staff
     */
    public Staff(String name, int skill) {
        yearsOfTeaching = 0;
        stamina = 100;
        this.name = name;
        if (skill > 100 || skill < 0) {
            throw new RuntimeException("The skill of " + this.name + " must be between 0 and 100. Given skill: " + skill);
        } else {
            this.skill = skill;
        }
    }
    /**
     * Instructs a certain number of students and updates the staff's stamina and skill.
     *
     * @param numberOfStudents Number of students to instruct.
     * @return The value which would be added onto the reputation of the university
     */
    public int instruct(int numberOfStudents) {

        int newStamina = stamina - (int) (Math.ceil((double) numberOfStudents / (20 + skill)) * 20);
        if (stamina < 0) {
            System.out.println(this.name + " cannot teach " + numberOfStudents + " students: Loses too much stamina (" + stamina + ")");
        }
        if (this.skill < 100) {
            skill++;
        }
        stamina = newStamina;
        return (100 * skill) / (100 + numberOfStudents);
    }
    /**
     * Replenishes the stamina of the staff member.
     */
    public void replenishStamina() {
        stamina += 20;
        if (stamina > 100) stamina = 100;
    }
    /**
     * Increases the number of years of teaching.
     */
    public void increaseYearsOfTeaching() {
        this.newYearsOfTeaching = yearsOfTeaching++;
    }

    /**
     * Returns the skill of the staff member
     * @return int Skill of this Staff Member
     */
    public int getSkill() {
        return this.skill;
    }

    /**
     * Returns the increased years of teaching.
     *
     * @return Increased years of teaching.
     */
    public int getIncreaseYearsOfTeaching() {
        return this.newYearsOfTeaching;
    }

    /**
     * Returns the number of years of teaching.
     *
     * @return Number of years of teaching.
     */
    public int getYearsOfTeaching() {
        return yearsOfTeaching;
    }

    /**
     * Returns the current stamina of the staff member.
     *
     * @return Current stamina.
     */
    public int getStamina() {
        return stamina;
    }

    /**
     * Returns the name of the staff member.
     *
     * @return Name of the staff member.
     */
    public String getName() {
        return this.name;
    }
    @Override
    public String toString() {
        return name + skill;
    }
}
