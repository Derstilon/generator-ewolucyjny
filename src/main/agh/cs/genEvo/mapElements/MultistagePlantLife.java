package agh.cs.genEvo.mapElements;

import java.awt.*;

public class MultistagePlantLife implements PlantInterface {
    private int energyValue;

    MultistagePlantLife(int value){
        energyValue = value;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public boolean CanGrowHere() {
        return true;
    }

    @Override
    public boolean CanBeEaten() {
        return true;
    }

    @Override
    public int getEnergyValue() {
        return energyValue;
    }

    @Override
    public void addGrowth(PlantInterface plant) {
        energyValue += plant.getEnergyValue();
    }
}
