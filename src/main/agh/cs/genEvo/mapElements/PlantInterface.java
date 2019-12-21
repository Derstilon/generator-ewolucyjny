package agh.cs.genEvo.mapElements;

import java.awt.*;

public interface PlantInterface {
    boolean CanGrowHere();
    boolean CanBeEaten();
    void addGrowth(PlantInterface plant);
    int getEnergyValue();
    Color getColor();
}
