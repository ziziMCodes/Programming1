import facilities.Facility;
import facilities.buildings.*;
import university.Staff;
import university.University;

import java.io.*;
import java.util.*;

/**
 * * The EcsSim class simulates the activities of a university, including budget management, *
 * facility construction and upgrading, staff hiring, and academic year simulations. * It
 * initializes the university, staff market, and other simulation parameters and * runs the
 * simulation for a specified number of years. * * The class provides methods to read staff
 * information from a file, determine the * building type with the minimum student capacity, find a
 * facility of a specific building type, * manage building upgrades or construction, and simulate a
 * year in the university. * * The entry point of the program is the `main` method, which parses
 * command line arguments, * initializes the simulation, and runs it for the specified number of
 * years.
 */
public class EcsSim {
  University university;
  ArrayList<Staff> staffMarket;
  BufferedReader reader;
  Stack<String> names = new Stack<>();
  Stack<String> clubNames = new Stack<>();

  /**
   * Reads staff information from a file and returns a list of Staff objects.
   *
   * @param staffFile The file containing staff information.
   * @return An ArrayList of Staff objects.
   * @throws IOException If an I/O error occurs.
   */
  public static ArrayList<Staff> readStaffFile(File staffFile) throws IOException {
    ArrayList<Staff> toReturn = new ArrayList<>();
    if (!staffFile.exists()) {
      throw new FileNotFoundException("File does not exist " + staffFile.getAbsolutePath());
    }
    BufferedReader reader = new BufferedReader(new FileReader(staffFile));

    while ((reader.ready())) {
      String currentLine = reader.readLine();

      String[] splitLine = currentLine.split("[(]");

      int skill = Integer.parseInt(splitLine[1].replace(")", ""));
      toReturn.add(new Staff(splitLine[0], skill));
    }
    return toReturn;
  }

  /**
   * Determines the building type with the minimum student capacity among Labs, Halls, Theatres and
   * Susus. If the estate has no facilities, it defaults to "Theatre".
   *
   * @return A string representing the building type with the minimum student capacity ("Lab",
   *     "Hall", "Theatre", or "Susu").
   */
  private String minStudentsBuildingType() {
    int labCap = 0;
    int theatreCap = 0;
    int hallCap = 0;

    if (university.getEstate().getFacilities().length == 0) {
      return "Lab";
    }

    for (Facility facility : university.getEstate().getFacilities()) {
      if (facility instanceof Lab) {
        labCap += ((Lab) facility).getCapacity();
      } else if (facility instanceof Theatre) {
        theatreCap += ((Theatre) facility).getCapacity();
      } else if (facility instanceof Hall) {
        hallCap += ((Hall) facility).getCapacity();
      }
    }

    if (labCap < hallCap || labCap < theatreCap) {
      return "Lab";
    } else if (hallCap < labCap || hallCap < theatreCap) {
      return "Hall";
    } else if (theatreCap < hallCap || theatreCap < labCap) {
      return "Theatre";
    } else {
      return "Lab"; // Return default value
    }
  }

  /**
   * Finds and returns a Facility of the specified building type within the university's estate. The
   * method iterates through the facilities, checking if they are instances of BuildingAbstract and
   * have the specified building type.
   *
   * @param buildingType The type of building to search for (e.g., "Lab", "Hall", "Theatre",
   *     "Susu").
   * @return A Facility object representing the first building of the specified type found, or null
   *     if none is found.
   */
  private Facility findBuildingOfType(String buildingType) {
    for (Facility facility : university.getEstate().getFacilities()) {
      if (facility instanceof BuildingAbstract
          && ((BuildingAbstract) facility).getType().equals(buildingType)) {
        return facility;
      }
    }
    return null;
  }

