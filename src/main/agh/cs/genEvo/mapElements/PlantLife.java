package agh.cs.genEvo.mapElements;

import java.awt.*;

public class PlantLife implements PlantInterface {
    private int energyValue;
    private static Color plantColor;

    public PlantLife(int value){
        energyValue = value;
        plantColor = new Color(28, 119, 58);
    }

    public PlantLife(PlantInterface template) {
        this(template.getEnergyValue());
    };

    @Override
    public boolean CanBeEaten() {
        return true;
    }

    @Override
    public boolean CanGrowHere() {
        return false;
    }

    @Override
    public int getEnergyValue() {
        return energyValue;
    }

    @Override
    public Color getColor() {
        return plantColor;
    }

    @Override
    public void addGrowth(PlantInterface plant) {
        return;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return ";";
    }
}
