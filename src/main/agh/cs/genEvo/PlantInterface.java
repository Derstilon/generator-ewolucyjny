package agh.cs.genEvo;

public interface PlantInterface {
    boolean CanGrowHere();
    boolean CanBeEaten();
    void addGrowth(PlantInterface plant);
    int getEnergyValue();
}