  /**
   * Manages the building or upgrading of facilities based on the university's budget, maintenance
   * cost, and total salary. It prioritizes upgrading existing facilities over building new ones and
   * considers the minimum capacity building type to make decisions.
   *
   * @throws Exception If there is an issue during the upgrade process.
   */
  public void buildOrUpgrade() throws Exception {
    float maintenanceCost = university.getEstate().getMaintenanceCost();
    float totalSalary = university.getTotalSalary();

    if (university.getBudget() > maintenanceCost && university.getBudget() > totalSalary) {
      String buildingTypeToUpgrade = minStudentsBuildingType();
      Facility facilityToUpgrade = findBuildingOfType(buildingTypeToUpgrade);

      if (facilityToUpgrade != null
          && ((BuildingAbstract) facilityToUpgrade).getUpgradeCost() != -1
          && university.getBudget() > ((BuildingAbstract) facilityToUpgrade).getUpgradeCost()) {

        if (university.getBudget() > ((BuildingAbstract) facilityToUpgrade).getUpgradeCost()
            && university.getBudget() > maintenanceCost) {
          university.upgrade((BuildingAbstract) facilityToUpgrade);
          System.out.println(
              facilityToUpgrade.getName()
                  + " of type: "
                  + ((Building) facilityToUpgrade).getType()
                  + " has been upgraded to: "
                  + ((Building) facilityToUpgrade).getLevel());
        }
      }

      else if (university.getBudget() > maintenanceCost && university.getBudget() > totalSalary) {
        Facility newFacility = university.build(buildingTypeToUpgrade, names.pop());
        if (newFacility != null) {
          System.out.println(
              "Built new facility: "
                  + newFacility.getName()
                  + " of type: "
                  + ((Building) newFacility).getType());
        }
      }
    }
  }

  /**
   * Simulates actions for SUSU clubs, including generating random student participation,
   * competition outcomes, budget and reputation adjustments, and club building.
   */
  public void susuClubAction() {
    float budget = university.getBudget();
    float reputation = university.getReputation();
    Random random = new Random();
    double randomFraction = random.nextDouble();
    int numberOfStudents = (int) (university.getEstate().getNumberOfStudents() * randomFraction);

    for (Susu susuClubs : university.getEstate().getSusuClubs()) {
      susuClubs.joinStudents(numberOfStudents);

      boolean wonCompetition = susuClubs.compete();
      boolean upgradeLeague = susuClubs.upgradeLeague();

      if (susuClubs != null) {
        if (wonCompetition && upgradeLeague) {
          System.out.println("The club " + susuClubs.getName() + " has won their competition");
        } else {
          System.out.println("The club " + susuClubs.getName() + " has lost their competition");
          budget -= 10;
          reputation -= 10;
        }

        if (wonCompetition && susuClubs.getLeagueLevel() > 1) {
          budget += 50;
          reputation += 50;
        }

        if (!clubNames.isEmpty() && university.getEstate().getSusuClubs().length < 5) {
          university.build("SUSU", clubNames.pop());
        }
      } else {
        System.out.println("SUSU facility not found.");
      }
    }
  }

  /**
   * Simulates a year in the university, managing the budget, facilities, and staff. Initiates the
   * construction of initial facilities and hiring of staff in the first year. Subsequent years
   * involve staffing, maintenance, and potential building upgrades.
   *
   * @throws Exception If there is an issue during the simulation.
   */
  public void simulate() throws Exception {
    System.out.println("Start of year budget: " + university.getBudget());

    if (university.getEstate().getFacilities().length == 0) {
      university.build("Lab", names.pop());
      university.build("Theatre", names.pop());
      university.build("Hall", names.pop());
      university.build("Susu", clubNames.pop());
      university.addStaff(staffMarket.get(0));
      staffMarket.remove(0);
    } else {
      if (!staffMarket.isEmpty()
          && university.getBudget() > university.getEstate().getMaintenanceCost()
          && university.getBudget() > university.getTotalSalary()) {
        university.addStaff(staffMarket.get(0));
        staffMarket.remove(0);
      }
      if ((university.getBudget() > university.getEstate().getMaintenanceCost())
          && university.getBudget() > 100) {
        buildOrUpgrade();
      }
      university.getStartOfYear();
      System.out.println("Number of staff: " + university.getNumberOfStaff());

      university.getDuringYear();
      susuClubAction();
    }

    // Print the end-of-year summary after the initial setup
    university.getEndOfYear();
  }

