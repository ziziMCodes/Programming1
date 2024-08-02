package facilities.buildings;

/**
 * Represents a laboratory building in the university.
 */
public class Lab extends BuildingAbstract {

    /**
     * Constructs a lab building with the given name.
     *
     * @param name The name of the lab building.
     */
    public Lab(String name) {
        super(name, 5, 5, 300, "Lab");
    }
}

