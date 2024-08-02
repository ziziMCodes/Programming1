package facilities;

/**
 * Represents a facility in the university.
 */
public class Facility {
    private final String name;

    /**
     * Constructs a facility with the given name.
     *
     * @param name The name of the facility.
     */
    public Facility(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the facility.
     *
     * @return The name of the facility.
     */
    public String getName() {
        return name;
    }
}

