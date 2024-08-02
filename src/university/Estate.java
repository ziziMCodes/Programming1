package university;

import facilities.Facility;
import facilities.buildings.*;

import java.util.ArrayList;
/**
 * Represents the estate of the university containing various facilities.
 */
public class Estate {
    private final ArrayList<Facility> facilities = new ArrayList<>();
    private final ArrayList<Susu> susuClubs = new ArrayList<>();

    public Estate() {
    }

    /**
     * Adds a new facility to the estate based on the provided type and name. If the type of the facility
     * is "Susu", the facility also gets added to a list only for susuClubs.
     *
     * @param type The type of the facility (case-insensitive). Valid values are "Hall", "Theatre", "Lab", or "Susu".
     * @param name The name of the facility.
     * @return The newly created Facility object if the type is valid, otherwise, returns null.
     */
    public Facility addFacility(String type, String name){
        Facility newFacility;
        if ("Hall".equalsIgnoreCase(type)){
            newFacility = new Hall(name);
            facilities.add(newFacility);
        }
        else if ("Theatre".equalsIgnoreCase(type)){
            newFacility = new Theatre(name);
            facilities.add(newFacility);
        }
        else if ("Lab".equalsIgnoreCase(type)){
            newFacility = new Lab(name);
            facilities.add(newFacility);
        }
        else if ("Susu".equalsIgnoreCase(type)){
      newFacility = new Susu(name);
            facilities.add(newFacility);
            susuClubs.add((Susu) newFacility);
        }
        else{
            return null;
        }
        return newFacility;
    }

    /**
     * Retrieves an array of facilities currently present in the estate.
     *
     * @return An array of Facility objects representing the facilities in the estate.
     */
    public Facility[] getFacilities(){
        return facilities.toArray(new Facility[0]);
    }

    /**
     * Calculates and returns the total maintenance cost for all facilities in the estate.
     *
     * The maintenance cost is calculated as 0.1 times the capacity of each building facility.
     *
     * @return The total maintenance cost for all facilities in the estate.
     */
    public float getMaintenanceCost(){
        float maintenanceCost = 0;
        for (Facility facility : facilities) {
            maintenanceCost += (float) 0.1 * ((Building) facility).getCapacity();
        }
        return maintenanceCost;

    }
    /**
     * Calculates and returns the minimum capacity among all facility types (Hall, Lab, Theatre) in the estate.
     *
     * @return The minimum capacity among Hall, Lab, Theatre, and Susu facilities in the estate.
     */
    public int getNumberOfStudents() {
        int theatreCapacity = 0;
        int labCapacity = 0;
        int hallCapacity = 0;

        for (Facility facility : facilities) {
            if (facility instanceof Hall) {
                hallCapacity += (((Hall) facility).getCapacity());
            } else if (facility instanceof Lab) {
                labCapacity += ((Lab) facility).getCapacity();
            } else if (facility instanceof Theatre) {
                theatreCapacity += ((Theatre) facility).getCapacity();
            }
        }
        return Math.min(hallCapacity, Math.min(labCapacity, theatreCapacity));
    }
    public Susu[] getSusuClubs(){
        return susuClubs.toArray(new Susu[0]);
    }



}
