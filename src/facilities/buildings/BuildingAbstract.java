package facilities.buildings;

import facilities.Facility;

/**
 * An abstract class representing a building in the university's facilities.
 * Extends the Facility class and implements the Building interface.
 */
public class BuildingAbstract extends Facility implements Building {
    private int level = 1;
    private final int maxLevel;
    private final int baseCapacity;
    private final int baseBuildingCost;
    private final String type;

    /**
     * Constructor for the abstract building class.
     *
     * @param name            The name of the building.
     * @param maxLevel        The maximum level the building can reach.
     * @param baseCapacity    The base capacity of the building.
     * @param baseBuildingCost The base building cost of the building.
     * @param type            The type of the building.
     */
    protected BuildingAbstract(String name, int maxLevel, int baseCapacity, int baseBuildingCost, String type) {
        super(name);
        this.maxLevel = maxLevel;
        this.baseBuildingCost = baseBuildingCost;
        this.baseCapacity = baseCapacity;
        this.type = type;
    }

    /**
     * Gets the type of the building.
     *
     * @return The type of the building.
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the current level of the building.
     *
     * @return The current level of the building.
     */
    @Override
    public int getLevel() {
        return level;
    }

    /**
     * Increases the level of the building, up to its maximum level.
     * Prints a message if the building is already at the maximum level.
     */
    @Override
    public void increaseLevel() {
        if (level < maxLevel) {
            level++;
        } else {
            System.out.println("Building " + this.getName() + " is already at max level " + this.getMaxLevel());
        }
    }

    /**
     * Gets the cost to upgrade the building to the next level.
     *
     * @return The cost to upgrade the building, or -1 if the building is already at the maximum level.
     */
    @Override
    public int getUpgradeCost() {
        if (level < maxLevel) {
            return baseBuildingCost * (level + 1);
        } else {
            return -1;
        }
    }

    /**
     * Gets the current capacity of the building.
     *
     * @return The current capacity of the building.
     */
    @Override
    public int getCapacity() {
        return (int) (baseCapacity * Math.pow(2, level - 1));
    }

    /**
     * Gets the maximum level the building can reach.
     *
     * @return The maximum level of the building.
     */
    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    /**
     * Gets the name of the building.
     *
     * @return The name of the building.
     */
    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * Gets the base building cost of the building.
     *
     * @return The base building cost of the building.
     */
    @Override
    public int getBaseBuildingCost() {
        return baseBuildingCost;
    }
}

