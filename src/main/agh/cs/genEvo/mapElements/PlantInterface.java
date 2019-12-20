package agh.cs.genEvo.mapElements;

public interface PlantInterface {
    boolean CanGrowHere();
    boolean CanBeEaten();
    void addGrowth(PlantInterface plant);
    int getEnergyValue();
}
