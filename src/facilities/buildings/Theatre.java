package facilities.buildings;

/**
 * Represents a theatre building in the university.
 */
public class Theatre extends BuildingAbstract {

    /**
     * Constructs a theatre building with the given name.
     *
     * @param name The name of the theatre building.
     */
    public Theatre(String name) {
        super(name, 6, 10, 200, "Theatre");
    }
}
