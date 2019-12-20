package agh.cs.genEvo.mapElements;

public class PlantLife implements PlantInterface {
    private int energyValue;

    public PlantLife(int value){
        energyValue = value;
    }

    public PlantLife(PlantInterface template) { energyValue = template.getEnergyValue(); };

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
