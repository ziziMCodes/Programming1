package facilities.buildings;

/**
 * Represents a hall building in the university.
 */
public class Hall extends BuildingAbstract {

    /**
     * Constructs a hall building with the given name.
     *
     * @param name The name of the hall building.
     */
    public Hall(String name) {
        super(name, 4, 6, 100, "Hall");
    }
}