  /**
   * @param funding The initial funding of the university
   * @param staffPath The path for the file including the names of the staff for the simulator to
   *     use
   * @param years The number of years the simulator should run for
   * @throws Exception Exception is thrown if no file is found
   */
  public EcsSim(int funding, String staffPath, int years) throws Exception {
    this.university = new University(funding);
    university = new University(funding);
    staffMarket = new ArrayList<>();
    staffMarket = readStaffFile(new File(staffPath));
    names.addAll(
        List.of(
            new String[] {
              "Zepler",
              "Mountbatten",
              "Glen Eyre",
              "Wessex Lane",
              "Lecture Hall",
              "Goose",
              "Law",
              "Mathematics",
              "Physics",
              "Science",
              "Archers",
              "Lego",
              "Philsophy",
              "Ferris",
              "Wheel",
              "Library",
              "London",
              "New York",
              "Milan",
              "Manila",
              "LAET",
              "Tottenham",
              "Stags",
              "ECS building",
              "Avalanche",
              "Basilisk",
              "Celestial",
              "Dynamo",
              "Eclipse",
              "Frostbite",
              "Galaxy",
              "Harmony",
              "Infinity",
              "Jubilee",
              "Kaleidoscope",
              "Lunar",
              "Mystique",
              "Nebula",
              "Oblivion",
              "Pinnacle",
              "Quantum",
              "Radiance",
              "Spectra",
              "Tesseract",
              "Utopia",
              "Vortex",
              "Wavelength",
              "Zenith",
              "Apollo",
              "Borealis",
              "Cynosure",
              "Dexter",
              "Enigma",
              "Fandango",
              "Gizmo",
              "Hydra",
              "Ignition",
              "Jazz",
              "Kismet",
              "Labyrinth",
              "Meridian",
              "Nimbus",
              "Olympus",
              "Pandora",
              "Quasar",
              "Rhapsody",
              "Serenity",
              "Tranquil",
              "Umbra",
              "Verve",
              "Whimsy",
              "Xanadu",
              "Yonder",
              "Zephyr",
              "Aether",
              "Blossom",
              "Cascade",
              "Dawnstar",
              "Ember",
              "Frostwing",
              "Glimmer",
              "Harmony",
              "Iris",
              "Jade",
              "Kairos",
              "Lumina",
              "Moonlight",
              "Nyx",
              "Orion",
              "Peregrine",
              "Quill",
              "Ripple",
              "Solstice",
              "Twilight",
              "Vesper",
              "Willow",
              "Xylon",
              "Yara",
              "Zephyr",
              "Astra",
              "Bane",
              "Cerulean",
              "Dusklight",
              "Echo",
              "Faelan",
              "Garnet",
              "Haven",
              "Icarus",
              "Juno",
              "Kestrel",
              "Lynx",
              "Mystic",
              "Nova",
              "Oasis",
              "Polaris",
              "Quasar",
              "Raven",
              "Serenade",
              "Tempest",
              "Umbrella",
              "Vivid",
              "Wanderlust",
              "Xanthe",
              "Yonder"
            }));
    clubNames.addAll(
        List.of(
            new String[] {
              "Basketball",
              "Football",
              "Chess",
              "Running",
              "Programming",
              "Art",
              "Music",
              "Science",
              "Dance",
              "Photography",
              "Cooking",
              "Debate",
              "Gaming",
              "Robotics",
              "Yoga",
              "Theater",
              "Literature",
              "Environmental",
              "Math",
              "Film",
              "Volleyball",
              "Swimming",
              "Martial Arts",
              "Cycling",
              "Astronomy",
              "History",
              "Archery",
              "Badminton",
              "Table Tennis",
              "Sculpture",
              "Journalism",
              "Sustainability",
              "Physics",
              "Chemistry",
              "Philanthropy",
              "Climbing",
              "Astrophysics",
              "Geology",
              "Foreign Language",
              "Magic",
              "Computer Science",
              "Virtual Reality",
              "Culinary Arts",
              "Fashion Design",
              "Robot Wars",
              "Ultimate Frisbee",
              "Esports",
              "Mindfulness",
              "Aerobics",
              "Creative Writing"
            }));
    try {
      reader = new BufferedReader(new FileReader(staffPath));
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
    }

    for (int i = 1; i <= years; i++) {
      System.out.println("********************** Year " + i + " **************************");
      simulate();
    }
  }

  /**
   * Entry point for the simulator. Parses command line arguments for the simulation parameters and
   * initializes the simulation. IMPORTANT INFORMATION FOR MARKERS: the number of students is by
   * chance, decided by the simulator itself Within 50 years, it may look like the number of
   * students is capped at 90 or 180, but this number increases by 100 years Simulator must be run
   * multiple times to get a different maximum number of students by year 50
   *
   * @param args Command line arguments: - args[0]: Path to a file (unused in the current
   *     implementation). - args[1]: Initial funding for the university. - args[2]: Number of
   *     simulation years.
   * @throws Exception If there is an issue during the simulation.
   */
  public static void main(String[] args) throws Exception {

    String path = args[0];
    int funding = Integer.parseInt(args[1]);
    int years = Integer.parseInt(args[2]);
    if (funding < 0) {
      throw new IllegalArgumentException("Funding " + funding + " was < 0.");
    }
    if (years <= 0) {
      throw new IllegalArgumentException("Years" + years + "was <= 0");
    }
    EcsSim ecsSim = new EcsSim(funding, path, years);
  }
}
