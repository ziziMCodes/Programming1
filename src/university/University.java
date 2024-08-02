package university;

import facilities.Facility;
import facilities.buildings.Building;
import facilities.buildings.BuildingAbstract;

import java.util.Iterator;
import java.util.Random;
/**
 * Represents a university with facilities, budget, staff and human resources.
 */
public class University {
    private float budget;
    private final Estate estate;
    private int reputation;
    private final HumanResource humanResource;
    /**
     * Constructs a new University object with the specified initial funding.
     *
     * @param funding The initial budget for the university.
     */
    public University(int funding) {
        this.budget = funding;
        estate = new Estate();
        humanResource = new HumanResource();
    }
    /**
     * Builds a new facility of the specified type and name.
     *
     * @param type The type of the facility to be built (e.g., "Hall", "Theatre", "Lab").
     * @param name The name of the facility to be built.
     * @return The built Facility object if successful, or null if the budget is insufficient.
     */
    public Facility build(String type, String name) {
        Facility newFacility = estate.addFacility(type, name);
        if (newFacility == null) {
            return null;
        }
        if (budget > ((BuildingAbstract) newFacility).getBaseBuildingCost()) {
            budget -= ((BuildingAbstract) newFacility).getBaseBuildingCost();
            reputation += 100;
            System.out.println("Building of type: " + type + " name: " + name + " has been built");
            return newFacility;
        } else {
            return null;
        }
    }

    /**
     * Upgrades the specified building, increasing its level and reputation.
     *
     * @param building The Building object to be upgraded.
     * @throws Exception If the budget is too low to perform the upgrade or if the building is not part of the estate.
     */
    public void upgrade(Building building) throws Exception {
        boolean buildingFound = false;
        for (Facility facility : estate.getFacilities()) {
            if (building.getName().equals(facility.getName())) {
                int cost = building.getUpgradeCost();
                if (budget >= cost && cost > 0) {
                    upgrade(building, cost);
                } else {
                    throw new Exception("Budget is too low to build");
                }
                buildingFound = true;
                break;
            }
        }

    if (!buildingFound) {
        throw new Exception("This building " + building.getName() + " is not a part of the estate");
    }
}



    /**
     * Performs the actual upgrade of the building, decreasing the budget, increasing the building level, and updating reputation.
     *
     * @param building The Building object to be upgraded.
     * @param cost The cost of the upgrade.
     */
    private void upgrade(Building building, int cost)  {
        budget -= cost;
        building.increaseLevel();
            reputation += 50;
            System.out.println(
                    building.getName()
                            + " has been upgraded to: "
                            + building.getLevel()
                            + " and reputation has increased to: "
                            + reputation);

    }
    /**
     * Performs the start-of-year actions, updating the budget based on the number of students and printing the student count.
     */
    private void startOfYear(){
        budget += estate.getNumberOfStudents() * 10;
        System.out.println("The university has " + estate.getNumberOfStudents() + " students");
    }


    /**
     * Maintains the staff-student ratio by instructing students based on the maximum number of students each staff member can handle.
     * Adjusts the university's reputation and prints the total number of students being instructed and any uninstructed students.
     *
     * @throws Exception If an issue arises during the process.
     */
    private void staffStudentRatio() throws Exception {
        int maxNumberStudentsEachStaff = 20;
        int totalInstructedStudents = 0;

        Iterator<Staff> staffIterator = humanResource.getStaff();
        while (staffIterator.hasNext() && totalInstructedStudents < estate.getNumberOfStudents()) {
            Staff staff = staffIterator.next();

            // Calculate the number of students this staff member can instruct
            int studentsToInstruct = Math.min(maxNumberStudentsEachStaff, estate.getNumberOfStudents() - totalInstructedStudents);

            // Instruct the students and update the total
            totalInstructedStudents += studentsToInstruct;
            reputation += staff.instruct(studentsToInstruct);

        }
        System.out.println("Total number of students being instructed: " + totalInstructedStudents);
        int uninstructedStudents = estate.getNumberOfStudents() - totalInstructedStudents;
        if (uninstructedStudents > 0) {
            System.out.println("Number of uninstructed students: " + uninstructedStudents);
            reputation -= uninstructedStudents;
        }
    }

    /**
     * Executes the university activities during the academic year, including maintaining the staff-student ratio
     * and handling any related processes.
     *
     * @throws Exception If an issue arises during the execution.
     */
    private void duringYear() throws Exception {

        staffStudentRatio();
    }


    /**
     * Conducts end-of-year activities for the university, including staff updates, budget adjustments,
     * and reporting on maintenance cost, number of buildings, budget, and reputation.
     */
    private void endOfYear(){
        System.out.println("Total staff salary: " + humanResource.getTotalSalary());
        budget = budget - estate.getMaintenanceCost() - humanResource.getTotalSalary();
        for (Iterator<Staff> it = humanResource.getStaff(); it.hasNext(); ) {
            Staff staff = it.next(); //go through hashmap

            staff.increaseYearsOfTeaching();

            if (staff.getYearsOfTeaching() > 30) {
                System.out.println(staff.getName() + "left due to 30 years of teaching");
                it.remove();
                continue;
            }
            //staff leaving due to stamina is due to chance e.g. 40 stamina = 40% chance leaving
            //chance of leaving based on stamina
            double chanceOfLeaving = (double) staff.getStamina() / 100;
            Random random = new Random();
            double randomValue = random.nextDouble();

            if ((randomValue > chanceOfLeaving && humanResource.getStaffSize() > 1) || staff.getStamina() < 0) {
                System.out.println(staff.getName() + "has left due to low stamina of: " + staff.getStamina());
                it.remove(); //remove the staff from the list
            } else {
                humanResource.replenishStamina(humanResource.staffSalary);
                //System.out.println(staff.getName() + "has had their stamina replenished to: " + staff.getStamina());
            }

        }


        System.out.println("Total maintenance cost: " + estate.getMaintenanceCost());
        System.out.println("Number of buildings: " + estate.getFacilities().length);
        System.out.println("End of year budget is: " + budget);
        System.out.println("End of Year reputation: " + reputation);
    }
    /**
     * Initiates the end-of-year activities for the university.
     */
    public void getEndOfYear() {
        endOfYear();
    }

    /**
     * Initiates the start-of-year activities for the university.
     */
    public void getStartOfYear() {
        startOfYear();
    }

    /**
     * Initiates the activities to be performed during the academic year for the university.
     *
     * @throws Exception If an exceptional situation occurs during the academic year.
     */
    public void getDuringYear() throws Exception {
        duringYear();
    }

    /**
     * Retrieves the estate (facilities) of the university.
     *
     * @return The estate of the university.
     */
    public Estate getEstate() {
        return estate;
    }

    /**
     * Adds a staff member to the human resource of the university.
     *
     * @param staff The staff member to be added.
     */
    public void addStaff(Staff staff) {
        humanResource.addStaff(staff);
    }

    /**
     * Retrieves the current budget of the university.
     *
     * @return The budget of the university.
     */
    public float getBudget() {
        return budget;
    }

    /**
     * Retrieves the total salary expense for the university's staff.
     *
     * @return The total salary expense.
     */
    public float getTotalSalary() {
        return humanResource.getTotalSalary();
    }

    /**
     * Retrieves the number of staff members in the university.
     *
     * @return The number of staff members.
     */
    public int getNumberOfStaff() {
        return humanResource.getStaffSize();
    }

    /**
     * Retrieves the reputation score of the university.
     *
     * @return The reputation score.
     */
    public int getReputation() {
        return reputation;
    }



}
