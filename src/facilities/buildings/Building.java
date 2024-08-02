package facilities.buildings;
public interface Building {

    int getLevel();
    String getType();

    void increaseLevel();
    int getUpgradeCost();
    int getCapacity();

    int getMaxLevel();

    String getName();
    int getBaseBuildingCost();

}


